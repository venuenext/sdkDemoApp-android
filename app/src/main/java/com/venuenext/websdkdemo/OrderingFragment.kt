package com.venuenext.websdkdemo

import android.R.layout
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.venuenext.vnwebsdk.VNNavigationController
import com.venuenext.vnwebsdk.types.ServiceType
import com.venuenext.websdkdemo.databinding.FragmentOrderingBinding

private const val ALL_RVCS = "Show All Revenue Centers"
private const val ALL_FOOD = "Show All Food And Beverage Menus"
private const val SPECIFIC_FOOD = "Show A Specific Food And Beverage Menu"
private const val ALL_MERCH = "Show All Merchandise Menus"
private const val SPECIFIC_MERCH = "Show A Specific Merchandise Menu"
private const val ALL_EXPERIENCE = "Show All Experience Menus"
private const val SPECIFIC_EXPERIENCE = "Show A Specific Experience Menu"
private const val PICKUP = "Filter For Pickup"
private const val DELIVERY = "Filter For Delivery"
private const val ADVANCED_FILTERING_RVCS = "All Revenue Centers Advanced Filtering"
private const val ADVANCED_FILTERING_FOOD = "All Food And Beverage Advanced Filtering"
private const val ADVANCED_FILTERING_MERCH = "All Merchandise Advanced Filtering"
private const val SPECIFIC_FOOD_ITEM = "Show A Specific Food Item"
private const val SPECIFIC_MERCH_ITEM = "Show A Specific Merchandise Item"
private const val SPECIFIC_EXPERIENCE_EVENT = "Show A Specific Experience Menu With An Event Pre-Populated"
private const val SPECIFIC_EXPERIENCE_EVENT_DETAILS = "Show A Detail View For And Experience Item"

class OrderingFragment : Fragment() {
    private val binding by lazy { FragmentOrderingBinding.inflate(layoutInflater) }

    private val demoMethods = arrayOf(
        ALL_RVCS,
        ALL_FOOD,
        SPECIFIC_FOOD,
        ALL_MERCH,
        SPECIFIC_MERCH,
        ALL_EXPERIENCE,
        SPECIFIC_EXPERIENCE,
        PICKUP,
        DELIVERY,
        ADVANCED_FILTERING_RVCS,
        ADVANCED_FILTERING_FOOD,
        ADVANCED_FILTERING_MERCH,
        SPECIFIC_FOOD_ITEM,
        SPECIFIC_MERCH_ITEM,
        SPECIFIC_EXPERIENCE_EVENT,
        SPECIFIC_EXPERIENCE_EVENT_DETAILS,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.orderingList.adapter = ArrayAdapter(
            requireContext(),
            layout.simple_list_item_1,
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

    private fun setListViewOnClickListeneter() = binding.orderingList.setOnItemClickListener { _, _, position, _ ->
        handleSelection(demoMethods[position])
    }

    // TODO: Update IDs as needed
    private fun handleSelection(selection: String) {
        when(selection) {
            ALL_RVCS -> VNNavigationController.showRvCs(requireContext())
            ALL_FOOD -> VNNavigationController.showFnB(requireContext())
            SPECIFIC_FOOD -> VNNavigationController.showFnBMenu(
                "<YOUR_MENU_ID>",
                requireContext()
            )
            ALL_MERCH -> VNNavigationController.showMerchandise(requireContext())
            SPECIFIC_MERCH -> VNNavigationController.showMerchandiseMenu(
                "<YOUR_MENU_ID>",
                requireContext()
            )
            ALL_EXPERIENCE -> VNNavigationController.showExperiences(requireContext())
            SPECIFIC_EXPERIENCE -> VNNavigationController.showExperienceMenu(
                "<YOUR_MENU_ID>",
                "<YOUR_EVENT_ID>",
                requireContext()
            )
            PICKUP -> VNNavigationController.showFnB(
                ServiceType.PICKUP,
                requireContext()
            )
            DELIVERY -> VNNavigationController.showFnB(
                ServiceType.DELIVERY,
                requireContext()
            )
            ADVANCED_FILTERING_RVCS -> VNNavigationController.showRvCs(
                noWaitTimes = true,
                serviceType = ServiceType.DELIVERY,
                locations = listOf("Section 100", "Section 101"),
                categories = listOf("Beer", "Yummy"),
                requireContext()
            )
            ADVANCED_FILTERING_FOOD -> VNNavigationController.showFnB(
                noWaitTimes = true,
                serviceType = ServiceType.DELIVERY,
                locations = listOf("Section 100", "Section 101"),
                categories = listOf("Beer", "Yummy"),
                requireContext()
            )
            ADVANCED_FILTERING_MERCH -> VNNavigationController.showMerchandise(
                noWaitTimes = true,
                serviceType = ServiceType.DELIVERY,
                locations = listOf("Section 100", "Section 101"),
                categories = listOf("Beer", "Yummy"),
                requireContext()
            )
            SPECIFIC_FOOD_ITEM -> VNNavigationController.showFoodMenuItem(
                "<YOUR_MENU_ID>",
                "<YOUR_ITEM_ID>",
                requireContext()
            )
            SPECIFIC_MERCH_ITEM -> VNNavigationController.showMerchandiseMenuItem(
                "<YOUR_MENU_ID>",
                "<YOUR_ITEM_ID>",
                requireContext()
            )
            SPECIFIC_EXPERIENCE_EVENT -> VNNavigationController.showExperienceDetails(
                "<YOUR_MENU_ID>",
                "<YOUR_ITEM_ID>",
                null,
                null,
                requireContext()
            )
            SPECIFIC_EXPERIENCE_EVENT_DETAILS -> VNNavigationController.showExperienceDetails(
                "<YOUR_MENU_ID>",
                "<YOUR_ITEM_ID>",
                "<YOUR_EVENT_ID>",
                "<YOUR_VARIANT_ID>",
                requireContext()
            )
            else -> Toast.makeText(requireContext(), "Unsupported selection.", Toast.LENGTH_SHORT).show()
        }
    }
}