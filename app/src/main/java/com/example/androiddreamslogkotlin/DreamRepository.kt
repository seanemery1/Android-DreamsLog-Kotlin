package com.example.androiddreamslogkotlin


import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class DreamRepository(private val dreamDao: DreamDAO) {

    val allDreams: Flow<List<Dream>> = dreamDao.getAllDreamsAsc()

    suspend fun insert(dream:Dream) {
        dreamDao.insert(dream)
    }
    suspend fun delete(id:Int) {
        dreamDao.delete(id)
    }
    suspend fun update(title:String,
                       content:String,
                       reflection:String,
                       emotion:String,
                       date:String,
                       id:Int) {
        dreamDao.update(title, content, reflection, emotion, date, id)
    }

    fun select(id:Int): LiveData<Dream> {
        return dreamDao.select(id)
    }
}