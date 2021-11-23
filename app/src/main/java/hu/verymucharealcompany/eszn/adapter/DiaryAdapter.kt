package hu.verymucharealcompany.eszn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.verymucharealcompany.eszn.data.DiaryItem
import hu.verymucharealcompany.eszn.databinding.ItemDiaryListBinding

class DiaryAdapter(private val listener: DiaryItemClickListener) :
    RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>() {

    private val items = mutableListOf<DiaryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiaryViewHolder(
        ItemDiaryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: DiaryViewHolder, position: Int) {
        val diaryItem = items[position]

        holder.binding.tvDescription.text = diaryItem.description
        holder.binding.tvWeight.text = "${diaryItem.weight} Kg"
        holder.binding.tvDate.text = diaryItem.date

        holder.binding.ibRemove.setOnClickListener {
            listener.onItemRemoved(diaryItem)
            notifyDataSetChanged()
        }
    }

    fun addItem(item: DiaryItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<DiaryItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun delete(item: DiaryItem){
        items.remove(item)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = items.size

    interface DiaryItemClickListener {
        fun onItemChanged(item: DiaryItem)
        fun onItemRemoved(item: DiaryItem)
    }

    inner class DiaryViewHolder(val binding: ItemDiaryListBinding) : RecyclerView.ViewHolder(binding.root)
}
