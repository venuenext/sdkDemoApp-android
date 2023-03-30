package com.venuenext.websdkdemo.ticketing

import android.content.SharedPreferences
import android.util.Log

/*
 TODO: This is a contrived example of a ticketing integration.
 For best results, please test with your own ticketing integration.

 *** DISCLOSURE ***
 Using this fake demo api will create this user in your org.
 */
private const val DEMO_USER_KEY = "demoUser"

class VNDemoTicketingAPI(private val sharedPrefs: SharedPreferences) {
    fun login(completion: (VNDemoTicketingUser) -> Unit) {
        val demoTicketingUser = VNDemoTicketingUser(
            "aaaa-5555-vndemo",
            "John",
            "Doe",
            "john.doe@test.com",
            "(555)555-5555"
        )
        sharedPrefs.edit()
            .putString(DEMO_USER_KEY, demoTicketingUser.toJSON())
            .apply()
        completion(demoTicketingUser)
    }

    fun logout(completion: () -> Unit) {
        sharedPrefs.edit().clear().apply()
        completion()
    }

    fun getDemoUser(completion: (VNDemoTicketingUser?) -> Unit) {
        val demoUserJSON = sharedPrefs.getString(DEMO_USER_KEY, null)
        if (demoUserJSON.isNullOrEmpty()) {
            Log.e("VNDemoTicketingAPI", "Demo user is null")
            completion(null)
        } else {
            val demoTicketingUser = VNDemoTicketingUser.fromJSON(demoUserJSON)
            completion(demoTicketingUser)
        }
    }
}
