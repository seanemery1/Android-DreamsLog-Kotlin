package com.example.androiddreamslogkotlin


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dream_table")
class Dream(@ColumnInfo(name="title") var title:String,
                     @ColumnInfo(name="content") var content:String,
                     @ColumnInfo(name="reflection") var reflection:String,
                     @ColumnInfo(name="emotion") var emotion:String,
                     @ColumnInfo(name="date") var date:String){
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id:Int = 0

}