package jpmc.team15.userapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.core.graphics.toColorInt
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import jpmc.team15.userapp.R
import jpmc.team15.userapp.adapters.ItemBoardAdapter
import jpmc.team15.userapp.databinding.ActivityMainBinding
import jpmc.team15.userapp.databinding.AppBarMainBinding
import jpmc.team15.userapp.databinding.MainContentBinding
import jpmc.team15.userapp.databinding.NavHeaderMainBinding
import jpmc.team15.userapp.firebase.FirestoreClass
import jpmc.team15.userapp.models.Board
import jpmc.team15.userapp.models.User

class MainActivity : BaseActivity() {
    private var mainBinding: ActivityMainBinding? = null //the parent layout
    private var appBarBinding: AppBarMainBinding? = null //the top toolbar
    private var mainContentBinding: MainContentBinding? = null //the recycler view containing

    private lateinit var mUserName:String//to know who created the board

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        appBarBinding= AppBarMainBinding.bind(mainBinding?.drawerAppBar?.root!!)
        mainContentBinding= MainContentBinding.bind(mainBinding?.drawerAppBar?.mainContent?.root!!)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setUpActionBar()

        //side drawer ke buttons ke on click listeners
        mainBinding?.navView?.setNavigationItemSelectedListener { menuItem->
            when(menuItem.itemId){
                R.id.nav_myProfile -> {
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this@MainActivity,MyProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_signOut -> {
                    FirebaseAuth.getInstance().signOut()
                    showErrorSnackBar("Successfully Signed out")
                    val intent= Intent(this@MainActivity,SignInActivity::class.java)

                    //if we had previous intro activity in stack use that one
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                    true
                }else ->false
            }
            mainBinding?.drawerLayout?.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }


        showProgressDialog(resources.getString(R.string.please_wait))
        //set navigation drawer details
        FirestoreClass().loadUserData(this,true)


        appBarBinding?.fabCreateBoard?.setOnClickListener {
            val intent= Intent(this@MainActivity,CreateBoardActivity::class.java)
            startActivity(intent)
        }

    }


    fun updateNavigationUserDetails(user: User,readBoardsList:Boolean){
        hideProgressDialog()

        val navView=mainBinding?.navView// Get a reference to the NavigationView
        val headerView=navView?.getHeaderView(0) // Get a reference to the header view
        val headerBinding = headerView?.let { NavHeaderMainBinding.bind(it) }// Bind the header view

        //access
        val userName=headerBinding?.tvHeaderUserName
        val profile=headerBinding?.ivHeaderProfile

        //set the image
        Glide.with(this)
            .load(R.drawable.jpmmss_logo)
            .fitCenter()
            .placeholder(R.drawable.ic_user_place_holder)
            .into(profile!!)

        userName?.text=user.name

        mUserName=user.name

        //read boards if required
        if(readBoardsList){
            showProgressDialog("Loading your uploads")
            FirestoreClass().getBoardsList(this)
        }

    }


    //recycler view list inside main screen
    fun populateBoardsListToUI(boardList:ArrayList<Board>){
        hideProgressDialog()

        if(boardList.size>0){
            //play with visibility
            mainContentBinding?.rvBoardsList?.visibility=android.view.View.VISIBLE
            mainContentBinding?.tvNoBoardsAvailable?.visibility=android.view.View.GONE

            //assign the adapter
            val adapter=ItemBoardAdapter(this@MainActivity,boardList)

            mainContentBinding?.rvBoardsList?.setHasFixedSize(true)
            mainContentBinding?.rvBoardsList?.layoutManager= LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
            mainContentBinding?.rvBoardsList?.adapter= adapter



        }else{
            //visibility
            mainContentBinding?.rvBoardsList?.visibility=android.view.View.GONE
            mainContentBinding?.tvNoBoardsAvailable?.visibility=android.view.View.VISIBLE
        }
    }

    private fun setUpActionBar(){

        val toolbar=appBarBinding?.toolbarMainActivity
        setSupportActionBar(toolbar)
        actionBar?.title="Your Uploads"
        toolbar?.title="Your Uploads"
        toolbar?.setTitleTextColor("#ffffff".toColorInt())

        toolbar?.setNavigationIcon(R.drawable.ic_navigation_menu)
        toolbar?.setNavigationOnClickListener {
            toggleDrawer()
        }

    }

    private fun toggleDrawer() {
        if(mainBinding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            mainBinding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            mainBinding?.drawerLayout?.openDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {

        if(mainBinding?.drawerLayout?.isDrawerOpen(GravityCompat.START) == true){
            mainBinding?.drawerLayout?.closeDrawer(GravityCompat.START)
        }else{
            doubleBackToExit()
        }
        super.onBackPressed()

    }


}