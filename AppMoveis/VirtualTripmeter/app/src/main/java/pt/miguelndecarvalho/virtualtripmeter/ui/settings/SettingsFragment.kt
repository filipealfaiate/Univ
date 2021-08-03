package pt.miguelndecarvalho.virtualtripmeter.ui.settings

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pt.miguelndecarvalho.virtualtripmeter.LoginActivity
import pt.miguelndecarvalho.virtualtripmeter.R


class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private val user = Firebase.auth.currentUser
    private var dbToggleImperialUnits:Boolean = false
    private var dbBaseSpeed:String = ""
    private var dbToggleAlertSounds:Boolean = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)

        var mView = inflater.inflate(R.layout.fragment_settings, container, false)

        mView.findViewById<Button>(R.id.logout_button).setOnClickListener{
            logout()
        }

        mView.findViewById<Button>(R.id.delete_button).setOnClickListener{
            delete()
        }

        checkSettings(mView)

        return mView
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(requireContext().applicationContext)
            .addOnCompleteListener {
                val intent = Intent(requireContext().applicationContext, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(requireContext().applicationContext, getString(R.string.login_logout_toast, user?.displayName) , Toast.LENGTH_LONG).show()
            }
    }

    private fun delete() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.login_delete_dialog_title)
        builder.setMessage(R.string.login_delete_dialog_description)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Yes"){dialogInterface, which ->
            Log.d(tag,"User chose to delete account")
            deleteAccount()
        }

        builder.setNegativeButton("No"){dialogInterface, which ->
            Log.d(tag,"User chose not to delete account")
        }


        val alertDialog: AlertDialog = builder.create()

        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun deleteAccount()
    {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore

        AuthUI.getInstance()
            .delete(requireContext().applicationContext)
            .addOnCompleteListener {
                db.collection("userSettings").document(userID)
                    .delete()
                    .addOnSuccessListener {
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(activity, getString(R.string.login_delete_toast, user?.displayName) , Toast.LENGTH_LONG).show()
                    }
            }
    }

    private fun checkSettings(mView: View)
    {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore
        val doc = db.collection("userSettings").document(userID)

        doc.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    getSettings(mView)
                } else {
                    createDefaultDocument(mView)
                }
            }
    }

    private fun existsSettings(): Boolean {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore
        var temp=false
        val doc = db.collection("userSettings").document(userID)

        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    temp=true
                }
            }

        return temp
    }

    private fun createDefaultDocument(mView: View)
    {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore


        val defaultSettings = hashMapOf(
            "toggleImperialUnits" to false,
            "baseSpeed" to "100",
            "toggleAlertSounds" to false
        )

        db.collection("userSettings")
            .document(userID)
            .set(defaultSettings)
            .addOnSuccessListener { document ->
                getSettings(mView)
            }
    }

    private fun getSettings(mView:View)
    {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore


        val doc = db.collection("userSettings").document(userID)

        doc.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    dbToggleImperialUnits = document.getBoolean("toggleImperialUnits") == true
                    dbBaseSpeed = document.getString("baseSpeed")!!
                    dbToggleAlertSounds = document.getBoolean("toggleAlertSounds") == true

                    if (dbToggleImperialUnits != null && dbBaseSpeed != null && dbToggleAlertSounds != null) {
                        applySettings(mView,dbToggleImperialUnits, dbBaseSpeed, dbToggleAlertSounds)
                        createListeners(mView)
                    }
                }
            }
    }

    private fun applySettings(mView:View, toggleImperialUnits:Boolean, baseSpeed:String, toggleAlertSounds:Boolean)
    {
        mView.findViewById<Switch>(R.id.settingsSwitchUnits).isChecked = toggleImperialUnits
        mView.findViewById<EditText>(R.id.settingsBaseSpeedTextEdit).setText(baseSpeed)
        mView.findViewById<Switch>(R.id.settingsSwitchAlerts).isChecked = toggleAlertSounds
    }

    private fun createListeners(mView: View)
    {
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore
        val doc = db.collection("userSettings").document(userID)

        val settingsSwitchUnits = mView.findViewById<Switch>(R.id.settingsSwitchUnits)
        val settingsBaseSpeedTextEdit = mView.findViewById<EditText>(R.id.settingsBaseSpeedTextEdit)
        val settingsSwitchAlerts = mView.findViewById<Switch>(R.id.settingsSwitchAlerts)


        settingsSwitchUnits.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != dbToggleImperialUnits)
                doc.update("toggleImperialUnits", isChecked)
                dbToggleImperialUnits=isChecked
        }

        settingsBaseSpeedTextEdit.doOnTextChanged{ text, start, count, after ->
            if (text.toString() != dbBaseSpeed) {
                if (text.toString() == "") {
                    doc.update("baseSpeed", "100")
                    dbBaseSpeed = "100"
                    settingsBaseSpeedTextEdit.setText(dbBaseSpeed)
                } else {
                    doc.update("baseSpeed", text.toString())
                    dbBaseSpeed = text.toString()
                }
            }
        }

        settingsSwitchAlerts.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked != dbToggleAlertSounds)
                doc.update("toggleAlertSounds", isChecked)
                dbToggleAlertSounds=isChecked
        }
    }
}