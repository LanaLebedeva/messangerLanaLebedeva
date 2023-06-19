package com.github.lanalebedeva.mydiscord.messages.ui.viewlayoutview


import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.withStyledAttributes
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import com.github.lanalebedeva.mydiscord.R
import com.github.lanalebedeva.mydiscord.messages.data.model.MessageData
import com.github.lanalebedeva.mydiscord.messages.data.model.Reactions


class MessageWithReaction @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ViewGroup(context, attrs, defStyleAttr, defStyleRes), GestureDetector.OnGestureListener {

    private val image: ImageView
    private val name: TextView
    private val message: TextView
    private val reaction: FlexBoxLayout

    private var mDetector: GestureDetectorCompat
    private var widthLayout = 0
    private var isMe = false
        set(value) {
            background = if (value)
                getDrawable(context, R.drawable.my_message_gradient)
            else
                getDrawable(context, R.drawable.message)
            field = value
        }


    init {
        context.withStyledAttributes(
            attrs,
            R.styleable.MessageWithReaction,
            defStyleAttr,
            defStyleRes
        ) {
            isMe = this.getBoolean(R.styleable.MessageWithReaction_isMe, false)
        }

        inflate(context, R.layout.custom_message_group, this)

        image = findViewById(R.id.image)
        reaction = findViewById(R.id.view_flexbox)
        name = findViewById(R.id.tv_name)
        message = findViewById(R.id.tv_message)
        mDetector = GestureDetectorCompat(context, this)

        if (isMe) {
            image.visibility = GONE
            name.visibility = GONE
        }
    }

    companion object {
        private const val DEFINE_MARGIN_TEXT_MESSAGE = 30
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        widthLayout = MeasureSpec.getSize(widthMeasureSpec)
        var imageWidth = paddingLeft + DEFINE_MARGIN_TEXT_MESSAGE
        var imageHeight = 0
        if (image.visibility != GONE) {
            measureChildWithMargins(image, widthMeasureSpec, 0, heightMeasureSpec, 0)
            imageWidth +=
                image.measuredWidth + image.marginLeft + image.marginRight
            imageHeight =
                image.measuredHeight + image.marginTop + image.marginBottom
        }
        var nameWidth = 0
        var nameHeight = 0
        if (name.visibility != GONE) {
            measureChildWithMargins(
                name,
                widthMeasureSpec,
                imageWidth,
                heightMeasureSpec,
                imageHeight,
            )
            nameWidth =
                name.measuredWidth + name.marginLeft + name.marginRight
            nameHeight =
                name.measuredHeight + name.marginTop + name.marginBottom
        }
        measureChildWithMargins(
            message,
            widthMeasureSpec,
            imageWidth + paddingRight,
            heightMeasureSpec,
            nameHeight
        )
        imageWidth += DEFINE_MARGIN_TEXT_MESSAGE
        val messageWidth =
            message.measuredWidth + message.marginLeft + message.marginRight
        val messageHeight =
            message.measuredHeight + message.marginTop + message.marginBottom

        measureChildWithMargins(
            reaction,
            widthMeasureSpec,
            imageWidth + reaction.marginLeft,
            heightMeasureSpec,
            nameHeight + messageHeight
        )
        val reactionsWidth =
            reaction.measuredWidth + reaction.marginLeft + reaction.marginRight
        val reactionsHeight =
            reaction.measuredHeight + reaction.marginLeft + reaction.marginRight

        val totalWidth =
            minOf(imageWidth + maxOf(nameWidth, messageWidth, reactionsWidth), widthLayout)
        val totalHeight = maxOf(imageHeight, nameHeight + messageHeight + reactionsHeight)
        setMeasuredDimension(totalWidth, totalHeight)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var offsetX = paddingLeft + DEFINE_MARGIN_TEXT_MESSAGE
        var offsetY = paddingTop
        if (image.visibility != GONE) {
            image.layout(
                offsetX + image.marginLeft,
                offsetY + image.marginTop,
                offsetX + image.measuredWidth,
                offsetY + image.measuredHeight
            )
            offsetX += image.measuredWidth + image.marginLeft + image.marginRight
            offsetX += paddingLeft
        }
        if (name.visibility != GONE) {
            name.layout(
                offsetX + name.marginLeft,
                offsetY + name.marginTop,
                offsetX + name.measuredWidth,
                offsetY + name.measuredHeight,
            )
            offsetY += name.measuredHeight + name.marginLeft + name.marginRight
        } else {
//            offsetX += paddingLeft + 30
        }
        message.layout(
            offsetX + message.marginLeft,
            offsetY + message.marginTop,
            offsetX + message.measuredWidth + message.marginLeft + message.marginRight + DEFINE_MARGIN_TEXT_MESSAGE,
            offsetY + message.measuredHeight
        )
        offsetY += message.measuredHeight + message.marginLeft + message.marginRight

        reaction.layout(
            offsetX + reaction.marginLeft,
            offsetY + reaction.marginTop,
            offsetX + maxOf(reaction.measuredWidth, message.measuredWidth),
            offsetY + reaction.measuredHeight
        )
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

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        reaction.plusView.visibility = VISIBLE
        super.setOnLongClickListener(l)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (mDetector.onTouchEvent(event)) {
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    override fun onShowPress(e: MotionEvent) {}

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return true
    }

    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent) {
        reaction.plusView.visibility = VISIBLE
    }

    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return true
    }

    fun populate(messageData: MessageData) {
        this.removeAllViews()
        //TODO загрузку аватара
        name.text = messageData.senderFullName
        message.text = messageData.content
        reaction.removeAllViewsExceptPlus()
        if (messageData.reactions.isNotEmpty()) {
            reaction.plusView.visibility = VISIBLE
        }
        addReaction(messageData.reactions)
    }

    fun setIsMe(flag: Boolean) {
        isMe = flag
    }

    private fun addReaction(reactions: List<Reactions>) {
        reactions.forEach { rec ->
            var count = rec.count
            val reactionView = ReactionView(context).apply {
                setOnClickListener {
                    if (isSelected) {
                        if (count == 1) {
                            visibility = GONE
                        }
                        count -= 1
                    } else {
                        count += 1
                    }
                    it.isSelected = !it.isSelected
                    setReactionCount(count)
                }
                setReaction(rec.emoji)
                setReactionCount(rec.count)
            }
            reaction.plusView.visibility
            reaction.addBox(reactionView)
        }
        requestLayout()
    }
}
