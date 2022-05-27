package kg.fkapps.finalbank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kg.fkapps.finalbank.allUsers.AllUsersActivity
import kg.fkapps.finalbank.data.UserDB

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Add User"

        val userName = findViewById<EditText>(R.id.username)
        val userEmail = findViewById<EditText>(R.id.useremail)
        val amount = findViewById<EditText>(R.id.amount)
        val add = findViewById<Button>(R.id.add_button)

        val db = UserDB(applicationContext)

        add.setOnClickListener {
            if (userName.text.isNotEmpty() && userEmail.text.isNotEmpty() && amount.text.isNotEmpty()) {
                db.insertUsers(
                    userName.text.toString(),
                    userEmail.text.toString(),
                    Integer.valueOf(amount.text.toString())
                )
                Toast.makeText(applicationContext, "New client added", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AddUser, AllUsersActivity::class.java)
                startActivity(intent)
            } else (Toast.makeText(applicationContext, "Enter all needed data", Toast.LENGTH_LONG)
                .show())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}