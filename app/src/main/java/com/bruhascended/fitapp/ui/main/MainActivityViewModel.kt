package com.bruhascended.fitapp.ui.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.bruhascended.db.activity.entities.ActivityEntry
import com.bruhascended.db.activity.types.ActivityType
import com.bruhascended.fitapp.repository.ActivityEntryRepository
import com.bruhascended.fitapp.util.getStartTime
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessActivities
import com.google.android.gms.fitness.data.DataSet
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    val app = application
    private val repository by ActivityEntryRepository.Delegate(app)
    val cal = Calendar.getInstance(TimeZone.getDefault())
    val endTime = cal.timeInMillis
    val startTime = cal.getStartTime(cal)
    val estimatedStepSource = DataSource.Builder()
        .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
        .setType(DataSource.TYPE_DERIVED)
        .setStreamName("estimated_steps")
        .setAppPackageName("com.google.android.gms")
        .build()

    fun syncPassiveData(context: Context, googleAccount: GoogleSignInAccount) {
        val readRequest = DataReadRequest.Builder()
            .bucketByTime(1, TimeUnit.DAYS)
            .aggregate(DataType.TYPE_CALORIES_EXPENDED)
            .aggregate(estimatedStepSource)
            .aggregate(DataType.TYPE_DISTANCE_DELTA)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .enableServerQueries()
            .build()

        Fitness.getHistoryClient(context, googleAccount)
            .readData(readRequest)
            .addOnSuccessListener {
                for (bucket in it.buckets) {
                    dumpDataSets(bucket.dataSets)
                }
            }
            .addOnFailureListener {
                Log.d("eyo", it.message.toString())
            }
    }

    private fun dumpDataSets(dataSets: List<DataSet>) {
        for (dataSet in dataSets) {
            Log.d("eyo", "DataType: ${dataSet.dataType.name}")
            for (dp in dataSet.dataPoints) {
                for (field in dp.dataType.fields) {
                    Log.d("eyo", "${field.name} = ${dp.getValue(field)}")
                }
            }
        }
    }

    fun syncActivities(context: Context, googleAccount: GoogleSignInAccount) {
        val readRequest = DataReadRequest.Builder()
            .bucketByActivitySegment(1, TimeUnit.SECONDS)
            .aggregate(estimatedStepSource)
            .aggregate(DataType.TYPE_DISTANCE_DELTA)
            .aggregate(DataType.TYPE_CALORIES_EXPENDED)
            .enableServerQueries()
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()

        Fitness.getHistoryClient(context, googleAccount)
            .readData(readRequest)
            .addOnSuccessListener {
                val entriesList = mutableListOf<ActivityEntry>()
                for (bucket in it.buckets) {
                    var calories: Int? = null
                    var steps: Int? = null
                    var distance: Double? = null
                    for (dataSet in bucket.dataSets) {
                        when {
                            dataSet.dataType == DataType.TYPE_STEP_COUNT_DELTA && dataSet.dataPoints.isNotEmpty() -> {
                                steps = dataSet.dataPoints[0].getValue(Field.FIELD_STEPS).asInt()
                            }
                            dataSet.dataType == DataType.TYPE_DISTANCE_DELTA && dataSet.dataPoints.isNotEmpty() -> {
                                distance =
                                    dataSet.dataPoints[0].getValue(Field.FIELD_DISTANCE).asFloat()
                                        .toDouble()
                            }
                            dataSet.dataType == DataType.TYPE_CALORIES_EXPENDED && dataSet.dataPoints.isNotEmpty() -> {
                                calories =
                                    dataSet.dataPoints[0].getValue(Field.FIELD_CALORIES).asFloat()
                                        .toInt()
                            }
                        }
                    }
                    val entry = calories?.let { it1 ->
                        ActivitiesMap.getActivityType(bucket.activity)?.let { it2 ->
                            ActivityEntry(
                                it2,
                                it1,
                                bucket.getStartTime(TimeUnit.MILLISECONDS),
                                bucket.getEndTime(TimeUnit.MILLISECONDS) - bucket.getStartTime(
                                    TimeUnit.MILLISECONDS
                                ),
                                distance,
                                steps
                            )
                        }
                    }
                    if (entry != null) {
                        entriesList.add(entry)
                    }
                }
                CoroutineScope(IO).launch {
                    for (entry in entriesList) {
                        repository.writeEntry(entry)
                    }
                }
            }
            .addOnFailureListener {
                Log.d("eyo", "${it.message}")
            }
    }
}