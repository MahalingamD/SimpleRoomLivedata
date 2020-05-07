package com.maha.simpleroom.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id=0

    @ColumnInfo(name = "username")
    var name=""

    @ColumnInfo(name = "country",defaultValue = "india")
    var country=""

    @ColumnInfo(name = "money")
    var money=""
}