## How to Use the Demo App
Simply search for the comments marked as "TODO" throughout the demo app.

### Initialization
First, add your `.pem` file to the project as described in the [integrator documentation](https://venuenext.github.io/android-2/quick-start-guide/). Then, find the following TODO's in the `MainActivity.kt` file and adjust the values accordingly:

```kotlin
/**
 * TODO: Before the VN SDK can be used, it must be initialized.
 * The `org`, `instance`, and env must be correct.
 * You should receive these values from the VenueNext team. `env` is optional.
 * Omit `env` to point to your production environment, or include it to point to other environments,
 * like "qa" or "dev" (the environment must be created by VN, however).
 */
VenueNextWeb.initialize("<YOUR_ORG>","<YOUR_INSTANCE>")
```

```kotlin
/**
 * TODO: A .pem file is needed for authenticating user.
 * If you are planning on allowing users to log in with external accounts, the .pem file must
 * be set. The .pem file should be received from the VenueNext team. You will also need to
 * import your .pem file to Android Studio in the assets resource directory.
 */
VenueNextWeb.privateKeyAssetName = "<YOUR_PEM_FILE_NAME>.pem"
```

You can demo using a custom theme by updating the contents of the `vn_colors.xml` file.

Custom theme:
```xml
<!--
TODO: This is an example of customizing the app colors.
See: https://venuenext.github.io/android-2/color-scheme/
-->
<color name="vnColorPrimary">#0077C0</color>
<color name="vnColorOnPrimary">#C4CED4</color>
```

### Ticketing
Use the contrived ticketing example as is, or replace the `VNDemoTicketingAPI.kt` file with your own ticketing integration.
```kotlin
/*
 TODO: This is a contrived example of a ticketing integration.
 For best results, please test with your own ticketing integration.

 *** DISCLOSURE ***
 Using this fake demo api will create this user in your org.
 */

...

class VNDemoTicketingAPI(private val sharedPrefs: SharedPreferences) {...}
```

Remember, it is the integrator's responsibility to track the status of external logins. If an external ticketing library is being used, and the login persists through several app sessions, the integrator should check the status of that logged in user and pass user data to the VN SDK when appropriate before launching VN flows.

```kotlin
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

        try {
            val user = Gson().fromJson(userJSON, VNDemoTicketingUser::class.java)
            VenueNextWeb.setUser(
                User(
                    id = user.userID,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    phoneNumber = user.phoneNumber
                )
            )
        }
        catch (e: Exception) {
            Log.e("MainActivity", "Error parsing user: $userJSON", e)
        }
    }
```

### Ordering and Wallet Methods
To test the ordering and wallet flows, you may need to plug in values to the VN SDK calls in the `private fun handleSelection(selection: String)` methods (in both the `OrderingFragment.kt` and `WalletFragment.kt` files) with values from your own organization (that match up with the `.pem` file being used) to properly demo full functionality.
