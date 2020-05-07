package com.maha.simpleroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.maha.simpleroom.adapter.UserAdapter
import com.maha.simpleroom.db.database.UserDatabase
import com.maha.simpleroom.db.entity.User
import com.maha.simpleroom.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    var mUserList = arrayListOf<User>()

    lateinit var mContext: Context

    var aCount = 0


    var mUserDataBase: UserDatabase? = null

    lateinit var mUserAdapter :UserAdapter

    lateinit var   mViewmodel :MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
       // configureGoogleSignIn()

        mViewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        setUpRecyclerView()

        mUserDataBase = UserDatabase.getDatabase(this)



        mViewmodel.getUserDetail()?.observe(this, Observer {
           // mUserList =
            mUserAdapter.update(it as ArrayList<User>)
        })


        add_but.setOnClickListener {
            val aUser = User()
            aUser.name = "A $aCount"
            aUser.country = "India $aCount"
            aUser.money = "$aCount"
            aUser.usertype = "sample"

            mViewmodel.insertUserDetail(aUser)

            aCount++
        }

    }

    private fun setUpRecyclerView() {

        val myLayoutManager = LinearLayoutManager(mContext)
        user_recyclerview!!.layoutManager = myLayoutManager
        user_recyclerview!!.setHasFixedSize(true)

        mUserAdapter = UserAdapter(this, mUserList,mViewmodel )
        user_recyclerview!!.adapter = mUserAdapter
    }

    private fun configureGoogleSignIn() {

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)


        clickListener()
    }

    private fun clickListener() {

        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {

                // startActivity(HomeActivity.getLaunchIntent(this))
            } else {
                Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
            }
        }
    }
}
