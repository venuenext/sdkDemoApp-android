package com.venuenext.websdkdemo.ticketing

import android.R.layout
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.Gson
import com.venuenext.vnwebsdk.VenueNextWeb
import com.venuenext.vnwebsdk.models.User
import com.venuenext.websdkdemo.databinding.FragmentTicketingBinding

private const val LOGIN = "Login"
private const val LOGOUT = "Logout"

class TicketingFragment: Fragment() {
    private val binding by lazy { FragmentTicketingBinding.inflate(layoutInflater) }
    private val ticketingAPI by lazy { VNDemoTicketingAPI(ticketingSharedPrefs) }
    private val ticketingSharedPrefs by lazy {
        requireActivity().getSharedPreferences("TICKETING_PREFS", Context.MODE_PRIVATE)
    }

    private val demoMethods = arrayOf(
        LOGIN,
        LOGOUT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.ticketingList.adapter = ArrayAdapter(
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

    private fun setListViewOnClickListeneter() = binding.ticketingList.setOnItemClickListener { _, _, position, _ ->
        handleSelection(demoMethods[position])
    }

    /*
	 TODO: This is a contrived login/logout flow. You can use this to test with a fake user, or you
	  can replace with your own ticketing integration to test with real users.
	 */
    private fun handleSelection(selection: String) {
        when(selection) {
            LOGIN -> handleLogin()
            LOGOUT -> handleLogout()
            else -> Toast.makeText(requireContext(), "Unsupported selection.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLogin() {
        val userJSON = ticketingSharedPrefs.getString("demoUser", null)
        if (!userJSON.isNullOrEmpty()) {
            val user = Gson().fromJson(userJSON, VNDemoTicketingUser::class.java)
            val message = "${user.firstName} ${user.lastName} is already logged in."
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            return
        }

        ticketingAPI.login(requireContext()) { user ->
            setVNUser(user)
            val message = "Successfully logged in ${user.firstName} ${user.lastName}"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLogout() {
        ticketingAPI.logout {
            logoutUser()
            Toast.makeText(requireContext(), "Successfully logged out.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setVNUser(user: VNDemoTicketingUser) = VenueNextWeb.setUser(
        User(
            id = user.userID,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phoneNumber = user.phoneNumber
        )
    )

    private fun logoutUser() = VenueNextWeb.logUserOut()
}