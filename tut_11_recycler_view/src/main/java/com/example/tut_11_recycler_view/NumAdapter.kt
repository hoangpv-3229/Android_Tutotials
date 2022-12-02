package com.example.tut_11_recycler_view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumAdapter(val context: Context, val nums: MutableList<String>, val listener: OnItemClickListener):
    RecyclerView.Adapter<NumAdapter.NumViewHolder>() {

    inner class NumViewHolder(private val view: View, private val listener: OnItemClickListener): RecyclerView.ViewHolder(view){
        lateinit var tv: TextView
        fun bind(item: String, pos: Int){
            tv = view.findViewById(R.id.tv)
            tv.text = item

            view.rootView.setOnClickListener{
                nums[pos] = "Clicked ${nums[pos]}"
                notifyItemChanged(pos)
                listener.onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumViewHolder {
        Log.d("RECYCLER_VIEWxxx", "onCreateViewHolder")
        val inflater = LayoutInflater.from(context)
        return if (viewType == 0)
                NumViewHolder(inflater.inflate(R.layout.item_num, parent, false), listener)
                else
                NumViewHolder(inflater.inflate(R.layout.item_num2, parent, false), listener)

    }

    override fun onBindViewHolder(holder: NumViewHolder, position: Int) {
        Log.d("RECYCLER_VIEWxxx", "onBindViewHolder")
        holder.bind(nums[position], position)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 3 == 0){
                    return 1
                }else 0
    }
    override fun getItemCount(): Int {
        Log.d("RECYCLER_VIEWxxx", "getItemCount")
        return nums.size
    }
    interface OnItemClickListener{
        fun onItemClick(item: String)
    }
}