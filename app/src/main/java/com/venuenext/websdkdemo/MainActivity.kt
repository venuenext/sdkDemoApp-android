package com.venuenext.websdkdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.venuenext.vnwebsdk.VenueNextWeb
import com.venuenext.websdkdemo.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.gson.Gson
import com.venuenext.vnwebsdk.models.User
import com.venuenext.websdkdemo.ticketing.VNDemoTicketingAPI
import com.venuenext.websdkdemo.ticketing.VNDemoTicketingUser

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navHost by lazy { binding.mainFragmentContainer.findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initVN()
    }

    override fun onStart() {
        super.onStart()
        setupWithNavController(binding.bottomNavigationView, navHost)
    }

    private fun initVN() {
        /**
         * TODO: Before the VN SDK can be used, it must be initialized.
         * The `org`, `instance`, and env must be correct.
         * You should receive these values from the VenueNext team. `env` is optional.
         * Omit `env` to point to your production environment, or include it to point to other environments,
         * like "qa" or "dev" (the environment must be created by VN, however).
         */
        VenueNextWeb.initialize("<YOUR_ORG>","<YOUR_INSTANCE>")

        /**
         * TODO: A .pem file is needed for authenticating user.
         * If you are planning on allowing users to log in with external accounts, the .pem file must
         * be set. The .pem file should be received from the VenueNext team. You will also need to
         * import your .pem file to Android Studio in the assets resource directory.
		 */
        VenueNextWeb.privateKeyAssetName = "<YOUR_PEM_FILE>.pem"
        setUserIfNeeded()
    }

    /**
     * TODO: The user must be set with each app launch.
     * VN does not save the login. If a user is already logged in to your app, don't forget set the VN User. You can experiment with this fake user and contrived ticketing API, or implement your own ticketing integration here.
     * > Note: See "External login providers" in the VN SDK documentation for more info on supported providers.
     * > https://venuenext.github.io/android-2/quick-start-guide/
     * */
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
}