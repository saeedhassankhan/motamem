package org.matamem.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.matamem.Const.Companion.SPLITTER
import org.matamem.modals.Group
import org.matamem.modals.Note
import org.matamem.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(repository: Repository) : ViewModel() {
    var repository = repository
    var backStackCount = MutableLiveData<Int>().apply{
        value = getBackStackCount()
    }

    var copyImgVisible = MutableLiveData<Boolean>().apply { value = false }

    var groupList  = repository.getGroups()

    var readMode = MutableLiveData<Boolean>().apply {
        value = false
    }

    val currentLink =  MutableLiveData<String>().apply {
        value = getLinks()?.last()

    }

    var onBackPressed = MutableLiveData<Boolean>().apply { value = false }

    var text : String = ""


    fun onCopyText(str : String){
        text = str

        copyImgVisible.value = true

         Thread {
            Thread.sleep(5000)
            copyImgVisible.postValue(false)
        }.start()

    }


    public fun addGroup( name : String){
        repository.insertGroup(Group(name))
    }
    private fun getBackStackCount() : Int{
        return getLinks()?.size!! - 1
    }

    fun addTextToGroup(note : Note){
        repository.insertNote(note);
    }

    fun onBackPressed() {
        var links = mutableListOf<String>()
        links.addAll(getLinks()!!)
        if(links.size > 1){

            links.removeLast()

            repository.saveLinks(links.joinToString(separator = SPLITTER))

            currentLink.value = links.last()

        }else {

            onBackPressed.value = true
        }

        backStackCount.value = getBackStackCount()
    }

    private fun getLinks() : List<String>?{
        return repository.getLinks()?.split(SPLITTER)
    }

    fun doubleClick() {
        readMode.value = readMode.value?.not()
    }

    fun handleOnLinkChange(str: String) {
        var list = repository.getLinks()?.split(SPLITTER)

        var historyList :String?

        if(list?.size!! > 3){
            historyList = list.subList(list.size -4 , list.size -1).joinToString (SPLITTER)
        }else{
            historyList = repository.getLinks()
        }

        var result = historyList + SPLITTER + str


        repository.saveLinks(result)
        currentLink.value = str

        backStackCount.value = getBackStackCount()



    }
}