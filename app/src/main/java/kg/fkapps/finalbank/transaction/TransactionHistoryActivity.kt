package kg.fkapps.finalbank.transaction

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kg.fkapps.finalbank.R
import kg.fkapps.finalbank.data.TransactionHistoryDB
import kg.fkapps.finalbank.data.UserDB
import kg.fkapps.finalbank.models.TransactionData
import kg.fkapps.finalbank.models.UserData

class TransactionHistoryActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_history)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Transaction History"

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.transaction_list)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val items: ArrayList<TransactionData> = ArrayList()

        val userDB = UserDB(applicationContext)
        val transactionDB = TransactionHistoryDB(applicationContext)

        val cursor: Cursor = transactionDB.viewAllTransactions()

        if (cursor != null && cursor.moveToFirst()) {

            val from = cursor.getInt(cursor.getColumnIndex("from_user"))
            val to = cursor.getInt(cursor.getColumnIndex("to_user"))
            val amount = cursor.getInt(cursor.getColumnIndex("amount"))
            val date_time = cursor.getString(cursor.getColumnIndex("date_time"))

            val fromUser: UserData = userDB.viewSingleUser(from)
            val toUser: UserData = userDB.viewSingleUser(to)

            val item = TransactionData(
                fromUser.name,
                toUser.name,
                date_time,
                amount
            )

            items.add(item)

            while (cursor.moveToNext()) {
                val from = cursor.getInt(cursor.getColumnIndex("from_user"))
                val to = cursor.getInt(cursor.getColumnIndex("to_user"))
                val amount = cursor.getInt(cursor.getColumnIndex("amount"))
                val date_time = cursor.getString(cursor.getColumnIndex("date_time"))

                val fromUser: UserData = userDB.viewSingleUser(from)
                val toUser: UserData = userDB.viewSingleUser(to)

                val item = TransactionData(
                    fromUser.name,
                    toUser.name,
                    date_time,
                    amount
                )

                items.add(item)
            }
        }

        items.reverse()

        //creating our adapter
        val adapter = TransactionRecyclerViewAdapter(items)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}