package com.example.tut_11_recycler_view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager

class MainActivity : AppCompatActivity(), NumAdapter.OnItemClickListener {
    lateinit var adapter: NumAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        val data = initData()
        adapter = NumAdapter(this, data, this)
        recyclerView.adapter = adapter
        val gridLayout = GridLayoutManager(this, 2)
        gridLayout.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when(adapter.getItemViewType(position)){
                    0 -> 1
                    1 -> 2
                    else -> 1
                }
            }

        }
        recyclerView.layoutManager = gridLayout
    }

    private fun initData(): MutableList<String> {
        val nums = mutableListOf<String>()
        for (i in 0..100){
            nums.add("Number ${i}")
        }
        return nums
    }

    override fun onItemClick(item: String) {
        Toast.makeText(this, item, Toast.LENGTH_LONG ).show()
    }

}