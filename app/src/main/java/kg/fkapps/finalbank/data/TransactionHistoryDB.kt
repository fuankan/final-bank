package kg.fkapps.finalbank.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TransactionHistoryDB(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "TransactionDatabase"
        private const val TABLE_NAME = "TransactionTable"
        private const val KEY_ID = "id"
        private const val FROM = "from_user"
        private const val TO = "to_user"
        private const val AMOUNT = "amount"
        private const val DATE_TIME = "date_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + AMOUNT + " INTEGER," + DATE_TIME + " TEXT," + FROM + " INTEGER,"
                + TO + " INTEGER" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertTransaction(from: Int, to: Int, amount: Int, date_time: String): Long {

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(AMOUNT, amount)
        contentValues.put(DATE_TIME, date_time)
        contentValues.put(FROM, from)
        contentValues.put(TO, to)

        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return success
    }

    fun viewAllTransactions(): Cursor {

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        cursor = db.rawQuery(selectQuery, null)

        return cursor
    }
}