package jpmc.team15.userapp.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FirestoreClass {
    private var mFireStore = FirebaseFirestore.getInstance()





     fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser
        //if the user is not null then get the uid of the user -> auto login
        //else return an empty string
        var currentUserID=""
        if(currentUser!=null){
            currentUserID=currentUser.uid.toString()
        }
        return currentUserID
    }



}