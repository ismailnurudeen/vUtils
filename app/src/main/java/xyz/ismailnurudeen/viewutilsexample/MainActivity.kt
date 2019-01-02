package xyz.ismailnurudeen.viewutilsexample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import xyz.ismailnurudeen.viewutils.VUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vUtils = VUtils(this)
        val clickListner = object : VUtils.OnDrawableClickListener {
            override fun onDrawableClick(target: VUtils.OnDrawableClickListener.Companion.DrawablePosition) {

                when (target) {
                    VUtils.OnDrawableClickListener.Companion.DrawablePosition.LEFT -> Toast.makeText(baseContext, "Listener Left Clicked", Toast.LENGTH_SHORT).show()
                    VUtils.OnDrawableClickListener.Companion.DrawablePosition.RIGHT -> Toast.makeText(baseContext, "Listener Right Clicked", Toast.LENGTH_SHORT).show()
                    VUtils.OnDrawableClickListener.Companion.DrawablePosition.TOP -> Toast.makeText(baseContext, "Listener Top Clicked", Toast.LENGTH_SHORT).show()
                    VUtils.OnDrawableClickListener.Companion.DrawablePosition.BOTTOM -> Toast.makeText(baseContext, "Listener Bottom Clicked", Toast.LENGTH_SHORT).show()
                    else -> Toast.makeText(baseContext, "none Clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //doClick(clickListner)
        vUtils.setDrawableOnClickListener(hello_tv, clickListner)
    }

    fun doClick(clickListener: VUtils.OnDrawableClickListener) {
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3
        hello_tv.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                if (event.rawX >= hello_tv.right - hello_tv.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                    Toast.makeText(baseContext, "Right Clicked", Toast.LENGTH_SHORT).show()
                } else if (event.rawX <= hello_tv.left - hello_tv.compoundDrawables[DRAWABLE_LEFT].bounds.width()) {
                    clickListener.onDrawableClick(VUtils.OnDrawableClickListener.Companion.DrawablePosition.LEFT)
                } else if (event.rawX >= hello_tv.top - hello_tv.compoundDrawables[DRAWABLE_TOP].bounds.width()) {
                    Toast.makeText(baseContext, "Top Clicked", Toast.LENGTH_SHORT).show()
                } else if (event.rawX <= hello_tv.bottom - hello_tv.compoundDrawables[DRAWABLE_BOTTOM].bounds.width()) {
                    clickListener.onDrawableClick(VUtils.OnDrawableClickListener.Companion.DrawablePosition.BOTTOM)
                }
            }
            false
        }
    }
}
