package com.example.laboratorio02viana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var weight: EditText
    private lateinit var height: EditText
    private lateinit var calculate: Button
    private lateinit var result: TextView
    private lateinit var status: TextView
    private lateinit var range: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bind()
        setListener()

    }

    private fun bind(){
        weight = findViewById(R.id.weight_edit_text)
        height = findViewById(R.id.height_edit_text)
        calculate = findViewById(R.id.calculate_btn)
        result = findViewById(R.id.result_text_view)
        status = findViewById(R.id.status_text_view)
        range = findViewById(R.id.range_text_view)
    }

    private fun setListener(){
        calculate.setOnClickListener {
            val peso = weight.text.toString()
            val altura = height.text.toString()

            if(!validateInput(peso, altura)){
                clearTextView()
                return@setOnClickListener
            }

            val bmi = calculatebmi(peso.toFloat(), altura.toFloat())
            val bmiTwoDig = String.format("%.2f", bmi).toFloat()

            clearFocus()
            displayResult(bmiTwoDig)
        }
    }

    private fun validateInput(weightt: String?, heightt: String?): Boolean {
        when{
            weightt.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_SHORT).show()
                return false
            }
            heightt.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun calculatebmi(weight: Float, height: Float): Float {
        return weight / ((height/100) * (height/100))
    }

    private fun displayResult(bmi: Float) {
        result.text = bmi.toString()
        range.text = "(Normal range is 18.80 - 24.9)"

        var informationRsult = ""
        var color = 0

        when{
            bmi < 18.5 -> {
                informationRsult = "Underweight"
                color = R.color.under_weight
            }
            bmi in 18.5..24.99 -> {
                informationRsult = "Healthy"
                color = R.color.normal_weight
            }
            bmi in 25.0..29.99 -> {
                informationRsult = "Overweight"
                color = R.color.over_weight
            }
            bmi > 30 -> {
                informationRsult = "Obese"
                color = R.color.obese
            }
        }

        status.text = informationRsult
        status.setTextColor(ContextCompat.getColor(this, color))
    }


    private fun clearFocus(){
        weight.clearFocus()
        height.clearFocus()
    }

    private fun clearTextView() {
        result.text = ""
        status.text = ""
        range.text = ""
    }

}