package com.venuenext.websdkdemo.ticketing

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog

/*
 TODO: This is a contrived example of a ticketing integration.
 For best results, please test with your own ticketing integration.

 *** DISCLOSURE ***
 Using this fake demo api will create this user in your org.
 */
private const val DEMO_USER_KEY = "demoUser"

class VNDemoTicketingAPI(private val sharedPrefs: SharedPreferences) {
    fun login(context: Context, completion: (VNDemoTicketingUser) -> Unit) {
        val userIDEditText = EditText(context).apply { hint = "ID" }
        val firstNameEditText = EditText(context).apply { hint = "First Name" }
        val lastNameEditText = EditText(context).apply { hint = "Last Name" }
        val emailEditText = EditText(context).apply { hint = "Email" }
        val phoneEditText = EditText(context).apply { hint = "Phone Number" }
        val providerEditText = EditText(context).apply { hint = "Provider" }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(20, 20, 20, 20) //set margin

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        layout.addView(userIDEditText)
        layout.addView(firstNameEditText)
        layout.addView(lastNameEditText)
        layout.addView(emailEditText)
        layout.addView(phoneEditText)
        layout.addView(providerEditText)

        val dialog = AlertDialog.Builder(context)
            .setTitle("VN Demo Ticketing User - Login")
            .setView(layout)
            .setPositiveButton("OK") { _, _ ->
                val demoTicketingUser = VNDemoTicketingUser(
                    userIDEditText.text.toString().trim(),
                    firstNameEditText.text.toString().trim(),
                    lastNameEditText.text.toString().trim(),
                    emailEditText.text.toString().trim(),
                    phoneEditText.text.toString().trim().ifEmpty { null },
                    providerEditText.text.toString().trim().ifEmpty { null }
                )

                sharedPrefs.edit()
                    .putString(DEMO_USER_KEY, demoTicketingUser.toJSON())
                    .apply()
                completion(demoTicketingUser)
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
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
