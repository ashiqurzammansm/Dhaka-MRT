package com.example.dhakamrt

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.dhakamrt.databinding.ActivityMainBinding
import com.example.dhakamrt.ui.viewmodel.CardViewModel

/**
 * MainActivity
 * Home screen of Dhaka MRT app
 * Handles NFC card tap and navigation
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null

    private val cardViewModel: CardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // Navigate to Fare Calculator
        binding.fareBtn.setOnClickListener {
            startActivity(Intent(this, FareCalculatorActivity::class.java))
        }

        // Observe card data from Room
        cardViewModel.cardData.observe(this) { card ->
            if (card != null) {

                binding.balanceText.text =
                    getString(R.string.balance_value, card.balance)

                binding.statusText.text =
                    if (cardViewModel.isLowBalance(card.balance))
                        getString(R.string.low_balance_warning)
                    else
                        getString(R.string.balance_ok)
            }
        }
    }

    /**
     * VERY IMPORTANT:
     * Signature must be Intent (NOT Intent?)
     */
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        if (tag != null) {
            val uid = tag.id.joinToString("") { byte ->
                String.format("%02X", byte)
            }

            cardViewModel.saveCard(uid)
            cardViewModel.readCard(uid)
        }
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    private fun enableNfcForegroundDispatch() {
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val filters = arrayOf(
            IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        )

        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            filters,
            null
        )
    }
}
