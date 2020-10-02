package com.devparadigam.agrade.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.devparadigam.agrade.BR

import java.util.*

class GenericListAdapter<T : Any>(var itemList: ArrayList<T>, val layout_id: Int, val listItemClickListener: OnListItemClickListener<T>) :
                        RecyclerView.Adapter<GenericListAdapter.GenericViewHolder>(), Filterable {

    var valueFilter: ValueFilter? = null
    var filteredItemList = itemList
   // val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        var binding: ViewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent?.context), layout_id, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

   /* fun setSwipeRevealOpenOnlyOne() {
        viewBinderHelper.setOpenOnlyOne(true)
    }*/

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val listItem = itemList.get(position)
        holder.itemView.setTag(position)
        holder?.binding?.setVariable(BR.itemClickListener, listItemClickListener)
        holder?.binding?.setVariable(BR.listitem, listItem)
        holder?.binding?.setVariable(BR.position, position)
      /*  if(listItem is CourseItem){
            val colorResource = if (listItem.isSelected) R.color.blue6 else R.color.grey6
            holder.itemView.txt_course_name.setTextColor(ContextCompat.getColor(holder.itemView.context, colorResource))
            holder.itemView.img_check.visibility = if (listItem.isSelected) View.VISIBLE else View.INVISIBLE
        } else{

        }*/
        holder.binding.executePendingBindings()
    }

    interface OnListItemClickListener<T> {
        fun onListItemClicked(selItem: T, extra:Any?,position: Int)
    }

    override fun getFilter(): Filter {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter as ValueFilter
    }

    class GenericViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        var binding: ViewDataBinding
        init {
            this.binding = binding
            this.binding.executePendingBindings()
        }
    }

    inner class ValueFilter : Filter() {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
            val results = Filter.FilterResults()
            if (constraint != null && constraint.length > 0) {
                val filterList = ArrayList<T>()
                for (i in 0 until filteredItemList.size) {
                    var currentItem = filteredItemList.get(i)
                    if(currentItem is com.devparadigam.agrade.model.response.ItemCategoryModel)
                    if (currentItem.type.toUpperCase().contains(constraint.toString().toUpperCase())) {
                        filterList.add(filteredItemList.get(i))
                    }
                }
                results.count = filterList.size
                results.values = filterList
            } else {
                results.count = filteredItemList.size
                results.values = filteredItemList
            }
            return results
        }

        override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {
            itemList = results.values as ArrayList<T>
            notifyDataSetChanged()
        }

    }



}