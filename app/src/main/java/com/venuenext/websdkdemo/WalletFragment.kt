package com.venuenext.websdkdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.venuenext.vnwebsdk.VNNavigationController
import com.venuenext.websdkdemo.databinding.FragmentWalletBinding

private const val MY_INFO = "My Info"
private const val BADGE = "Badge"
private const val WALLET = "Wallet"
private const val SCAN_AND_PAY = "Scan And Pay"
private const val VC_ACTIVITY = "Virtual Currency Activity"
private const val SEND_VC = "Send Virtual Currency"
private const val BENEFITS = "Benefits And Rewards"
private const val PAYMENTS = "Payments"
private const val SCANNER = "Scanner"
private const val QR_CODE = "QR Code"
private const val ORDER_HISTORY = "Order History"
private const val RECEIPT = "Order Receipt"

class WalletFragment : Fragment() {
    private val binding by lazy { FragmentWalletBinding.inflate(layoutInflater) }

    private val demoMethods = arrayOf(
        MY_INFO,
        BADGE,
        WALLET,
        SCAN_AND_PAY,
        VC_ACTIVITY,
        SEND_VC,
        BENEFITS,
        PAYMENTS,
        SCANNER,
        QR_CODE,
        ORDER_HISTORY,
        RECEIPT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.walletList.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            demoMethods
        )
        setListViewOnClickListeneter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setListViewOnClickListeneter() = binding.walletList.setOnItemClickListener { _, _, position, _ ->
        handleSelection(demoMethods[position])
    }

    // TODO: Update IDs as needed
    private fun handleSelection(selection: String) {
        when(selection) {
            MY_INFO -> VNNavigationController.showMyInfo(requireContext())
            BADGE -> VNNavigationController.showBadge(requireContext())
            WALLET -> VNNavigationController.showWallet(requireContext())
            SCAN_AND_PAY -> VNNavigationController.showScanAndPay(requireContext())
            VC_ACTIVITY -> VNNavigationController.showVirtualCurrencyActivity(requireContext())
            SEND_VC -> VNNavigationController.showSendVirtualCurrency(requireContext())
            BENEFITS -> VNNavigationController.showBenefitsAndRewards(requireContext())
            PAYMENTS -> VNNavigationController.showPayments(requireContext())
            SCANNER -> VNNavigationController.showScanner(requireContext())
            QR_CODE -> VNNavigationController.showQrCode(requireContext())
            ORDER_HISTORY -> VNNavigationController.showOrderHistory(requireContext())
            RECEIPT -> VNNavigationController.showOrderReceipt("YOUR_RECEIPT_ID", requireContext())
            else -> Toast.makeText(requireContext(), "Unsupported selection.", Toast.LENGTH_SHORT).show()
        }
    }
}