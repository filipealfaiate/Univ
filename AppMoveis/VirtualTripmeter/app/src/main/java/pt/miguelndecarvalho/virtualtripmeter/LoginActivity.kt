package pt.miguelndecarvalho.virtualtripmeter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private const val  RC_SIGN_IN = 18943

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (auth.currentUser != null) {
            authenticated()
        }

        else{
            setupButton()
            nonAuthenticated()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN && resultCode == Activity.RESULT_OK) {
            val user = Firebase.auth.currentUser

            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Toast.makeText(applicationContext, getString(R.string.login_welcome, user?.displayName) , Toast.LENGTH_SHORT).show()
        }
    }

    private fun authenticated(){
        val intent = Intent(this, MainActivity::class.java)
        val user = Firebase.auth.currentUser

        startActivity(intent)
        finish()
        Toast.makeText(applicationContext, getString(R.string.login_back, user?.displayName) , Toast.LENGTH_SHORT).show()
    }

    private fun nonAuthenticated(){
        callLoginUI()
    }

    private fun setupButton(){
        findViewById<Button>(R.id.login_button).setOnClickListener {
            callLoginUI()
        }
    }

    private fun callLoginUI(){

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.GitHubBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.FirebaseUITheme)
                .setTosAndPrivacyPolicyUrls("https://virtual-tripmeter.miguelndecarvalho.pt/terms-of-service.html",
                    "https://virtual-tripmeter.miguelndecarvalho.pt/privacy-policy.html")
                .setLogo(R.drawable.ic_icon_login_ui)
                .build(),
            RC_SIGN_IN
        )
    }

}