package org.matamem.ui.groups.notes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.matamem.YesNoDialog
import org.matamem.databinding.ActivityGroupTextsBinding
import org.matamem.modals.Note

@AndroidEntryPoint
class ActivityGroupNotes : AppCompatActivity(),
    SwipeToDeleteCallback.RecyclerItemTouchHelperListener {
    private lateinit var b: ActivityGroupTextsBinding
    private lateinit var vm: ActivityGroupNotesViewModel
    private lateinit var adapter : NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProvider(this).get(ActivityGroupNotesViewModel::class.java)
        b = ActivityGroupTextsBinding.inflate(layoutInflater)

        setContentView(b.root)

        var id = intent.getIntExtra("id", 0)

        vm.getTexts(id)

        b.rclTextItems.layoutManager = LinearLayoutManager(this)
        vm.notes.observe(this) {
            adapter.setList(it.reversed())
            handleEmptyLayout(it)


        }
        adapter = NotesAdapter(this, vm)
        b.rclTextItems.adapter = adapter

        enableSwipeToDelete()
    }

    private fun handleEmptyLayout(list : List<Note>){
        if(list.isEmpty()){
            b.ltEmpty.visibility = View.VISIBLE
            b.rclTextItems.visibility = View.GONE
        }else{
            b.ltEmpty.visibility = View.GONE
            b.rclTextItems.visibility = View.VISIBLE
        }
    }
    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(0, ItemTouchHelper.LEFT, this) {}

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(b.rclTextItems)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        var yesNoDlg = YesNoDialog()
        yesNoDlg.show(
            this,
            { dialog, which ->  vm.deleteText(vm.notes.value?.get(position)!!) },
            { dialog, which -> adapter.notifyDataSetChanged() },

            )
    }

}