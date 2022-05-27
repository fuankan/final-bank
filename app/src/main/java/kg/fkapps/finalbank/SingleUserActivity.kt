package kg.fkapps.finalbank

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kg.fkapps.finalbank.data.UserDB
import kg.fkapps.finalbank.selectUser.SelectUsersActivity

class SingleUserActivity : AppCompatActivity() {

    var id: Int = -1
    var amount: Int = 0

    lateinit var transfer: Button
    lateinit var Name: TextView
    lateinit var Email: TextView
    lateinit var Amount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_user)

        id = intent.getIntExtra("id",0)

        Name = findViewById(R.id.username)
        Email = findViewById(R.id.useremail)
        Amount = findViewById(R.id.amount)
        transfer  = findViewById(R.id.transfer_btn)

        transfer.setOnClickListener{
            val intent = Intent(this@SingleUserActivity, SelectUsersActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("amount",amount)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        val db = UserDB(applicationContext)
        val user = db.viewSingleUser(id)
        amount = user.amount

        Name.text = user.name
        Email.text = user.email
        Amount.text = "Total Amount: " + amount.toString() + "$"
    }
}