package jpmc.team15.userapp.activities

import android.util.Base64
import org.json.JSONObject

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.google.common.net.MediaType
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import jpmc.team15.userapp.R
import jpmc.team15.userapp.databinding.ActivityCreateBoardBinding
import jpmc.team15.userapp.firebase.FirestoreClass
import jpmc.team15.userapp.models.Board
import jpmc.team15.userapp.utils.Constants
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class CreateBoardActivity : BaseActivity() {
    private var createBoardBinding: ActivityCreateBoardBinding? = null

    private var mSelectedImageFileUri: Uri? = null

    private lateinit var mUserName:String//to know who created the board


    private var mBoardImageURL:String=""//to store the image url in remote


    private var et_board_name:String ="Category"

    private var inferred_class:String="Not identified"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createBoardBinding= ActivityCreateBoardBinding.inflate(layoutInflater)
        setContentView(createBoardBinding?.root)

        setupToolbar()

        mUserName="abcd"
        //get user name
        if(intent.hasExtra(Constants.NAME)){
            mUserName=intent.getStringExtra(Constants.NAME).toString()
        }

        //image picker
        createBoardBinding?.ivBoardImage?.setOnClickListener {
            checkPermission()
        }


        createBoardBinding?.btnCreate?.setOnClickListener {
            if(mSelectedImageFileUri!=null) {
                uploadBoardImage()
            }
            else{
                showProgressDialog(resources.getString(R.string.please_wait))
                createBoard()
            }
            //if we have not selected any image -> mSelectedImageFileUri will be null
            //we directly call createBoard
            //create board just passes the URL of the firebase storage image as "empty"
            //so we can create without any image as well in this code
        }

        //auto populate the date
        createBoardBinding?.etCurrentDate?.setText(getCurrentDate())


        //drop down
        val spinner:Spinner=createBoardBinding?.spinnerCategory!!
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedCategory = parent.getItemAtPosition(position).toString()
                // Assuming you have an EditText named et_board_name
                et_board_name=selectedCategory.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Another interface callback
            }
        }

    }

    //board creation
    private fun createBoard(){
        //add owner to user list
        val assignedUsersArrayList:ArrayList<String> = ArrayList()
        assignedUsersArrayList.add(getCurrentUserID())

        var userId:String=getCurrentUserID()
        val qty=createBoardBinding?.etQty?.text.toString()

        //create the board information
        val board= Board(
            et_board_name,
            mBoardImageURL,
            createBoardBinding?.etCurrentDate?.text.toString(),
            userId,
            qty.toInt(),
            inferred_class
        )

        FirestoreClass().createBoard(this@CreateBoardActivity,board)

    }
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return currentDate.format(formatter)
    }


    //to know successful creation
    fun boardCreatedSuccessfully(){
        hideProgressDialog()
        setResult(Activity.RESULT_OK)
        val intent= Intent(this@CreateBoardActivity,MainActivity::class.java)



        //startActivity(intent)
        //finish()
    }




    //picture upload in storage
    private fun uploadBoardImage() {
        showProgressDialog(("ML model running"))

        if (mSelectedImageFileUri != null) {
            val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
                "BOARD_IMAGE" + System.currentTimeMillis() + "." +
                        Constants.getFileExtension(this, mSelectedImageFileUri!!)
            )

            sRef.putFile(mSelectedImageFileUri!!).addOnSuccessListener { taskSnapshot ->
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    mBoardImageURL = uri.toString()
                    callAPI(mBoardImageURL)
                    // Delay the execution of createBoard() by 5 seconds
                    Handler(Looper.getMainLooper()).postDelayed({
                        createBoard()
                    }, 30000)

                }.addOnFailureListener {
                    hideProgressDialog()
                    showErrorSnackBar("Error in uploading image")
                }
            }
        }
    }

    private fun callAPI(imageUrl: String) {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
            .readTimeout(30, TimeUnit.SECONDS)    // socket read timeout
            .writeTimeout(30, TimeUnit.SECONDS)   // socket write timeout
            .build()

        val request = Request.Builder()
            .url("https://detect.roboflow.com/jpmmss/1?api_key=IZtyygJiC3DOgQoj4iCN&image=$imageUrl")
            .build()

        println("Request: $request")
        Log.e("Request:", request.toString())

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    hideProgressDialog()
                    showErrorSnackBar("Error in calling the API: ${e.message}")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonResponse = JSONObject(response.body?.string())
                    val predictions = jsonResponse.getJSONObject("predictions")
                    val keys = predictions.keys()
                    var lastKey = ""
                    while (keys.hasNext()) {
                        lastKey = keys.next()
                    }
                    inferred_class = lastKey
                } else {
                    inferred_class = "Not identified"
                }

                Log.e("API:", inferred_class)
            }
        })
    }

    //permission handling
    private fun checkPermission(){
        Dexter.withActivity(this)
            .withPermissions(
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if(report!!.areAllPermissionsGranted()){
                        Constants.showImageChooser(this@CreateBoardActivity)
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
    //rationale for permission
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
    //activity result handling
    //1.Set image from gallery intent
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if the result is ok
        if (resultCode == Activity.RESULT_OK) {
            //activity result is of gallery
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    showErrorSnackBar("Image selection successful")
                    mSelectedImageFileUri = data.data
                    Log.i("Image URI", mSelectedImageFileUri.toString())
                    try {
                        Glide.with(this@CreateBoardActivity)
                            .load(mSelectedImageFileUri)
                            .fitCenter()
                            .placeholder(R.drawable.ic_board_place_holder)
                            .into(createBoardBinding!!.ivBoardImage)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        showErrorSnackBar("Image selection failed")
                    }
                } else {
                    Toast.makeText(this@CreateBoardActivity, "Invalid URI", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            //other activity results
        }
    }





    private fun setupToolbar(){
        val toolbar=createBoardBinding?.toolbarCreateBoardActivity

        setSupportActionBar(toolbar)
        val actionBar=supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)

        toolbar?.title = resources.getString(R.string.create_board_title)
        toolbar?.setTitleTextColor("#ffffff".toColorInt())

        toolbar?.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }
}