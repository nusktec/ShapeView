package com.github.stephenvinouze.shapeview.shapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import com.github.stephenvinouze.shapeview.R
import com.github.stephenvinouze.shapeview.ShapeView

/**
 * Created by stephenvinouze on 05/10/16.
 */
class TicketShapeView : ShapeView {

    var innerRadius = 0
    var outerRadius = 0
    var innerOffset = 0
    var innerMargin = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.TicketShapeView, 0, 0)

        innerRadius = a.getDimensionPixelSize(R.styleable.TicketShapeView_outerRadius, 0)
        outerRadius = a.getDimensionPixelSize(R.styleable.TicketShapeView_innerRadius, 0)
        innerOffset = a.getDimensionPixelSize(R.styleable.TicketShapeView_innerOffset, 0)
        innerMargin = a.getDimensionPixelSize(R.styleable.TicketShapeView_innerMargin, 0)

        a.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val width = (w - innerMargin).toFloat()
        val height = (h - innerMargin).toFloat()

        shapePath = Path()
        //top left corner
        shapePath!!.moveTo(width - innerRadius, innerMargin.toFloat())
        shapePath!!.lineTo(width - innerRadius, innerMargin.toFloat())
        //top right corner
        shapeCurveRect.set(width - innerRadius, innerMargin.toFloat(), width, (innerMargin + innerRadius).toFloat())
        shapePath!!.arcTo(shapeCurveRect, 270f, SWEEP_ANGLE.toFloat())
        shapePath!!.lineTo(width, (innerOffset - innerMargin).toFloat())
        //top right curve
        shapeCurveRect.set(width - outerRadius, (innerOffset - innerMargin).toFloat(), width, (innerOffset - innerMargin + outerRadius).toFloat())
        shapePath!!.arcTo(shapeCurveRect, 270f, (-SWEEP_ANGLE).toFloat())
        shapePath!!.lineTo(width - outerRadius, height - innerOffset.toFloat() - outerRadius.toFloat())
        //bottom right curve
        shapeCurveRect.set(width - outerRadius, height - innerOffset + innerMargin - outerRadius, width, height - innerOffset + 2 * innerMargin)
        shapePath!!.arcTo(shapeCurveRect, 180f, (-SWEEP_ANGLE).toFloat())
        shapePath!!.lineTo(width, height - innerOffset + 2 * innerMargin)
        //bottom right corner
        shapePath!!.lineTo(width, height - innerRadius)
        shapeCurveRect.set(width - innerRadius, height - innerRadius, width, height)
        shapePath!!.arcTo(shapeCurveRect, 0f, SWEEP_ANGLE.toFloat())
        //bottom left corner
        shapePath!!.lineTo(innerRadius.toFloat(), height)
        shapeCurveRect.set(innerMargin.toFloat(), height - innerRadius, innerRadius.toFloat(), height)
        shapePath!!.arcTo(shapeCurveRect, 90f, SWEEP_ANGLE.toFloat())
        shapePath!!.lineTo(innerMargin.toFloat(), height - innerOffset + 2 * innerMargin)
        // bottom left curve
        shapeCurveRect.set(innerMargin.toFloat(), height - innerOffset + innerMargin - outerRadius, (innerMargin + outerRadius).toFloat(), height - innerOffset + 2 * innerMargin)
        shapePath!!.arcTo(shapeCurveRect, 90f, (-SWEEP_ANGLE).toFloat())
        shapePath!!.lineTo((innerMargin + outerRadius).toFloat(), (innerMargin + innerOffset + outerRadius).toFloat())
        //top left curve
        shapeCurveRect.set(innerMargin.toFloat(), (innerOffset - innerMargin).toFloat(), (innerMargin + outerRadius).toFloat(), (innerOffset - innerMargin + outerRadius).toFloat())
        shapePath!!.arcTo(shapeCurveRect, 0f, (-SWEEP_ANGLE).toFloat())
        shapePath!!.lineTo(innerMargin.toFloat(), (innerOffset - innerMargin).toFloat())
        shapePath!!.lineTo(innerMargin.toFloat(), innerRadius.toFloat())
        shapeCurveRect.set(innerMargin.toFloat(), innerMargin.toFloat(), innerRadius.toFloat(), (innerMargin + innerRadius).toFloat())
        shapePath!!.arcTo(shapeCurveRect, 180f, SWEEP_ANGLE.toFloat())
        shapePath!!.close()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.clipPath(shapePath!!)
        canvas.drawPath(shapePath!!, shapePaint)
    }

    companion object {
        private val SWEEP_ANGLE = 90
    }
}
