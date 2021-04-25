package com.example.androiddreamslogkotlin

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DreamViewModel (private val repository: DreamRepository) : ViewModel() {

    val allDreams: LiveData<List<Dream>> = repository.allDreams.asLiveData()

    // we want to make sure that view model is running in its own scope
    // in the viewmodel library, it has its own scope
    // viewModelScope
    // you want to launch a new coroutine to run each of the suspend function
    // from your repository. To use viewmodelscope, it allows everything to run based on
    // their lifecycles

    fun insert(dream:Dream) = viewModelScope.launch{
        repository.insert(dream)
    }
    fun delete(id:Int) = viewModelScope.launch{
        repository.delete(id)
    }
    fun update(title:String,
               content:String,
               reflection:String,
               emotion:String,
               date:String,
               id:Int) = viewModelScope.launch{
        repository.update(title, content, reflection, emotion, date, id)
    }
    fun select(id:Int): LiveData<Dream> = repository.select(id)
}

class DreamViewModelFactory(private val repository: DreamRepository) : ViewModelProvider.Factory{

    // override the create method to return the TaskViewModel

    override fun <D : ViewModel?> create(modelClass: Class<D>): D {
        if (modelClass.isAssignableFrom(DreamViewModel::class.java)) {
            return DreamViewModel(repository) as D
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}