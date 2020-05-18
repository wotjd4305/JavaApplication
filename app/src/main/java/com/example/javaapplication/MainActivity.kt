package com.example.javaapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.example.javaapplication.scan.CreateQR
import com.example.javaapplication.scan.ScanQR
import com.example.javaapplication.user.LoginActivity
import com.example.javaapplication.user.SignUpActivity
import kotlinx.android.synthetic.main.activity_create_qr.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnBtnClickListener()
    }

    private fun setOnBtnClickListener() {
        btn_login.setOnClickListener {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        btn_sign_up.setOnClickListener {
            val intent = Intent(this@MainActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        btn_scanQR.setOnClickListener {
            val intent = Intent(this@MainActivity, ScanQR::class.java)
            startActivity(intent)
        }
        btn_createQR.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateQR::class.java)
            startActivity(intent)
        }
    }
}