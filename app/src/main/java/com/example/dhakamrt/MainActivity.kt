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
 * Main screen
 * Shows balance and low balance warning
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var nfcAdapter: NfcAdapter? = null

    private val cardViewModel: CardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.balanceText.text = getString(R.string.balance_default)
        binding.statusText.text = getString(R.string.status_tap_card)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        if (nfcAdapter == null) {
            binding.statusText.text = getString(R.string.nfc_not_supported)
        }

        // Observe Room database
        cardViewModel.cardData.observe(this) { card ->
            if (card != null) {

                binding.balanceText.text =
                    getString(R.string.balance_value, card.balance)

                if (cardViewModel.isLowBalance(card.balance)) {
                    binding.statusText.text =
                        getString(R.string.low_balance_warning)
                } else {
                    binding.statusText.text =
                        getString(R.string.balance_ok)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableNfcForegroundDispatch()
    }

    override fun onPause() {
        super.onPause()
        disableNfcForegroundDispatch()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val tag: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        if (tag != null) {

            val uid = tag.id.joinToString("") {
                String.format("%02X", it)
            }

            cardViewModel.saveCard(uid)
            cardViewModel.readCard(uid)
        }
    }

    private fun enableNfcForegroundDispatch() {
        val intent = Intent(this, javaClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE
        )

        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))

        nfcAdapter?.enableForegroundDispatch(
            this,
            pendingIntent,
            filters,
            null
        )
    }

    private fun disableNfcForegroundDispatch() {
