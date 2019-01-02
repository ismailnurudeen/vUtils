package xyz.ismailnurudeen.viewutils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.io.ByteArrayOutputStream

class VUtils(val context: Context) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun setTextViewDrawableColor(tv: TextView, colorRes: Int) {
        for (drawable in tv.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter = PorterDuffColorFilter(context.getColor(colorRes), PorterDuff.Mode.SRC_IN)
            }
        }
    }

    // convert from bitmap to byte array
    fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }

    // convert from byte array to bitmap
    fun byteArrayToBitmap(image: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(image, 0, image.size)
    }

    fun getBitMapFromVectorDrawableRes(drawableID: Int): Bitmap {
        var drawable = ContextCompat.getDrawable(context, drawableID)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable!!).mutate()
        }
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }

    fun getBitMapFromDrawable(drawableImg: Drawable): Bitmap {
        var drawable = drawableImg
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = DrawableCompat.wrap(drawable).mutate()
        }
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }


    fun setDrawableOnClickListener(myView: View, clickListener: OnDrawableClickListener) {
        val DRAWABLE_LEFT = 0
        val DRAWABLE_TOP = 1
        val DRAWABLE_RIGHT = 2
        val DRAWABLE_BOTTOM = 3

        if (myView is TextView) {
            myView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    if (event.rawX >= myView.right - myView.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.RIGHT)
                    } else if (event.rawY<= myView.left - myView.compoundDrawables[DRAWABLE_LEFT].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.LEFT)
                    } else if (event.rawY >= myView.top - myView.compoundDrawables[DRAWABLE_TOP].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.TOP)
                    } else if (event.rawX <= myView.bottom - myView.compoundDrawables[DRAWABLE_BOTTOM].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.BOTTOM)
                    }
                }
//                if (event.action == MotionEvent.ACTION_DOWN) {
//                    if (event.rawX >= myView.right - myView.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
//                        Toast.makeText(context, "Right Clicked", Toast.LENGTH_SHORT).show()
//                    } else if (event.rawX <= myView.left - myView.compoundDrawables[DRAWABLE_LEFT].bounds.width()) {
//                        clickListener.onDrawableClick(VUtils.OnDrawableClickListener.Companion.DrawablePosition.LEFT)
//                    } else if (event.rawX >= myView.top - myView.compoundDrawables[DRAWABLE_TOP].bounds.height()) {
//                        Toast.makeText(context, "Top Clicked", Toast.LENGTH_SHORT).show()
//                    } else if (event.rawX <= myView.bottom - myView.compoundDrawables[DRAWABLE_BOTTOM].bounds.height()) {
//                        clickListener.onDrawableClick(VUtils.OnDrawableClickListener.Companion.DrawablePosition.BOTTOM)
//                    }
//                }
                false
            }
        } else if (myView is EditText) {
            myView.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= myView.right - myView.compoundDrawables[DRAWABLE_RIGHT].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.RIGHT)
                    } else if (event.rawX <= myView.left - myView.compoundDrawables[DRAWABLE_LEFT].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.LEFT)
                    } else if (event.rawX >= myView.top - myView.compoundDrawables[DRAWABLE_TOP].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.TOP)
                    } else if (event.rawX >= myView.bottom - myView.compoundDrawables[DRAWABLE_BOTTOM].bounds.width()) {
                        clickListener.onDrawableClick(OnDrawableClickListener.Companion.DrawablePosition.BOTTOM)
                    }
                }
                false
            }
        }
    }

    interface OnDrawableClickListener {
        companion object {
            enum class DrawablePosition { TOP, BOTTOM, LEFT, RIGHT }
        }

        fun onDrawableClick(target: DrawablePosition)
    }
}