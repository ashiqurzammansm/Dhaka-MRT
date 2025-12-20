package com.example.dhakamrt.ui.home

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dhakamrt.R
import com.example.dhakamrt.databinding.FragmentHomeBinding
import com.example.dhakamrt.ui.viewmodel.CardViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var nfcAdapter: NfcAdapter? = null

    private val cardViewModel: CardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())
        updateNfcUi()
        enableNfc()
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(requireActivity())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNewIntent(intent: Intent) {
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        tag?.let {
            val uid = it.id.joinToString("") { b -> "%02X".format(b) }
            cardViewModel.saveCard(uid)
            cardViewModel.readCard(uid)
        }
    }

    private fun updateNfcUi() {
        if (nfcAdapter == null || nfcAdapter?.isEnabled == false) {

            binding.nfcIcon.setImageResource(android.R.drawable.ic_dialog_alert)
            binding.nfcTitle.text = getString(R.string.nfc_disabled_title)
            binding.nfcMessage.text = getString(R.string.nfc_disabled_message)

        } else {

            binding.nfcIcon.setImageResource(android.R.drawable.ic_menu_share)
            binding.nfcTitle.text = getString(R.string.tap_card_title)
            binding.nfcMessage.text = getString(R.string.tap_card_message)
        }

        cardViewModel.cardData.observe(viewLifecycleOwner) { card ->
            if (card != null) {
                binding.balanceText.text =
                    getString(R.string.balance_value, card.balance)
            }
        }
    }

    private fun enableNfc() {
        if (nfcAdapter?.isEnabled == true) {

            val intent = Intent(requireContext(), requireActivity()::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

            val pendingIntent = PendingIntent.getActivity(
                requireContext(),
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )

            nfcAdapter?.enableForegroundDispatch(
                requireActivity(),
                pendingIntent,
                arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)),
                null
            )
        }
    }
}
