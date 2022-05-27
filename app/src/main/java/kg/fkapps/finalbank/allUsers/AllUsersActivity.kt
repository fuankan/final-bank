package kg.fkapps.finalbank.allUsers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kg.fkapps.finalbank.LoginActivity
import kg.fkapps.finalbank.R
import kg.fkapps.finalbank.data.UserDB
import kg.fkapps.finalbank.models.UserData
import kg.fkapps.finalbank.transaction.TransactionHistoryActivity

class AllUsersActivity : AppCompatActivity() {

    var users: ArrayList<UserData> = ArrayList()
    var adapter: UserRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_users)

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.app_bar_layout)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Final bank"

        val prefs = getSharedPreferences("CheckDB", Context.MODE_PRIVATE)
        val check = prefs.getString("Check", "Insert")

        if(check.equals("Insert"))
        {
            val db: UserDB = UserDB(applicationContext)
            db.insertUsers("Atabek","atabek@gmail.com",10000)
            db.insertUsers("Aian","aian.d@gmail.com",5000)
            db.insertUsers("Kalyibek","kkalyibek@gmail.com",30100)
            db.insertUsers("Tilek","zholdoshbek.t@gmail.com",4500)
            db.insertUsers("Baisal","baisal@gmail.com",1200)
            db.insertUsers("Oleg","oleg@gmail.com",1700)
            db.insertUsers("Valeriy","valeriy@gmail.com",800)
            db.insertUsers("Marina","marina@gmail.com",2000)
            db.insertUsers("Killian","murphy@gmail.com",900)
            db.insertUsers("Rick","leroy@gmail.com",1000)

            val editor: SharedPreferences.Editor = applicationContext.getSharedPreferences("CheckDB", Context.MODE_PRIVATE).edit()
            editor.putString("Check", "Not Insert")
            editor.apply()
        }

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.user_list)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //creating our adapter
        adapter = UserRecyclerViewAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        users.clear()

        val db: UserDB = UserDB(applicationContext)
        val cursor: Cursor = db.viewAllUsers()

        if (cursor!=null && cursor.moveToFirst()) {

            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val amount = cursor.getInt(cursor.getColumnIndex("amount"))
            val userName = cursor.getString(cursor.getColumnIndex("name"))
            val userEmail = cursor.getString(cursor.getColumnIndex("email"))
            val user= UserData(
                id,
                userName,
                userEmail,
                amount
            )
            users.add(user)

            while (cursor.moveToNext())
            {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val amount = cursor.getInt(cursor.getColumnIndex("amount"))
                val userName = cursor.getString(cursor.getColumnIndex("name"))
                val userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val user= UserData(
                    id,
                    userName,
                    userEmail,
                    amount
                )
                users.add(user)
            }
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return when (id) {
            R.id.view_history -> {
                val intent = Intent(this@AllUsersActivity, TransactionHistoryActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout_option -> {
                val editor: SharedPreferences.Editor = applicationContext.getSharedPreferences("CheckLogin", Context.MODE_PRIVATE).edit()
                editor.putString("Check", "No")
                editor.apply()
                val intent = Intent(this@AllUsersActivity, LoginActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}