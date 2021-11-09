package com.dicoding.picodiploma.likesapps

import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.dicoding.picodiploma.likesapps.databinding.ActivityMainBinding
import android.graphics.PorterDuff
import android.os.Build

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mBitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888)
    private val mCanvas = Canvas(mBitmap)
    private val mPaint = Paint()

    private val halfOfWidth = (mBitmap.width/2).toFloat()
    private val halfOfHeight = (mBitmap.height/2).toFloat()

    private val left = 150F
    private val top = 250F
    private val right = mBitmap.width - left
    private val bottom = mBitmap.height.toFloat() - 50F

    private val blackColor = ResourcesCompat.getColor(resources, R.color.black, null)
    private val whiteColor = ResourcesCompat.getColor(resources, R.color.white, null)
    private val brownColor = ResourcesCompat.getColor(resources, R.color.brown_hair, null)
    private val anotherBrownColor = ResourcesCompat.getColor(resources, R.color.brown_anotherHair, null)
    private val yellowColor = ResourcesCompat.getColor(resources, R.color.yellow_skin, null)
    private val anotherYellowColor = ResourcesCompat.getColor(resources, R.color.yellow_anotherSkin, null)
    private val redColor = ResourcesCompat.getColor(resources, R.color.red_ear, null)

    private val message = "Apakah kamu suka Dicoding?"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView.setImageBitmap(mBitmap)
        showText()

        binding.like.setOnClickListener {
            clearCanvas()
            showEar()
            showFace()
            showLip(true)
            showEyes()
            showNoise()
            showText()
            showHair()
        }

        binding.dislike.setOnClickListener {
            clearCanvas()
            showEar()
            showFace()
            showLip(false)
            showEyes()
            showNoise()
            showText()
            showHair()
        }
    }

    private fun showFace() {
        val face = RectF(left, top, right, bottom)

        mPaint.color = anotherYellowColor
        mCanvas.drawArc(face, 90F, 180F, false, mPaint)

        mPaint.color = yellowColor
        mCanvas.drawArc(face, 270F, 180F, false, mPaint)
    }

    private fun showLip(isHappy: Boolean) {
        when (isHappy) {
            true -> {
                mPaint.color = blackColor
                val lip = RectF(halfOfWidth - 200F, halfOfHeight - 100F, halfOfWidth + 200F, halfOfHeight + 400F)
                mCanvas.drawArc(lip, 25F, 130F, false, mPaint)

                mPaint.color = whiteColor
                val mouth = RectF(halfOfWidth - 180F, halfOfHeight, halfOfWidth + 180F, halfOfHeight + 380F)
                mCanvas.drawArc(mouth, 25F, 130F, false, mPaint)


            }
            false -> {
                mPaint.color = blackColor
                val lip = RectF(halfOfWidth - 200F, halfOfHeight + 250F, halfOfWidth + 200F, halfOfHeight + 360F)
                mCanvas.drawArc(lip, 0F, -180F, false, mPaint)

                mPaint.color = whiteColor
                val mouth = RectF(halfOfWidth - 180F, halfOfHeight + 260F, halfOfWidth + 180F, halfOfHeight + 330F)
                mCanvas.drawArc(mouth, 0F, -180F, false, mPaint)
            }
        }
    }

    private fun showEyes() {
        mPaint.color = blackColor
        mCanvas.drawCircle(halfOfWidth - 100F, halfOfHeight - 10F, 50F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 100F, halfOfHeight - 10F, 50F, mPaint)

        mPaint.color = whiteColor
        mCanvas.drawCircle(halfOfWidth - 120F, halfOfHeight - 20F, 15F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 80F, halfOfHeight - 20F, 15F, mPaint)
    }

    private fun showNoise() {
        mPaint.color = blackColor
        mCanvas.drawCircle(halfOfWidth - 40F, halfOfHeight + 140F, 15F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 40F, halfOfHeight + 140F, 15F, mPaint)
    }
    
    private fun clearCanvas() {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC)
    }

    private fun showText() {
        val mPaintText =  Paint(Paint.FAKE_BOLD_TEXT_FLAG).apply {
            textSize = 50F
            color = blackColor
        }

        val mBounds = Rect()
        mPaintText.getTextBounds(message, 0, message.length, mBounds)

        val x: Float = halfOfWidth - mBounds.centerX()
        val y = 50F
        mCanvas.drawText(message, x, y, mPaintText)
    }

    private fun showEar() {
        mPaint.color = anotherBrownColor
        mCanvas.drawCircle(halfOfWidth - 300F, halfOfHeight - 100F, 100F, mPaint)

        mPaint.color = brownColor
        mCanvas.drawCircle(halfOfWidth + 300F, halfOfHeight - 100F, 100F, mPaint)

        mPaint.color = redColor
        mCanvas.drawCircle(halfOfWidth - 300F, halfOfHeight - 100F, 60F, mPaint)
        mCanvas.drawCircle(halfOfWidth + 300F, halfOfHeight - 100F, 60F, mPaint)

    }

    private fun showHair() {
        mCanvas.save()

        val path = Path()

        path.addCircle(halfOfWidth - 100F,halfOfHeight - 10F, 150F, Path.Direction.CCW)
        path.addCircle(halfOfWidth + 100F,halfOfHeight - 10F, 150F, Path.Direction.CCW)

        val mouth = RectF(halfOfWidth - 250F, halfOfHeight, halfOfWidth + 250F, halfOfHeight + 500F)
        path.addOval(mouth, Path.Direction.CCW)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mCanvas.clipPath(path, Region.Op.DIFFERENCE)
        } else {
            mCanvas.clipOutPath(path)
        }

        val face = RectF(left, top, right, bottom)

        mPaint.color = anotherBrownColor
        mCanvas.drawArc(face, 90F, 180F, false, mPaint)

        mPaint.color = brownColor
        mCanvas.drawArc(face, 270F, 180F, false, mPaint)

        mCanvas.restore()
    }
}