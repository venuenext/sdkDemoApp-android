package com.venuenext.websdkdemo.ticketing

import android.util.Log
import com.google.gson.Gson

data class VNDemoTicketingUser(
    val userID: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String
) {
    companion object {
        fun fromJSON(string: String): VNDemoTicketingUser? {
            return try {
                Gson().fromJson(string, VNDemoTicketingUser::class.java)
            } catch (e: Exception) {
                Log.e("VNDemoTicketingUser", "Failed to parse user from JSON: $string", e)
                null
            }
        }
    }

    fun toJSON() = Gson().toJson(this)
}
