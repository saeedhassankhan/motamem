package org.matamem.modals

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
class Note(var text : String?) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var group_id = 0

}