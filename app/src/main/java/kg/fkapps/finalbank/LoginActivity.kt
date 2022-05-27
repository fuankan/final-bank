package kg.fkapps.finalbank

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kg.fkapps.finalbank.allUsers.AllUsersActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameBlank = "admin"
        val passwordBlank = "admin123"

        val username: TextInputEditText = findViewById(R.id.username)
        val password: TextInputEditText = findViewById(R.id.password)
        val login: Button = findViewById(R.id.log_in_button)

        login.setOnClickListener {
            if (username.text.toString().isNotEmpty()) {
                if (password.text.toString().isNotEmpty()) {
                    if (username.text.toString().equals(usernameBlank) && password.text.toString()
                            .equals(passwordBlank)
                    ) {
                        val editor: SharedPreferences.Editor =
                            applicationContext.getSharedPreferences(
                                "CheckLogin",
                                Context.MODE_PRIVATE
                            ).edit()
                        editor.putString("Check", "Yes")
                        editor.apply()

                        val intent = Intent(this@LoginActivity, AllUsersActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(
                            this@LoginActivity,
                            "Login Successful...",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Username/Password is incorrect!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    password.error = "Please Enter Password"
                }
            } else {
                username.error = "Please Enter Username"
            }
        }
    }
}