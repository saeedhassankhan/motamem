package org.matamem.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import org.matamem.modals.Group

@Dao
interface GroupDao {
    @Insert
    fun insertGroup(grp: Group?)

    @Delete
    fun deleteGroup(grp : Group?): Int

    @Query("DELETE FROM group_table")
    fun deleteAll()


    @Query("SELECT * FROM group_table")
    fun getGroupItems() : LiveData<List<Group>>

    @Update
    fun updateGroup(grp : Group)
}