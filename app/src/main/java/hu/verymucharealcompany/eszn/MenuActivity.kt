package hu.verymucharealcompany.eszn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.verymucharealcompany.eszn.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bRecords.setOnClickListener {
            val recordIntent = Intent(this, MainActivity::class.java)
            startActivity(recordIntent)
        }

        binding.bProfile.setOnClickListener{
            //todo profile activity
        }

        binding.bStats.setOnClickListener{
            val statIntent = Intent(this, GraphActivity::class.java)
            startActivity(statIntent)
        }

        binding.bAbout.setOnClickListener{
            //todo credit page activity
        }
    }
}