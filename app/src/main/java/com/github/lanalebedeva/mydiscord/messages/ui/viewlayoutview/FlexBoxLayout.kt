package com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop


class FlexBoxLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    val plusView: ReactionView
    private var widthLayout = 0
    private var heightLayout = 0


    init {
        isClickable = true
        plusView = ReactionView(context).apply {
            setReaction("➕")
            setReactionCount(0)
            setOnClickListener {
                isClickable = !isClickable
            }
            setPadding(30, 10, 30, 10)
            visibility = GONE
        }
        addView(plusView, 0)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthUsed = paddingLeft
        var heightUsed = paddingTop
        var maxWidth = paddingLeft + paddingBottom
        var numberRow = 0

        widthLayout = MeasureSpec.getSize(widthMeasureSpec) - paddingLeft - paddingRight
        heightLayout = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom

//        plusView.visibility = VISIBLE
        children.forEach { child: View ->
                if (child.visibility != GONE) {
            val widthUsedChild =
                child.measuredWidth + child.marginLeft + child.marginRight + paddingLeft + paddingRight
            val heightUsedChild =
                child.measuredHeight + child.marginTop + child.marginBottom + paddingTop + paddingBottom
            if (widthUsed + widthUsedChild + paddingRight > widthLayout) {
                heightUsed += heightUsedChild
                widthUsed = 0
                maxWidth = maxOf(maxWidth, widthUsed)
                ++numberRow
            }
            measureChildWithMargins(
                child,
                widthMeasureSpec,
                widthUsed,
                heightMeasureSpec,
                heightUsed,
            )

            if (widthUsed + widthUsedChild + paddingRight < widthLayout) {
                widthUsed += widthUsedChild
                if (numberRow == 0) {
                    maxWidth = widthUsed + child.measuredWidth + paddingRight
                    heightUsed =
                         child.measuredHeight + paddingBottom
                } else {
                    maxWidth = maxOf(widthUsed + paddingRight, maxWidth)
                    heightUsed =
                        maxOf(heightUsed, (heightUsedChild + paddingBottom) * numberRow)
                }
            }
                }
        }
        maxWidth = maxOf(maxWidth, widthLayout)
        setMeasuredDimension(
            maxWidth + paddingLeft + paddingRight,
            heightUsed + paddingBottom + paddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

        var offsetXl = paddingLeft
        var offsetYt = paddingTop

        children.forEach { child: View ->
            if (child.visibility != GONE) {
                if (offsetXl + child.measuredWidth + child.marginLeft + child.marginLeft + paddingLeft + paddingRight > widthLayout
                ) {
                    offsetXl = paddingLeft
                    offsetYt += paddingBottom + child.measuredHeight + paddingTop
                }
                offsetXl += paddingLeft
                child.layout(
                    offsetXl + child.marginLeft,
                    offsetYt + child.marginTop,
                    offsetXl + child.measuredWidth + child.marginLeft + child.marginRight,
                    offsetYt + child.measuredHeight + child.marginTop + child.marginBottom
                )
                offsetXl += child.width + child.marginRight + child.marginLeft
            }
        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: LayoutParams): LayoutParams {
        return MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }

    override fun checkLayoutParams(p: LayoutParams): Boolean {
        return p is MarginLayoutParams
    }

    //    fun addBox(unicode: String, count: Int, isMeReaction: Boolean = false, action: (String, Int) -> Unit) {
    fun addBox(reactionView: ReactionView) {
        addView(reactionView, 0)
        plusView.visibility
    }

    fun addListBox(listView: List<View>) {
        listView.forEach {
            addView(it, 0)
        }
    }

    fun removeAllViewsExceptPlus() {
        removeAllViews()
        plusView.visibility = INVISIBLE
        addView(plusView, 0)
    }
}
