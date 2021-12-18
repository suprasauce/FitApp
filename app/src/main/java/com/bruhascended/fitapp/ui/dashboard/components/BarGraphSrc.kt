package com.bruhascended.fitapp.ui.dashboard.components

import android.content.Context
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.bruhascended.fitapp.util.BarGraphData

val menMaxCalories = 2500f

@Composable
fun BarGraph(data: List<BarGraphData>, context: Context) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        items(items = data, itemContent = { item ->
            Bar(item, context = context)
        })
    }
}


@Composable
fun Bar(item: BarGraphData, context: Context) {

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var offset by remember {
            mutableStateOf(Offset(x = 0f, y = 0f))
        }

        var height by remember {
            mutableStateOf(0f)
        }

        val perc = item.height / menMaxCalories

        val y by animateFloatAsState(
            targetValue = height, animationSpec = tween(500)
        )

        var popUpShown by remember {
            mutableStateOf(false)
        }
        if (popUpShown) {
            Popup(
                onDismissRequest = { popUpShown = false },
                popupPositionProvider = popUpPosProvider(offset)
            ) {

                Card(
                    elevation = 6.dp,
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(vertical = 8.dp, horizontal = 16.dp)) {
                        Text(
                            text = "${item.height.toInt()} Cal on 15 December",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        Canvas(modifier = Modifier
            .size(
                width = 16.dp,
                height = 60.dp
            )
            .onGloballyPositioned { coordinates ->
                offset = coordinates.positionInWindow()
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        popUpShown = true
                        popUpShown = try {
                            this.awaitRelease()
                            false
                        } catch (e: GestureCancellationException) {
                            false
                        }
                    }
                )
            },
            onDraw = {
                height = if (perc <= 1f) perc * size.height
                else size.height
                drawRect(
                    color = Color.Blue,
                    size = Size(width = size.width, height = y),
                    topLeft = Offset(
                        x = 0f,
                        y = size.height - y
                    )
                )

                if (popUpShown) {
                    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    drawLine(
                        color = Color.LightGray,
                        strokeWidth = 8f,
                        start = Offset(size.width / 2f, 0f),
                        end = Offset(size.width / 2f, size.height),
                        pathEffect = pathEffect
                    )
                }
            })


        Text(text = item.x, fontSize = 12.sp)
    }
}

fun popUpPosProvider(offset: Offset): PopupPositionProvider {
    return object : PopupPositionProvider {
        override fun calculatePosition(
            anchorBounds: IntRect,
            windowSize: IntSize,
            layoutDirection: LayoutDirection,
            popupContentSize: IntSize
        ): IntOffset {
            return IntOffset(
                x = offset.x.toInt() - (popupContentSize.width / 2),
                y = offset.y.toInt() - popupContentSize.height
            )
        }
    }
}