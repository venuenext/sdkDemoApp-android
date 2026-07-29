package com.venuenext.websdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import com.venuenext.vnwebsdk.VenueNextWeb
import com.venuenext.websdkdemo.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.venuenext.vnwebsdk.models.User
import com.venuenext.websdkdemo.ticketing.VNDemoTicketingAPI

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navHost by lazy { binding.mainFragmentContainer.findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initVN()
        handleIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        setupWithNavController(binding.bottomNavigationView, navHost)
    }

    override fun onNewIntent(newIntent: Intent?) {
        super.onNewIntent(newIntent)
        handleIntent(newIntent)
    }

    private fun handleIntent(newIntent: Intent?) {
        if (newIntent?.action == Intent.ACTION_VIEW) {
            newIntent.data?.let {
                if (VenueNextWeb.canHandleDeepLink(it)) {
                    VenueNextWeb.handleDeepLink(binding.root, it, this)
                }
            }
        }
    }

    private fun initVN() {
        VenueNextWeb.initialize("arena","arena", "stg")
        VenueNextWeb.privateKeyAssetName = "private_key.pem"
        VenueNextWeb.configureAnalytics(
            VNDemoAnalyticsInterface(::onAnalyticsEvent)
        )
        setUserIfNeeded()
    }

    private fun setUserIfNeeded() {
        val ticketingPrefs = getSharedPreferences("TICKETING_PREFS", Context.MODE_PRIVATE)
        val userJSON = ticketingPrefs.getString("demoUser", null)
        if (userJSON.isNullOrEmpty()) {
            return
        }

        VNDemoTicketingAPI(ticketingPrefs).getDemoUser { user ->
            user?.let {
                VenueNextWeb.setUser(
                    User(
                        id = it.userID,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        email = it.email,
                        phoneNumber = it.phoneNumber
                    )
                )
            }
        }
    }

    private fun onAnalyticsEvent(map: Map<String, Any>) {
        val message = "Analytic Event:\r\n\t$map"
        Log.i("VN_ANALYTIC_EVENT", message)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
