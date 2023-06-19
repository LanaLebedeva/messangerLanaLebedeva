package com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.github.lanalebedeva.mydiscord.R

const val DEFINE_TEXT_SIZE = 5
const val DEFINE_TEXT_COLOR = -3355444


class ReactionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = R.style.ReactionView,
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private val textPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textSizeInView: Int
    private val textBound = Rect()
    private val textToDraw: String
        get() = if (count == 0) {
            reaction
        } else {
            "$reaction $count"
        }
    private lateinit var reaction: String

    private var count = 0
    private var textColor: Int = 0
    private var textSize: Int = DEFINE_TEXT_SIZE

    init {
        context.withStyledAttributes(attrs, R.styleable.ReactionView, defStyleAttr, defStyleRes) {
            reaction = this.getString(R.styleable.ReactionView_reaction) ?: ""
            count = this.getInt(R.styleable.ReactionView_reactionCount, 0)
            textColor = this.getColor(R.styleable.ReactionView_textColor, DEFINE_TEXT_COLOR)
            textSize = this.getDimensionPixelSize(R.styleable.ReactionView_textSize, DEFINE_TEXT_SIZE)
        }
        textSizeInView = textSize
        textPaint.apply {
            textSize = textSizeInView.toFloat()
            color = textColor
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        textPaint.getTextBounds(textToDraw, 0, textToDraw.length, textBound)
        val textWidth = textBound.width()
        val textHeight = textBound.height()

        val measuredWidth = resolveSize(textWidth + paddingLeft + paddingRight, widthMeasureSpec)
        val measuredHeight = resolveSize(textHeight + paddingTop + paddingBottom, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        val y = height / 2f - textBound.centerY()
        canvas.drawText(textToDraw, paddingLeft.toFloat(), y, textPaint)
    }

    fun setReaction(reactionInput: String) {
        reaction = reactionInput.ifBlank {
            ""
        }
        this.requestLayout()
    }

    fun setReactionCount(countInput: Int) {
        count = if (countInput < 0) {
            0
        } else {
            countInput
        }
        this.requestLayout()
    }
}
