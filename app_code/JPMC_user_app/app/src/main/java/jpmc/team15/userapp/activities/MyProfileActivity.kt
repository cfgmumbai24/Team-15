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




    //for permission code
    companion object{
        private const val READ_STORAGE_PERMISSION_CODE=1
        private const val PICK_IMAGE_REQUEST_CODE=2
    }

    // Add a global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    //for downloading
    private var mProfileImageURL: String = ""

    //for updating profile
    private lateinit var mUserDetails: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        profileBinding= ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(profileBinding?.root)

        setupToolbar()

        //to populate data
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().loadUserData(this)



        //for updating the image
        profileBinding?.ivProfileUserImage?.setOnClickListener {
            //check for permission
            Dexter.withActivity(this)
                .withPermissions(
                    android.Manifest.permission.READ_MEDIA_IMAGES,
                    android.Manifest.permission.INTERNET,
                    android.Manifest.permission.ACCESS_NETWORK_STATE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if(report!!.areAllPermissionsGranted()){
                            showImageChooser()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest>,
                        token: PermissionToken
                    ) {
                        showRationalDialogForPermissions()
                    }
                }).onSameThread().check()
        }



        //update button
        profileBinding?.btnUpdate?.setOnClickListener {
            if(mSelectedImageFileUri!=null) {
                uploadUserImage()
            }

            else{
                showProgressDialog(resources.getString(R.string.please_wait_update))
                updateUserProfileData()
            }



        }
    }


    //upload to storage
    private fun uploadUserImage(){
        showProgressDialog(resources.getString(R.string.please_wait))

        //image exists then only upload
        if(mSelectedImageFileUri!=null){

            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "USER_IMAGE"+System.currentTimeMillis()+"."+
                        getFileExtension(mSelectedImageFileUri!!) //name of the file we want => generate unique name
            )

            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->//write the file in storage
                Log.i("Firebase Image URL",taskSnapshot.metadata!!.reference!!.downloadUrl.toString())

                //store the link to the image int somewhere -> storage
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {uri->
                    Log.i("Downloadable Image URL",uri.toString())
                    mProfileImageURL=uri.toString() //get the link to the image in storage

                    //TODO update the profile using this URI
                    showErrorSnackBar("Task successfully completed")
                }
            }.addOnFailureListener{
                showErrorSnackBar(it.message.toString())

            }

        }
        hideProgressDialog()
    }

    //to update profile after button pressed successfully
    private fun updateUserProfileData(){


        val userHashMap=HashMap<String, Any>()

        var anyChangesMade=false

        //add values to hashmap

        //update image if it is not the same
        //mUserDetails is the current user details fetched from remote
        //mProfileImage will be empty if we have not selected a new image
        if(!mProfileImageURL.isNullOrEmpty() && mProfileImageURL!=mUserDetails.image){
            userHashMap[Constants.IMAGE]=mProfileImageURL
            anyChangesMade=true
        }
        if(profileBinding?.etName?.text.toString()!=mUserDetails.name){
            userHashMap[Constants.NAME]=profileBinding?.etName?.text.toString()
            anyChangesMade=true
        }
        if(profileBinding?.etMobile?.text.toString()!=mUserDetails.mobile.toString()){
            userHashMap[Constants.MOBILE]=profileBinding?.etMobile?.text.toString().toLong()
            anyChangesMade=true
        }

        FirestoreClass().updateUserProfileData(this,userHashMap)

//        if(anyChangesMade){
//            showProgressDialog(resources.getString(R.string.please_wait_update))
//            FirestoreClass().updateUserProfileData(this,userHashMap)
//        }else{
//            showErrorSnackBar("No changes made")
//        }

    }

    fun profileUpdateSuccess(){
        hideProgressDialog()
        Toast.makeText(this@MyProfileActivity,"Profile updated successfully",Toast.LENGTH_SHORT).show()

        val intent=Intent(this@MyProfileActivity,MainActivity::class.java)
        setResult(Activity.RESULT_OK)//for updating the side nav

        startActivity(intent)
        finish()
    }



    //check the file type from the URI  to check if we can use as image
    private fun getFileExtension(uri: Uri):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(contentResolver.getType(uri))
    }

    //to pick image from gallery
    private fun showImageChooser() {
        //pick image from gallery
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }


    //get activity result
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //if the result is ok
        if (resultCode == Activity.RESULT_OK) {
            //activity result is of gallery
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    showErrorSnackBar("Image selection successful")
                    mSelectedImageFileUri=data.data
                    Log.i("Image URI",mSelectedImageFileUri.toString())
                    try {
                        Glide.with(this@MyProfileActivity)
                            .load(mSelectedImageFileUri)
                            .fitCenter()
                            .placeholder(R.drawable.ic_user_place_holder)
                            .into(profileBinding!!.ivProfileUserImage)
                    }catch (e: IOException) {
                        e.printStackTrace()
                        showErrorSnackBar("Image selection failed")
                    }
                }
                else{
                    Toast.makeText(this@MyProfileActivity,"Invalid URI",Toast.LENGTH_SHORT).show()
                }
            }

            //other activity results

        }
    }

    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(this).setMessage("It looks like you have turned off permissions required for this feature. It can be enabled under the Application settings")
            .setPositiveButton("GO TO SETTINGS"){
                    _,_ ->
                try{
                    val intent= Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri= Uri.fromParts("package",packageName,null)
                    intent.data=uri
                    startActivity(intent)
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
            .setNegativeButton("CANCEL"){
                    dialog,_ ->
                dialog.dismiss()
            }.show()
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