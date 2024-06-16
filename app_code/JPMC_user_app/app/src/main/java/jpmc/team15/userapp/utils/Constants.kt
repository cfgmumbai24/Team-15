package jpmc.team15.userapp.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String="Users" //the table
    const val BOARDS:String="Boards"


    //fields in the user Users table
    const val IMAGE:String="image"
    const val NAME:String="name"
    const val MOBILE:String="mobile"

    const val ASSIGNED_TO:String="assignedTo"


    const val READ_STORAGE_PERMISSION_CODE=1
    const val PICK_IMAGE_REQUEST_CODE=2


    fun getFileExtension(activity: Activity,uri: Uri):String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri))
    }

    fun showImageChooser(activity: Activity){
        //pick image from gallery
        val galleryIntent= Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, Constants.PICK_IMAGE_REQUEST_CODE)
    }
}