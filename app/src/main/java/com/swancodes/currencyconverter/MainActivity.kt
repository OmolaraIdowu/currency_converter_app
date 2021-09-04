package com.swancodes.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.swancodes.currencyconverter.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {

    private var nairaChar: Char = '\u20A6'
    private var bitcoinChar: Char = '\u20BF'
    private var ngnRate = 20534193.66

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        binding.btnConvert.setOnClickListener {
            convert()
        }

        binding.roundUpSwitch.setOnCheckedChangeListener { _, _ ->
            binding.tvRate.text = ""
        }
    }

    private fun convert() {
        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
        val ngnAmountEntered = binding.ngnAmount.text.toString().toDouble()

        var btcValue = ngnAmountEntered / ngnRate
        val convertTwoDp = BigDecimal(btcValue).setScale(2, RoundingMode.UP).toString()
        println(btcValue)
        println(convertTwoDp)
        binding.btcAmount.text = convertTwoDp.toEditable()


        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {
            btcValue = kotlin.math.ceil(btcValue)
            Log.d(
                "MainActivity",
                "$nairaChar, $ngnAmountEntered, $bitcoinChar, $convertTwoDp, $btcValue"
            )

            binding.tvRate.text = getString(
                R.string.rateWholeNo,
                nairaChar.toString(),
                ngnAmountEntered.toString(),
                bitcoinChar.toString(),
                btcValue.toString()
            )
        } else {
            binding.tvRate.text = getString(
                R.string.rateTwoDp,
                nairaChar.toString(),
                ngnAmountEntered.toString(),
                bitcoinChar.toString(),
                convertTwoDp
            )
        }
    }
}