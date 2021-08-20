package org.matamem.ui.groups

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import org.matamem.YesNoDialog
import org.matamem.databinding.FragmentNotificationsBinding
import org.matamem.modals.Group


@AndroidEntryPoint
class GroupsFragment : Fragment() ,
    SwipeToDeleteCallback.RecyclerItemTouchHelperListener {

    private lateinit var vm: GroupsViewModel
    private lateinit var adapter : GroupsAdapter
    lateinit var b: FragmentNotificationsBinding;
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm =
            ViewModelProvider(this).get(GroupsViewModel::class.java)
        b = FragmentNotificationsBinding.inflate(inflater, container, false)




        vm.groupList.observe(viewLifecycleOwner) {
            adapter.setList(it.reversed())
            handleEmptyLayout(it)
        }

        b.rclItems.layoutManager = LinearLayoutManager(context)

        adapter = GroupsAdapter(requireContext(), vm)
        b.rclItems.adapter = adapter

        enableSwipeToDelete()

        return b.root
    }


    private fun handleEmptyLayout(list : List<Group>){
        if(list.isEmpty()){
            b.ltEmpty.visibility = View.VISIBLE
            b.rclItems.visibility = View.GONE
        }else{
            b.ltEmpty.visibility = View.GONE
            b.rclItems.visibility = View.VISIBLE
        }
    }

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback: SwipeToDeleteCallback =
            object : SwipeToDeleteCallback(0, ItemTouchHelper.LEFT, this) {}

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(b.rclItems)
    }


    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {

        var yesNoDlg = YesNoDialog()
        yesNoDlg.show(
            requireContext(),
            { dialog, which -> vm.deleteGroup(vm.groupList.value?.reversed()!![position]) },
            { dialog, which -> adapter.notifyDataSetChanged() },

            )
    }
}