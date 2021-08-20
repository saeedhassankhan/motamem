package org.matamem.repository

import androidx.lifecycle.LiveData
import org.matamem.Const.Companion.HOME_PAGE_URL
import org.matamem.Const.Companion.LAST_SAVED_LINK
import org.matamem.DataPreferencesStore
import org.matamem.db.GroupDao
import org.matamem.db.NoteDao
import org.matamem.modals.Group
import org.matamem.modals.Note
import javax.inject.Inject

 class Repository @Inject constructor(var noteDao : NoteDao, var groupDao : GroupDao, var  dataPreferencesStore : DataPreferencesStore ) {

     fun insertGroup(group: Group) {
         groupDao.insertGroup(group)
     }

     fun deleteGroup(groupName: Group) {
         groupDao.deleteGroup(groupName)
     }
     fun updateGroup(grp : Group){
         groupDao.updateGroup(grp)
     }

     fun getGroups() : LiveData<List<Group>> {
         return groupDao.getGroupItems()
     }

     //*******************************

     fun getNotes(id : Int) : LiveData<List<Note>>{
         return noteDao.getNotes(id)
     }

     fun deleteNote(note : Note){
         noteDao.deleteNote(note)
     }

     fun insertNote(note : Note){
         noteDao.insertNote(note)
     }

     fun updateNote(txt : Note){
         noteDao.updateNote(txt)
     }

     //*******************************

     fun saveLinks(link: String) {
         dataPreferencesStore.save(LAST_SAVED_LINK, link)
     }

     fun getLinks(): String? {
         return dataPreferencesStore.getString(LAST_SAVED_LINK, HOME_PAGE_URL)
     }
 }