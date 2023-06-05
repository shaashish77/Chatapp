package com.ashish.chatapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnSignUp: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val name= edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signUp(name,email, password)
        }
    }
    private fun signUp(name:String, email:String, password: String){
//        logic of creating user
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(name, email, auth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity:: class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()

                }
            }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun addUserToDatabase(name: String, email: String, uid:String){
      mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}