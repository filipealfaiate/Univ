package pt.miguelndecarvalho.virtualtripmeter.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import pt.miguelndecarvalho.virtualtripmeter.MainActivity
import pt.miguelndecarvalho.virtualtripmeter.R
import pt.miguelndecarvalho.virtualtripmeter.ui.trip.TripFragment

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)

        var mView = inflater.inflate(R.layout.fragment_home, container, false)

        var trip = TripFragment()

        mView.findViewById<Button>(R.id.start_button).setOnClickListener{
            val transaction = parentFragmentManager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment, trip)
            transaction.addToBackStack(null)
            transaction.commit()
            (activity as MainActivity?)?.tripBarSelected(1)
        }

        createDocument(mView)

        return mView
    }

    private fun createDocument(mView: View){
        val user = Firebase.auth.currentUser
        val userID = user!!.uid
        val db = Firebase.firestore
        val doc = db.collection("userSettings").document(userID)

        doc.get()
            .addOnSuccessListener { document ->
                if (! document.exists()) {
                    createDefaultDocument(mView)
                }
            }
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
    }
}