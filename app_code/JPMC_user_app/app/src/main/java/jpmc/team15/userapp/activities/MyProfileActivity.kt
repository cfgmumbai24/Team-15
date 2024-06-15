package jpmc.team15.userapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import jpmc.team15.userapp.R
import jpmc.team15.userapp.databinding.ActivityMyProfileBinding
import jpmc.team15.userapp.firebase.FirestoreClass
import jpmc.team15.userapp.models.User

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
            etMobile.setText(user.mobile.toString())

            Glide.with(this@MyProfileActivity)
                .load(user.image)
                .fitCenter()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(ivProfileUserImage)
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