package jpmc.team15.userapp.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import jpmc.team15.userapp.R
import jpmc.team15.userapp.databinding.ActivityMyProfileBinding
import jpmc.team15.userapp.firebase.FirestoreClass
import jpmc.team15.userapp.models.User
import jpmc.team15.userapp.utils.Constants
import java.io.IOException

class MyProfileActivity : BaseActivity() {
    private var profileBinding: ActivityMyProfileBinding? = null //the parent layout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileBinding= ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding?.root)

        setupToolbar()

        //to populate data
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this)

    }





        //to populate user data from remote
    fun setUserDataInUI(user: User){
        hideProgressDialog()
        profileBinding?.apply {
            etName.setText(user.name)
            etEmail.setText(user.email)
            etMobile1.setText(user.mobile_1.toString())
            etMobile2.setText(user.mobile_2.toString())
        }
    }


    private fun setupToolbar(){

        val toolbar=profileBinding?.toolbarMyProfileActivity

        setSupportActionBar(toolbar)
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        toolbar?.title = "My Profile"
        toolbar?.setTitleTextColor("#ffffff".toColorInt())

        toolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }
}