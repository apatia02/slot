package com.example.slot.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.slot.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment : Fragment(), IEventEnd {
    private var countDown = 0
    private lateinit var binding: FragmentMainBinding
    private var betValue = 3
    private var score = 10000
    private val nameScore = "ScoreKey"
    private lateinit var sharedPreferences: SharedPreference

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        sharedPreferences = SharedPreference(this.requireContext())
        score = sharedPreferences.getValueInt(nameScore)
        binding.txtScore.text = score.toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnPlus.setOnClickListener {
            betValue++
            binding.bet.text = betValue.toString()
        }
        binding.btnMinus.setOnClickListener {
            if (betValue > 1)
                betValue--
            binding.bet.text = betValue.toString()
        }

        with(binding) {
            image1.setEventEnd(this@MainFragment)
            image2.setEventEnd(this@MainFragment)
            image3.setEventEnd(this@MainFragment)
            image4.setEventEnd(this@MainFragment)
            image5.setEventEnd(this@MainFragment)

            spin.setOnClickListener {
                spin.isEnabled = false
                image1.setValueRandom(
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(10) + 5
                )
                image2.setValueRandom(
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(10) + 5
                )
                image3.setValueRandom(
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(10) + 5
                )
                image4.setValueRandom(
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(10) + 5
                )
                image5.setValueRandom(
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(9),
                    Random.nextInt(10) + 5
                )

                score -= betValue
                txtScore.text = score.toString()
                sharedPreferences.save(nameScore, score)
            }
        }
    }


    override fun eventEnd(result1: Int, result2: Int, result3: Int, count: Int) {
        if (countDown < 4)
            countDown++
        else {
            binding.spin.isEnabled = true
            countDown = 0
            with(binding) {
                calculateResult(
                    image1.value1,
                    image2.value1,
                    image3.value1,
                    image4.value1,
                    image5.value1
                )
                calculateResult(
                    image1.value2,
                    image2.value2,
                    image3.value2,
                    image4.value2,
                    image5.value2
                )
                calculateResult(
                    image1.value3,
                    image2.value3,
                    image3.value3,
                    image4.value3,
                    image5.value3
                )
                txtScore.text = score.toString()
                sharedPreferences.save(nameScore, score)
            }
        }
    }

    private fun checkWin(
        value1: Int,
        value2: Int,
        value3: Int,
        value4: Int,
        value5: Int,
        num: Int
    ): Boolean {
        val list = listOf(value1, value2, value3, value4, value5)
        val numbersByElement = list.groupingBy { it }.eachCount()
        return numbersByElement.maxBy { it.value }.value == num
    }

    private fun calculateResult(
        value1: Int,
        value2: Int,
        value3: Int,
        value4: Int,
        value5: Int,
    ) {
        when {
            checkWin(
                value1,
                value2,
                value3,
                value4,
                value5,
                5
            ) ->
                score += betValue * 10
            checkWin(
                value1,
                value2,
                value3,
                value4,
                value5,
                4
            ) ->
                score += betValue * 6
            checkWin(
                value1,
                value2,
                value3,
                value4,
                value5,
                3
            ) ->
                score += betValue * 4
            checkWin(
                value1,
                value2,
                value3,
                value4,
                value5,
                2
            ) ->
                score += betValue * 2
        }

    }


}