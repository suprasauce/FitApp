package com.bruhascended.db.activity.types

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bruhascended.db.R

import com.bruhascended.db.R.string.*
import com.bruhascended.db.R.drawable.*

enum class ActivityType(
    @StringRes
    val stringRes: Int,
    @DrawableRes
    val iconRes: Int,
) {
    Archery(archery, ic_activity_run),
    Badminton(badminton, ic_activity_run),
    BaseBall(baseball, ic_activity_run),
    BasketBall(basketball, ic_activity_run),
    Biathlon(badminton, ic_activity_run),
    Biking(biking, ic_activity_run),
    BikingHand(bikingHand, ic_activity_run),
    BikingMountain(bikingMountain, ic_activity_run),
    BikingRoad(bikingRoad, ic_activity_run),
    BikingSpinning(bikingSpinning, ic_activity_run),
    BikingStationary(bikingStationary, ic_activity_run),
    BikingUtility(bikingUtility, ic_activity_run),
    Calisthenics(calisthenics, ic_activity_run),
    CircuitTraining(circuitTraining, ic_activity_run),
    Cricket(cricket, ic_activity_run),
    Curling(curling, ic_activity_run),
    Diving(diving, ic_activity_run),
    Elevator(elevator, ic_activity_run),
    Elliptical(elliptical, ic_activity_run),
    ErgoMeter(ergoMeter, ic_activity_run),
    Escalator(escalator, ic_activity_run),
    Fencing(fencing, ic_activity_run),
    Frisbee(frisbee, ic_activity_run),
    Gardening(gardening, ic_activity_run),
    GuidedBreathing(guidedBreathing, ic_activity_run),
    Gymnastics(gymnastics, ic_activity_run),
    HandBall(handBall, ic_activity_run),
    Hiking(hiking, ic_activity_run),
    HorseBackRiding(horseBackRiding, ic_activity_run),
    HouseWork(houseWork, ic_activity_run),
    IceSkating(iceSkating, ic_activity_run),
    InVehicle(inVehicle, ic_activity_run),
    JumpRope(jumpRope, ic_activity_run),
    Kayaking(kayaking, ic_activity_run),
    KettleBellTraining(kettleBellTraining, ic_activity_run),
    KickScooter(kickScooter, ic_activity_run),
    KiteSurfing(kiteSurfing, ic_activity_run),
    MartialArts(martialArts, ic_activity_run),
    P90X(p90X, ic_activity_run),
    ParaGliding(paraGliding, ic_activity_run),
    Polo(polo, ic_activity_run),
    RacquetBall(racquetBall, ic_activity_run),
    RockClimbing(rockClimbing, ic_activity_run),
    Rowing(rowing, ic_activity_run),
    RowingMachine(rowingMachine, ic_activity_run),
    RunningTreadMill(runningTreadMill, ic_activity_run),
    Sailing(sailing, ic_activity_run),
    ScubaDiving(scubaDiving, ic_activity_run),
    SkateBoarding(skateBoarding, ic_activity_run),
    Skating(skating, ic_activity_run),
    Skiing(skiing, ic_activity_run),
    Sledding(sledding, ic_activity_run),
    Sleeping(sleeping, ic_activity_run),
    SnowBoarding(snowBoarding, ic_activity_run),
    SnowMobile(snowBoarding, ic_activity_run),
    SnowShoeing(snowShoeing, ic_activity_run),
    SoftBall(softBall, ic_activity_run),
    Squash(squash, ic_activity_run),
    StairClimbing(stairClimbing, ic_activity_run),
    StandupPaddleBoarding(standupPaddleBoarding, ic_activity_run),
    Still(still, ic_activity_run),
    StrengthTraining(strengthTraining, ic_activity_run),
    Surfing(surfing, ic_activity_run),
    TableTennis(tableTennis, ic_activity_run),
    TeamSports(teamSports, ic_activity_run),
    Tennis(tennis, ic_activity_run),
    Tilting(tilting, ic_activity_run),
    Unknown(unknown, ic_activity_run),
    VolleyBall(volleyBall, ic_activity_run),
    WakeBoarding(wakeBoarding, ic_activity_run),
    WalkingNordic(walkingNordic, ic_activity_run),
    WalkingStroller(walkingStroller, ic_activity_run),
    WalkingTreadMill(walkingTreadMill, ic_activity_run),
    WaterPolo(waterPolo, ic_activity_run),
    WheelChair(wheelChair, ic_activity_run),
    WindSurfing(windSurfing, ic_activity_run),
    Walking(walking, ic_activity_run),
    FitnessWalking(fitness_walking, ic_activity_run),
    Running(running, ic_activity_run),
    Jogging(jogging, ic_activity_run),
    Cycling(cycling, ic_activity_run),
    CrossFit(crossfit, ic_activity_run),
    Weightlifting(weightlifting, ic_activity_run),
    Swimming(swimming, ic_activity_run),
    Aerobics(aerobics, ic_activity_run),
    Dancing(dancing, ic_activity_run),
    Zumba(zumba, ic_activity_run),
    Boxing(boxing, ic_activity_run),
    Kickboxing(kickboxing, ic_activity_run),
    MixedMartialArts(mixed_martial_arts, ic_activity_run),
    Rugby(rugby, ic_activity_run),
    Football(football, ic_activity_run),
    Hockey(hockey, ic_activity_run),
    Golf(golf, ic_activity_run),
    HighIntensityIntervalTraining(high_intensity_interval_training, ic_activity_run),
    IntervalTraining(interval_training, ic_activity_run),
    Yoga(yoga, ic_activity_run),
    Pilates(pilates, ic_activity_run),
    Meditation(meditation, ic_activity_run),
    Other(other, ic_activity_run);

    fun getString(context: Context) = context.getString(stringRes)
}