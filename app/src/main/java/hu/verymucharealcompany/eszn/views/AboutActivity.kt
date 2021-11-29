package hu.verymucharealcompany.eszn.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import hu.verymucharealcompany.eszn.databinding.ActivityAboutBinding
import hu.verymucharealcompany.eszn.databinding.ActivityAboutBinding.inflate

class AboutActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}