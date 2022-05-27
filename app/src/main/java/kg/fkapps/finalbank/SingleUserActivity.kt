package kg.fkapps.finalbank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kg.fkapps.finalbank.data.UserDB
import kg.fkapps.finalbank.selectUser.SelectUsersActivity

class SingleUserActivity : AppCompatActivity() {

    var id: Int = -1
    var amount: Int = 0

    private lateinit var transfer: Button
    private lateinit var name: TextView
    private lateinit var email: TextView
    private lateinit var intAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_user)

        id = intent.getIntExtra("id", 0)

        name = findViewById(R.id.username)
        email = findViewById(R.id.useremail)
        intAmount = findViewById(R.id.amount)
        transfer = findViewById(R.id.transfer_btn)

        transfer.setOnClickListener {
            val intent = Intent(this@SingleUserActivity, SelectUsersActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("amount", amount)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        val db = UserDB(applicationContext)
        val user = db.viewSingleUser(id)
        amount = user.amount

        name.text = user.name
        email.text = user.email
        intAmount.text = "Total Amount: " + amount.toString() + "$"
    }
}