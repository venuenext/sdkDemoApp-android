package com.venuenext.websdkdemo

import com.venuenext.vnwebsdk.protocol.VNAnalyticsInterface

typealias AnalyticsEventListener = (map: Map<String, Any>) -> Unit

class VNDemoAnalyticsInterface(val callback: AnalyticsEventListener): VNAnalyticsInterface {
    override fun trackAddItemToCart(
        itemId: String,
        itemName: String,
        itemCategory: String,
        variant: String,
        price: Double,
        quantity: Long
    ) {
        val eventData = mapOf(
            "itemId" to itemId,
            "itemName" to itemName,
            "itemCategory" to itemCategory,
            "variant" to variant,
            "price" to price,
            "quantity" to quantity
        )
        callback(
            createAnalyticsMap("trackAddItemToCart", eventData)
        )
    }

    override fun trackBeginCheckout() {
        callback(createAnalyticsMap("trackBeginCheckout"))
    }

    override fun trackCheckoutProgress(orderId: String, state: String) {
        val eventData = mapOf(
            "orderId" to orderId,
            "state" to state
        )
        callback(
            createAnalyticsMap("trackCheckoutProgress", eventData)
        )
    }

    override fun trackCompletedPurchase(
        orderId: String,
        quantity: Int,
        discount: Double,
        tips: Double,
        tax: Double,
        total: Double,
        paymentTypes: String,
        name: String?,
        email: String?
    ) {
        val eventData = mapOf(
            "orderId" to orderId,
            "quantity" to quantity,
            "discount" to discount,
            "tips" to tips,
            "tax" to tax,
            "total" to total,
            "paymentTypes" to paymentTypes,
            "name" to (name ?: "EMPTY"),
            "email" to (email ?: "EMPTY")
        )
        callback(
            createAnalyticsMap("trackCompletedPurchase", eventData)
        )
    }

    override fun trackMenuItemSelection(
        itemId: String,
        itemName: String,
        itemCategory: String,
        variant: String,
        price: Double
    ) {
        val eventData = mapOf(
            "itemId" to itemId,
            "itemName" to itemName,
            "itemCategory" to itemCategory,
            "variant" to variant,
            "price" to price
        )
        callback(
            createAnalyticsMap("trackMenuItemSelection", eventData)
        )
    }

    override fun trackRemoveItemFromCart(
        itemId: String,
        itemName: String,
        itemCategory: String,
        variant: String,
        price: Double,
        quantity: Long
    ) {
        val eventData = mapOf(
            "itemId" to itemId,
            "itemName" to itemName,
            "itemCategory" to itemCategory,
            "variant" to variant,
            "price" to price,
            "quantity" to quantity
        )
        callback(
            createAnalyticsMap("trackRemoveItemFromCart", eventData)
        )
    }

    override fun trackRvcSelection(itemId: String, itemName: String, itemCategory: String) {
        val eventData = mapOf(
            "itemId" to itemId,
            "itemName" to itemName,
            "itemCategory" to itemCategory
        )
        callback(
            createAnalyticsMap("trackRvcSelection", eventData)
        )
    }

    override fun trackUserAffiliations(affiliations: List<String>) {
        val eventData = mapOf(
            "affiliations" to affiliations
        )
        callback(
            createAnalyticsMap("trackUserAffiliations", eventData)
        )
    }

    override fun trackUserId(id: String, email: String?, name: String?) {
        val eventData = mapOf(
            "id" to id,
            "email" to (email ?: "EMPTY"),
            "name" to (name ?: "EMPTY")
        )
        callback(
            createAnalyticsMap("trackUserId", eventData)
        )
    }

    private fun createAnalyticsMap(eventName: String, eventData: Map<String, Any>? = null) = mapOf(
        "eventName" to eventName,
        "eventData" to (eventData ?: "EMPTY")
    )
}
