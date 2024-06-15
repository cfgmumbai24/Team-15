package jpmc.team15.userapp.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import jpmc.team15.userapp.activities.MainActivity
import jpmc.team15.userapp.activities.SignInActivity
import jpmc.team15.userapp.activities.SignUpActivity
import jpmc.team15.userapp.models.User
import jpmc.team15.userapp.utils.Constants


class FirestoreClass {
    private var mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignUpActivity, userInfo: User) {
        mFireStore.collection(Constants.USERS)//collection name we want to access
            .document(getCurrentUserID())//every user has his own document -> unique for each user
            .set(userInfo, SetOptions.merge())//set the data in the document
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e("FireStoreError", "Error while registering the user.", e)
            }
    }



    fun signInUser(activity: Activity){//kyuki calling activity ke instance pr  hi wapas jana hain
        mFireStore.collection(Constants.USERS)//is collection -> table
            .document(getCurrentUserID())//ka ye row -> cause the rows are identified by the user id
            .get()
            .addOnSuccessListener { document->
                val loggedInUser=document.toObject(User::class.java)!! // we are creating a user object from the document we get

                when(activity){
                    is SignInActivity ->{
                        activity.signInSuccess(loggedInUser)
                    }
                    is MainActivity ->{
                        activity.updateNavigationUserDetails(loggedInUser,false)
                    }
                }


            }
            .addOnFailureListener {
                    e->
                when(activity){
                    is SignInActivity ->{
                        activity.hideProgressDialog()
                    }
                    is MainActivity ->{
                        activity.hideProgressDialog()
                    }
                }

                Log.e("FireStoreError", "Error while signing in")
            }
    }



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