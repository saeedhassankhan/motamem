package org.matamem.ui.groups

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.matamem.modals.Group
import org.matamem.repository.Repository
import javax.inject.Inject

@HiltViewModel
class GroupsViewModel @Inject constructor(private var repository : Repository ) :  ViewModel() {

    var groupList = repository.getGroups()

    public fun updateGroup(grp : Group){
        repository.updateGroup(grp)
    }

    fun deleteGroup(grp : Group){
        repository.deleteGroup(grp)
    }

}