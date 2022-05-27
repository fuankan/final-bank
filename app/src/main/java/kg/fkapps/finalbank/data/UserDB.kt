package kg.fkapps.finalbank.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kg.fkapps.finalbank.models.UserData

class UserDB(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "UserDatabase"
        private val TABLE_NAME = "UserTable"
        private val KEY_ID = "id"
        private val KEY_NAME = "name"
        private val KEY_EMAIL = "email"
        private val AMOUNT = "amount"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + AMOUNT + " INTEGER," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    //method to insert data
    fun insertUsers(name: String, email: String, amount: Int):Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_NAME, name)
        contentValues.put(KEY_EMAIL, email)
        contentValues.put(AMOUNT, amount)
        // Inserting Row
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun viewAllUsers(): Cursor {

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        cursor = db.rawQuery(selectQuery, null)

        return cursor
    }

    @SuppressLint("Range")
    fun viewSingleUser(id: Int): UserData {

        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $KEY_ID = $id"
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        var user: UserData = UserData(0, "", "", 0)

        if (cursor != null)
        {
            if(cursor.moveToFirst())
            {
                user.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                user.name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                user.email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))
                user.amount = cursor.getInt(cursor.getColumnIndex(AMOUNT))
            }
        }

        cursor.close()

        return user
    }

    fun updateAmount(id: Int, amount: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(AMOUNT, amount)
        val success = db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(id.toString())).toLong()
        db.close()
        return Integer.parseInt("$success") != -1
    }
}