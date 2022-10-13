package com.example.slot.ui.main

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.slot.R
import com.example.slot.databinding.ImageViewScrollingBinding
import kotlin.random.Random

class ImageViewScrolling(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private lateinit var eventEnd: IEventEnd
    private var binding: ImageViewScrollingBinding =
        ImageViewScrollingBinding.inflate(LayoutInflater.from(context))
    private var lastResult1 = 0
    private var lastResult2 = 0
    private var lastResult3 = 0
    private var oldValue1 = 0
    private var oldValue2 = 0
    private var oldValue3 = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value1: Int
        get() = Integer.parseInt(binding.nextImage1.tag.toString())
    val value2: Int
        get() = Integer.parseInt(binding.nextImage2.tag.toString())
    val value3: Int
        get() = Integer.parseInt(binding.nextImage3.tag.toString())

    fun setEventEnd(eventEnd: IEventEnd) {
        this.eventEnd = eventEnd
    }

    init {
        addView(binding.root)
        val startValue1 = Random.nextInt(9)
        val startValue2 = Random.nextInt(9)
        val startValue3 = Random.nextInt(9)
        setImage(
            binding.curImage1,
            binding.curImage2,
            binding.curImage3,
            startValue1,
            startValue2,
            startValue3
        )
        setImage(
            binding.nextImage1,
            binding.nextImage2,
            binding.nextImage3,
            startValue1,
            startValue2,
            startValue3
        )

    }


    fun setValueRandom(image1: Int, image2: Int, image3: Int, numRotate: Int) {
        binding.nextImage.translationY = height.toFloat()
        binding.currentImage.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        binding.nextImage.animate().translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator) {
                }

                override fun onAnimationEnd(p0: Animator) {
                    setImage(
                        binding.curImage1,
                        binding.curImage2,
                        binding.curImage3,
                        oldValue1 % 9,
                        oldValue2 % 9,
                        oldValue3 % 9
                    )

                    binding.currentImage.translationY = 0f
                    if (oldValue1 < numRotate) {
                        setValueRandom(image1, image2, image3, numRotate)
                        oldValue1++
                        oldValue2++
                        oldValue3++
                    } else {
                        lastResult1 = 0
                        lastResult2 = 0
                        lastResult3 = 0
                        oldValue1 = 0
                        oldValue2 = 0
                        oldValue3 = 0
                        setImage(
                            binding.nextImage1,
                            binding.nextImage2,
                            binding.nextImage3,
                            image1,
                            image2,
                            image3
                        )
                        setImage(
                            binding.curImage1,
                            binding.curImage2,
                            binding.curImage3,
                            image1,
                            image2,
                            image3
                        )
                        eventEnd.eventEnd(image1 % 9, image2 % 9, image3 % 9, numRotate)
                    }
                }

                override fun onAnimationCancel(p0: Animator) {
                }

                override fun onAnimationRepeat(p0: Animator) {
                }


            }).start()
    }

    private fun setImage(
        curImage1: ImageView?,
        curImage2: ImageView?,
        curImage3: ImageView?,
        value1: Int,
        value2: Int,
        value3: Int
    ) {
        setImageDec(curImage1, value1)
        curImage1!!.tag = value1
        lastResult1 = value1
        setImageDec(curImage2, value2)
        curImage2!!.tag = value2
        lastResult2 = value2
        setImageDec(curImage3, value3)
        curImage3!!.tag = value3
        lastResult3 = value3

    }

    private fun setImageDec(curImage: ImageView?, value: Int) {
        when (value) {
            Util.a -> curImage!!.setImageResource(R.drawable.item_a)
            Util.bug -> curImage!!.setImageResource(R.drawable.item_bug)
            Util.dig -> curImage!!.setImageResource(R.drawable.item_dig)
            Util.man -> curImage!!.setImageResource(R.drawable.item_man)
            Util.hook -> curImage!!.setImageResource(R.drawable.item_hook)
            Util.n -> curImage!!.setImageResource(R.drawable.item_n)
            Util.pot -> curImage!!.setImageResource(R.drawable.item_pot)
            Util.q -> curImage!!.setImageResource(R.drawable.item_q)
            Util.stick -> curImage!!.setImageResource(R.drawable.item_stick)
        }

    }
}