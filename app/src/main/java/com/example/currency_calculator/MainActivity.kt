package com.example.currency_calculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.currency_calculator.R

class MainActivity : AppCompatActivity() {

    private lateinit var editTextSourceAmount: EditText
    private lateinit var editTextTargetAmount: EditText
    private lateinit var spinnerSourceCurrency: Spinner
    private lateinit var spinnerTargetCurrency: Spinner
    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "JPY" to 110.0,
        "VND" to 23000.0
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextSourceAmount = findViewById(R.id.editTextSourceAmount)
        editTextTargetAmount = findViewById(R.id.editTextTargetAmount)
        spinnerSourceCurrency = findViewById(R.id.spinnerSourceCurrency)
        spinnerTargetCurrency = findViewById(R.id.spinnerTargetCurrency)

        setupSpinners()

        editTextSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convertCurrency()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        spinnerSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertCurrency()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, exchangeRates.keys.toList())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSourceCurrency.adapter = adapter
        spinnerTargetCurrency.adapter = adapter
    }

    private fun convertCurrency() {
        val sourceCurrency = spinnerSourceCurrency.selectedItem.toString()
        val targetCurrency = spinnerTargetCurrency.selectedItem.toString()
        val sourceAmountText = editTextSourceAmount.text.toString()

        if (sourceAmountText.isNotEmpty()) {
            val sourceAmount = sourceAmountText.toDouble()
            val targetAmount = sourceAmount * getConversionRate(sourceCurrency, targetCurrency)
            editTextTargetAmount.setText(String.format("%.2f", targetAmount))
        } else {
            editTextTargetAmount.setText("")
        }
    }

    private fun getConversionRate(sourceCurrency: String, targetCurrency: String): Double {
        val sourceRate = exchangeRates[sourceCurrency] ?: 1.0
        val targetRate = exchangeRates[targetCurrency] ?: 1.0
        return targetRate / sourceRate
    }
}
