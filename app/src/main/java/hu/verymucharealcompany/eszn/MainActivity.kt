package hu.verymucharealcompany.eszn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import hu.verymucharealcompany.eszn.adapter.DiaryAdapter
import hu.verymucharealcompany.eszn.data.DiaryItem
import hu.verymucharealcompany.eszn.data.DiaryListDatabase
import hu.verymucharealcompany.eszn.databinding.ActivityMainBinding
import hu.verymucharealcompany.eszn.fragments.NewDiaryItemDialogFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), DiaryAdapter.DiaryItemClickListener,
NewDiaryItemDialogFragment.NewDiaryItemDialogListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var database: DiaryListDatabase
    private lateinit var adapter: DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        database = DiaryListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewDiaryItemDialogFragment().show(
            supportFragmentManager,
            NewDiaryItemDialogFragment.TAG
        )
        }

        initRecyclerView()
    }

    override fun onDiaryItemCreated(newItem: DiaryItem) {
        thread {
            val insertId = database.diaryItemDao().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }

    override fun onItemChanged(item: DiaryItem) {
        thread{
            database.diaryItemDao().update(item)
            Log.d("MainActivity", "DiaryItem update was successfull")
        }
    }

    private fun initRecyclerView() {
        adapter = DiaryAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }

    private fun loadItemsInBackground() {
        thread {
            val items = database.diaryItemDao().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemRemoved(item: DiaryItem) {
        thread {
            database.diaryItemDao().deleteItem(item)
            runOnUiThread {
                adapter.delete(item)
            }
            Log.d("MainActivity", "$item was removed from DiaryItem")
        }
    }

}