package org.matamem.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.matamem.databinding.ItemGroupBinding
import org.matamem.modals.Group

class DlgGroupsAdapter(private val paymentList: List<Group>) : RecyclerView.Adapter<DlgGroupsAdapter.PaymentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
        val itemBinding = ItemGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
        val paymentBean: Group = paymentList[position]

        holder.itemBinding.crdGroup.setOnClickListener{
            onItemClick(paymentBean)
        }
        holder.bind(paymentBean)
    }

    override fun getItemCount(): Int = paymentList.size

    class PaymentHolder(val itemBinding: ItemGroupBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(paymentBean: Group) {
            itemBinding.txtItemGroupName.text = paymentBean.name
        }
    }

    fun setOnItemClickListener(onItemClick : (grp : Group) -> Unit) : DlgGroupsAdapter{
        this.onItemClick = onItemClick
        return this
    }


    lateinit var onItemClick : (grp : Group) -> Unit


}

