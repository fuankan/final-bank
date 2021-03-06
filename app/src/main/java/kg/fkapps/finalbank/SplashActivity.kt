package kg.fkapps.finalbank

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import kg.fkapps.finalbank.allUsers.AllUsersActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = getSharedPreferences("CheckLogin", Context.MODE_PRIVATE)
        val check = prefs.getString("Check", "No")

        Handler(Looper.getMainLooper()).postDelayed(
            {
                if (check.equals("Yes")) {
                    val intent = Intent(this@SplashActivity, AllUsersActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
            },
            1500 // value in milliseconds
        )

    }
}