@file:Suppress("RemoveEmptyParenthesesFromAnnotationEntry", "DEPRECATION")

package com.zachklipp.compose.backstack

import androidx.ui.core.*
import androidx.ui.unit.Density
import androidx.ui.unit.IntOffset
import androidx.ui.unit.IntSize
import com.zachklipp.compose.backstack.BackstackTransition.Crossfade
import com.zachklipp.compose.backstack.BackstackTransition.Slide

/**
 * Defines transitions for a [Backstack]. Transitions control how screens are rendered by returning
 * [Modifier]s that will be used to wrap screen composables.
 *
 * @see Slide
 * @see Crossfade
 */
interface BackstackTransition {

    /**
     * Returns a [Modifier] to use to draw screen in a [Backstack].
     *
     * @param visibility A float in the range `[0, 1]` that indicates at what visibility this screen
     * should be drawn. For example, this value will increase when [isTop] is true and the transition
     * is in the forward direction.
     * @param isTop True only when being called for the top screen. E.g. if the screen is partially
     * visible, then the top screen is always transitioning _out_, and non-top screens are either
     * transitioning out or invisible.
     */
    fun modifierForScreen(
        visibility: Float,
        isTop: Boolean
    ): Modifier

    /**
     * A simple transition that slides screens horizontally.
     */
    object Slide : BackstackTransition {
        override fun modifierForScreen(
            visibility: Float,
            isTop: Boolean
        ): Modifier = PercentageLayoutOffset(
            offset = if (isTop) 1f - visibility else -1 + visibility
        )

        private class PercentageLayoutOffset(private val offset: Float) : LayoutModifier {
            override fun MeasureScope.measure(
                measurable: Measurable,
                constraints: Constraints,
                layoutDirection: LayoutDirection
            ): MeasureScope.MeasureResult {
                TODO("Not yet implemented")
//                var realOffset = offset.coerceIn(-1f..1f)
//                if (layoutDirection == LayoutDirection.Rtl) realOffset *= -1f

//                how does this work?
//                return MeasureScope().layout(
//                    width = (constraints.maxWidth * realOffset).dp,
//                    height = constraints.maxHeight,
//                    alignmentLines = Map()
//                )
            }
//            override fun Density.modifyPosition(
//                childSize: IntSize,
//                containerSize: IntSize,
//                layoutDirection: LayoutDirection
//            ): IntOffset {
//                var realOffset = offset.coerceIn(-1f..1f)
//                if (layoutDirection == LayoutDirection.Rtl) realOffset *= -1f
//                return IntOffset(
//                    x = (containerSize.width * realOffset).toInt(),
//                    y = 0
//                )
//            }
        }
    }

    /**
     * A simple transition that crossfades between screens.
     */
    object Crossfade : BackstackTransition {
        override fun modifierForScreen(
            visibility: Float,
            isTop: Boolean
        ): Modifier = Modifier.drawOpacity(visibility)
    }
}
