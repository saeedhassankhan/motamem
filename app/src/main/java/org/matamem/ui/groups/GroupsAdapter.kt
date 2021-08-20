package org.matamem.ui.groups

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.matamem.databinding.ItemGroupDetailBinding
import org.matamem.modals.Group
import org.matamem.ui.groups.notes.ActivityGroupNotes

class GroupsAdapter(var context : Context ,var vm : GroupsViewModel) : RecyclerView.Adapter<GroupsAdapter.Holder>() {

    private var paymentList: List<Group>? = null

    public fun setList(list: List<Group>){
        this.paymentList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding = ItemGroupDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(context , itemBinding , vm)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = paymentList?.get(position)





        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        if (paymentList == null) return 0

        return paymentList!!.size
    }

     class Holder(var context: Context, public val itemBinding: ItemGroupDetailBinding, private var vm : GroupsViewModel) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(group: Group) {
            itemBinding.txtItemGroupName.text = group.name

            itemBinding.imgEdit.setOnClickListener{
                if(itemBinding.lytNewGroupName.visibility == View.GONE) {
                    itemBinding.lytNewGroupName.visibility = View.VISIBLE
                    itemBinding.edtGroupName.setText(group.name)
                }else{
                    itemBinding.lytNewGroupName.visibility = View.GONE
                }
            }


            itemBinding.crdGroup.setOnClickListener{
                context.startActivity(Intent(context , ActivityGroupNotes::class.java)
                    .putExtra("id" , group.id)
                )
            }

            itemBinding.lytNewGroupName.visibility = View.GONE

            itemBinding.btnRegisterEdit.setOnClickListener{

                group.name = itemBinding.edtGroupName.text.toString()

                vm.updateGroup(group)
            }

        }
    }



}

