package com.example.androiddreamslogkotlin

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow



@Dao
interface DreamDAO {

    @Query("SELECT * FROM dream_table ORDER BY date ASC")
    fun getAllDreamsAsc() : Flow<List<Dream>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream:Dream)

    @Query("UPDATE dream_table SET title=:title, content=:content, reflection=:reflection, emotion=:emotion, date=:date WHERE id=:id")
    suspend fun update(title:String,
                       content:String,
                       reflection:String,
                       emotion:String,
                       date:String,
                       id:Int)

    @Query("DELETE FROM dream_table WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM dream_table WHERE id=:id")
    fun select(id:Int): LiveData<Dream>
}