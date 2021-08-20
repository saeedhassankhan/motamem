package org.matamem.ui.groups.notes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.matamem.R
import org.matamem.databinding.ItemTextDetailBinding
import org.matamem.modals.Note
import android.content.Intent


class NotesAdapter(var context : Context, var vm : ActivityGroupNotesViewModel) : RecyclerView.Adapter<NotesAdapter.Holder>() {
    private var noteList: List<Note>? = null

    fun setList(list : List<Note>){
        this.noteList = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemBinding =
            ItemTextDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(context, itemBinding, vm)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = noteList?.get(position)

        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        if(noteList == null) return 0


        return noteList!!.size
    }

    class Holder(
        var context: Context,
        val itemBinding: ItemTextDetailBinding,
        private var vm: ActivityGroupNotesViewModel
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(note: Note) {
            itemBinding.txtItemGroupName.text = note.text

            itemBinding.imgEdit.setBackgroundResource(0)

            itemBinding.imgEdit.setOnClickListener {
                if (itemBinding.lytNewGroupName.visibility == View.GONE) {
                    itemBinding.lytNewGroupName.visibility = View.VISIBLE
                    itemBinding.imgEdit.setBackgroundResource(R.drawable.oval_gray)
                    itemBinding.edtGroupName.setText(note.text)
                } else {
                    itemBinding.imgEdit.setBackgroundResource(0)
                    itemBinding.lytNewGroupName.visibility = View.GONE
                }
            }

            itemBinding.crdGroup.setOnClickListener {

            }
            itemBinding.imgShare.setOnClickListener {
                shareText(note.text)
            }

            itemBinding.lytNewGroupName.visibility = View.GONE

            itemBinding.btnRegisterEdit.setOnClickListener {

                note.text = itemBinding.edtGroupName.text.toString()

                vm.updateText(note)
            }

        }

        private fun shareText(body: String?) {
            val txtIntent = Intent(Intent.ACTION_SEND)
            txtIntent.type = "text/plain"
            txtIntent.putExtra(Intent.EXTRA_TEXT, body)
            context.startActivity(Intent.createChooser(txtIntent, "Share"))

        }
    }
}

