package com.gwsilver.reading_counter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gwsilver.reading_counter.databinding.ItemListBinding

class CounterAdapter: RecyclerView.Adapter<CounterAdapter.CounterHolder>() {
    val counterList = ArrayList<ListItem>()
    class CounterHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = ItemListBinding.bind(item)
        fun bind(listItem: ListItem) = with(binding){

            var CountInt = listItem.counterInt
            var CountString = ""
            val CA = IntArray(6)

            for (i in 0..5) {
                CA[i] = CountInt % 10
                CountInt /= 10
            }
            for(i in 5 downTo 0) CountString += "${CA[i]}"

            val Tarif = "${listItem.tarifFloat} pуб."
            val Rashod = "${listItem.rashodMoneyFloat} руб."

            tvListCounter.text = CountString
            tvListRasCounter.text = listItem.rashodCounterInt.toString()
            tvListTarif.text = Tarif
            tvListRasMoney.text = Rashod
            tvListData.text = listItem.dateString
            btDel.setOnClickListener {
                MAIN.DeleteElement(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent,false)
        return CounterHolder(view)
    }

    override fun onBindViewHolder(holder: CounterHolder, position: Int) {
        holder.bind(counterList[position])
    }

    override fun getItemCount(): Int {
        return counterList.size
    }

    fun addAll(list: List<ListItem>) {
        counterList.clear()
        counterList.addAll(list)
        notifyDataSetChanged()
    }
}