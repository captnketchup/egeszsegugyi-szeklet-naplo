package hu.verymucharealcompany.eszn.menu

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import hu.verymucharealcompany.eszn.GraphActivity
import hu.verymucharealcompany.eszn.MainActivity
import hu.verymucharealcompany.eszn.databinding.ActivityMenuBinding
import hu.verymucharealcompany.eszn.views.AboutActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding.bRecords.setOnClickListener {
            val recordIntent = Intent(this, MainActivity::class.java)
            startActivity(recordIntent)
        }

        binding.bStats.setOnClickListener{
            val statIntent = Intent(this, GraphActivity::class.java)
            startActivity(statIntent)
        }

        binding.bAbout.setOnClickListener{
            val aboutIntent = Intent(this, AboutActivity::class.java)
            startActivity(aboutIntent)
        }
    }
}