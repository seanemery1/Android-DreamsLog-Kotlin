package com.example.androiddreamslogkotlin


import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var editTextReflection: EditText
    private lateinit var spinnerEmotion: Spinner
    private lateinit var emotions: Array<String>
    private lateinit var buttonSave:Button
    private lateinit var spinnerSelected:String
    private var index:Int = -1

    private val dreamViewModel:DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        index = intent.getIntExtra("id", -1)

        editTextTitle = findViewById(R.id.editText_title)
        editTextContent = findViewById(R.id.editText_content)
        editTextReflection = findViewById(R.id.editText_reflection)
        spinnerEmotion = findViewById(R.id.spinner_emotion)
        emotions = resources.getStringArray(R.array.emotions)

        if (index!=-1) {
            editTextTitle.setText(intent.getStringExtra("title"))
            editTextContent.setText(intent.getStringExtra("content"))
            editTextReflection.setText(intent.getStringExtra("reflection"))
            spinnerEmotion.setSelection(emotions.indexOf(intent.getStringExtra("emotion")))
        }
        spinnerSelected = ""

        spinnerEmotion.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
//                Toast.makeText(this@AddActivity,
//                    emotions[position], Toast.LENGTH_SHORT).show()
                spinnerSelected = emotions[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                spinnerSelected = ""
            }
        }

        buttonSave = findViewById(R.id.button_save)


        buttonSave.setOnClickListener{
            if (TextUtils.isEmpty(editTextTitle.text)||
                TextUtils.isEmpty(editTextContent.text)||
                TextUtils.isEmpty(editTextReflection.text)) {
                toastError("Missing fields")
            } else {

                if (index==-1) {
                    val dream = Dream(editTextTitle.text.toString(),
                        editTextContent.text.toString(),
                        editTextReflection.text.toString(),
                        spinnerSelected, LocalDateTime.now().toString())
                    dreamViewModel.insert(dream)

                } else {
                    dreamViewModel.update(editTextTitle.text.toString(),
                        editTextContent.text.toString(),
                        editTextReflection.text.toString(),
                        spinnerSelected, LocalDateTime.now().toString(),
                        intent.getIntExtra("id",-1))
                }
                finish()
            }
        }

    }

    private fun toastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}