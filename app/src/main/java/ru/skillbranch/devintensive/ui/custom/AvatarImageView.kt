package ru.skillbranch.devintensive.ui.custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.skillbranch.devintensive.R


fun Context.dpToPx(dp: Int) =
    dp * (this.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)

class AvatarImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_BORDER_WIDTH = 4 //dp
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_SIZE = 40 //dp

        private val bgColors = listOf(
            Color.parseColor("#676D96"),
            Color.parseColor("#3ABF40"),
            Color.parseColor("#BFB53A"),
            Color.parseColor("#A74A42")
        )
    }


    var borderWidth = context.dpToPx(DEFAULT_BORDER_WIDTH)
    var borderColor = DEFAULT_BORDER_COLOR
    var initals: String = "??"

    var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var initalsPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var resultPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var viewRect = Rect()

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView)

            borderWidth = a.getDimension(
                R.styleable.AvatarImageView_avatar_border_width,
                context.dpToPx(DEFAULT_BORDER_WIDTH)
            )
            borderColor = a.getColor(
                R.styleable.AvatarImageView_avatar_border_color,
                DEFAULT_BORDER_COLOR
            )
            initals = a.getString(R.styleable.AvatarImageView_avatar_initials) ?: "??"
            if(initals.isEmpty()){
                initals = "??"
            }

            a.recycle()
        }

        scaleType = ScaleType.CENTER_CROP
        setup()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val initSize = resolveDefaultSize(widthMeasureSpec)
        setMeasuredDimension(initSize, initSize)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        with(viewRect) {
            left = 0
            top = 0
            bottom = h
            right = w
        }
        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        if(drawable != null) {
            canvas!!.drawOval(viewRect.toRectF(), resultPaint)
        } else {
            drawBg(canvas)
            drawInitials(canvas)
        }
        val half = (borderWidth / 2).toInt()
        viewRect.inset(half, half)
        canvas!!.drawOval(viewRect.toRectF(), borderPaint)
    }

    private fun setup() {
        with(borderPaint){
            color = borderColor
            strokeWidth = borderWidth
            style = Paint.Style.STROKE
        }
    }

    private fun prepareShader(w: Int, h: Int) {
        if (drawable == null) {
            resultPaint.color = Color.GREEN
        } else {
            resultPaint.shader =
                BitmapShader(drawable.toBitmap(w, h), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
    }

    private fun resolveDefaultSize(spec: Int) = when (MeasureSpec.getMode(spec)) {
        MeasureSpec.AT_MOST -> MeasureSpec.getSize(spec)
        MeasureSpec.EXACTLY -> MeasureSpec.getSize(spec)
        MeasureSpec.UNSPECIFIED -> context.dpToPx(DEFAULT_SIZE).toInt()
        else -> MeasureSpec.getSize(spec)
    }

    private fun drawBg(canvas: Canvas?) {
        initalsPaint.color = bgColors[initals.getOrElse(0) {'A'}.toByte() % bgColors.size]
        canvas!!.drawOval(viewRect.toRectF(), initalsPaint)
    }

    private fun drawInitials(canvas: Canvas?) {
        if(drawable != null) return
        with(initalsPaint){
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = height * 0.33f
        }
        val offsetY = (initalsPaint.descent() + initalsPaint.ascent()) / 2
        canvas!!.drawText(initals, viewRect.exactCenterX(), viewRect.exactCenterY() - offsetY, initalsPaint)
    }
}