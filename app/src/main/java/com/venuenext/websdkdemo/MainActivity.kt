package com.venuenext.websdkdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.venuenext.vnwebsdk.VNNavigationController
import com.venuenext.vnwebsdk.VenueNextWeb
import com.venuenext.websdkdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*
        Use this method signature to initialize for an environment other than production
        This would point to <INSTANCE_NAME>-<ENVIRONMENT>.ordernext.com
        */

        VenueNextWeb.initialize("arena","arena", "dev")
        VenueNextWeb.privateKeyAssetName = "arena_dev_pkcs8_private_key.pem"
        VNNavigationController.showRvCs(this)
    }
}