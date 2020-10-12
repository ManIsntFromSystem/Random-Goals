package com.quantumman.randomgoals.utils

import android.content.Context
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.VelocityTracker
import android.view.View
import android.view.ViewConfiguration
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.SpringForce.DAMPING_RATIO_LOW_BOUNCY
import androidx.dynamicanimation.animation.SpringForce.STIFFNESS_LOW
import androidx.recyclerview.widget.RecyclerView

class SwipeItemHelper(context: Context, private val listener: SwipeListener) :
    RecyclerView.OnItemTouchListener, RecyclerView.OnChildAttachStateChangeListener {

    private val animations = hashMapOf<RecyclerView.ViewHolder, DynamicAnimation<*>>()
    private var touchSlop: Int = 0 //ложное или незначительное касание
    private var initialTouchX: Float = 0f
    private var recyclerView: RecyclerView? = null
    private var velocityTracker: VelocityTracker? = null
    private var swipedChild: View? = null

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView.apply {
            addOnItemTouchListener(this@SwipeItemHelper)
            addOnChildAttachStateChangeListener(this@SwipeItemHelper)
        }
    }

    fun detachToRecyclerView() {
        recyclerView?.apply {
            removeOnItemTouchListener(this@SwipeItemHelper)
            removeOnChildAttachStateChangeListener(this@SwipeItemHelper)
        }
        animations.apply {
            values.forEach { it.cancel() }
            clear()
        }
        recyclerView = null
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean { //следует ли нам сейчас перехватить ивент
        return when(e.actionMasked) {
            ACTION_DOWN -> {
                initialTouchX = e.x // сохраняем место куда коснулся пользователь
                velocityTracker?.recycle() // очищаем если есть уже
                //создаем новый трекер и прокидываем в трекер - ивент, чтобы посчитать скорость анимации
                velocityTracker = VelocityTracker.obtain().apply { addMovement(e) }
                false // не будем обрабатывать этот ивент, просто засетили его
            }
            ACTION_MOVE -> {
                velocityTracker?.addMovement(e) // запишем в трекер - ивент
                val dX = e.x - initialTouchX
                val dragged = dX > touchSlop // проверяем изменилась ли горизонтальная координата на какоето значение больше чем touchSlop
                // при true мы определяем происходит ли swipe и находим View на которой сейчас находится палец
                if (dragged) swipedChild = rv.findChildViewUnder(e.x, e.y)
                dragged
            }
            ACTION_UP, ACTION_CANCEL -> false
            else -> false
        }
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        if (swipedChild == null) return
        velocityTracker?.addMovement(e) // сохраняем в трекер наше движение
        val dX = e.x - initialTouchX
        when(e.actionMasked) {
            ACTION_MOVE -> swipedChild?.translationX = dX // не меняет координаты, а просто сдвигает для анимации (чтобы View следовала за пальцем)
            ACTION_CANCEL, ACTION_UP -> { // понимаем был ли свайп или нет
                velocityTracker?.computeCurrentVelocity(1000) // считаем текущую скорость движения пальца 1000/сек
                // проверяем тот вью который будем анимировать | это та View которую поличили в onInterceptTouchEvent на которой был опущен палец и началось какое то движение
                val swipeViewHolder: RecyclerView.ViewHolder = rv.findContainingViewHolder(swipedChild!!) ?: return
                val velocity = velocityTracker?.xVelocity ?: return// получаем расчитанную скорость
                if (velocity > 0) {
                    animateWithFling(swipeViewHolder, velocity)  // анимация по закону физики
                } else {
                    animateWithSpring(swipeViewHolder, velocity)
                } 
                velocityTracker?.clear()
            }
        }
    }

    // анимация движения за пальцем
    private fun animateWithFling(swipeViewHolder: RecyclerView.ViewHolder, velocity: Float) {
        FlingAnimation(swipedChild, DynamicAnimation.TRANSLATION_X).apply {
            friction = 1f // некий лоп множитель для онимации
            setStartVelocity(velocity) // скакой скоростью начинать анимировать View by velocity that was got from tracker
            setMaxValue(swipedChild?.width?.toFloat() ?: return) // max anim value
            swipeViewHolder.setIsRecyclable(false) // говорим Recycler у не перетспользовать эту ячейку
            addEndListener( object : DynamicAnimation.OnAnimationEndListener { // устанавливаем listener для окончанияя анимации Fling
                override fun onAnimationEnd(
                    animation: DynamicAnimation<out DynamicAnimation<*>>?,
                    canceled: Boolean,
                    value: Float,
                    velocity: Float
                ) {
                    // когда вылетваем за конец экрана возвращаем переиспользование и передаем в listener.onSwipe()
                    if (value == recyclerView?.width?.toFloat()) {
                        swipeViewHolder.setIsRecyclable(true)
                        listener.onSwipe(swipeViewHolder)
                    } else animateWithSpring(swipeViewHolder, velocity) // если не дошли до конца либо с недостаточной скоростью проскролили
                }

            })
        }
    }

    private fun animateWithSpring(swipeViewHolder: RecyclerView.ViewHolder, velocity: Float) {
        val springForce: SpringForce = SpringForce(0f).apply {
            dampingRatio = DAMPING_RATIO_LOW_BOUNCY
            stiffness = STIFFNESS_LOW
        }
        SpringAnimation(swipeViewHolder.itemView, DynamicAnimation.TRANSLATION_X).apply {
            setStartVelocity(velocity) // скакой скоростью начинать анимировать View by velocity that was got from tracker
            spring = springForce
            swipeViewHolder.setIsRecyclable(false) // говорим Recycler у не перетспользовать эту ячейку
            addEndListener( object : DynamicAnimation.OnAnimationEndListener { // устанавливаем listener для окончанияя анимации Spring
                override fun onAnimationEnd(
                    animation: DynamicAnimation<out DynamicAnimation<*>>?,
                    canceled: Boolean,
                    value: Float,
                    velocity: Float
                ) {
//                    animations - recyclerView
                    animations.remove(recyclerView)
                    swipeViewHolder.setIsRecyclable(true)
                }

            })
        }
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    override fun onChildViewAttachedToWindow(view: View) {}

    override fun onChildViewDetachedFromWindow(view: View) {}

    interface SwipeListener {
        fun onSwipe(swipeViewHolder: RecyclerView.ViewHolder)
    }
}
