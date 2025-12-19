package com.example.dhakamrt

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dhakamrt.databinding.ActivityFareCalculatorBinding
import com.example.dhakamrt.ui.viewmodel.FareViewModel
import java.util.*

class FareCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFareCalculatorBinding
    private val viewModel: FareViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFareCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.stations.observe(this) { list ->
            val isBangla = Locale.getDefault().language == "bn"
            val names = list.map { if (isBangla) it.nameBn else it.nameEn }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                names
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.startSpinner.adapter = adapter
            binding.endSpinner.adapter = adapter
        }

        binding.calculateBtn.setOnClickListener {
            viewModel.calculateFare(
                binding.startSpinner.selectedItemPosition,
                binding.endSpinner.selectedItemPosition
            )
        }

        viewModel.fare.observe(this) {
            binding.fareText.text = getString(R.string.fare_result, it)
        }
    }
}
