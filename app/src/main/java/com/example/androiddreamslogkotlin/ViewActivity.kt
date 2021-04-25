package com.example.androiddreamslogkotlin


import android.content.Intent
import android.os.Bundle
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.*
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class ViewActivity : AppCompatActivity() {

    private lateinit var textViewTitle: TextView
    private lateinit var textViewContent: TextView
    private lateinit var textViewReflection: TextView
    private lateinit var textViewEmotion: TextView
    private lateinit var buttonUpdate:Button
    private lateinit var buttonDelete:Button
    private lateinit var dream:Dream
    private var index:Int = 0

    private val dreamViewModel:DreamViewModel by viewModels{
        DreamViewModelFactory((application as DreamApplication).repository)
    } // call the one and only repository we created in the Task Application Class
    // so that we are not creating multiple instances of the repository in our app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dream)

        textViewTitle = findViewById(R.id.textView_title_dream)
        textViewContent = findViewById(R.id.textView_content2_dream)
        textViewReflection = findViewById(R.id.textView_reflection2_dream)
        textViewEmotion = findViewById(R.id.textView_emotion2_dream)
        index = intent.getIntExtra("id", -1)

        if (index!=-1) {
            dreamViewModel.select(index).observe(this, Observer {
                if (it!=null) {
                    this.dream = it
                    textViewTitle.text = dream.title
                    textViewContent.text = dream.content
                    textViewReflection.text = dream.reflection
                    textViewEmotion.text = dream.emotion
                }
            })
        }

        buttonUpdate = findViewById(R.id.button_update)
        buttonDelete = findViewById(R.id.button_delete)
        // catch user input error in front end
        buttonUpdate.setOnClickListener{
            val intent = Intent(this@ViewActivity, AddActivity::class.java)
            intent.putExtra("content", dream.content)
            intent.putExtra("id", dream.id)
            intent.putExtra("reflection", dream.reflection)
            intent.putExtra("title", dream.title)
            intent.putExtra("date", dream.date)
            intent.putExtra("emotion", dream.emotion)
            startActivity(intent)
        }

        buttonDelete.setOnClickListener{

            // Alert Box:
            var deleteConfirmation:Boolean = false
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setMessage("Do you really want to delete this dream entry?")
                .setCancelable(false)
                .setPositiveButton("Proceed", DialogInterface.OnClickListener {
                        dialog, id -> finish()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })
            val alert = dialogBuilder.create()
            alert.setTitle("Alert!")
            alert.show()
            
            if (deleteConfirmation) {
                val tempIndex:Int = index;
                index = -1
                dreamViewModel.delete(tempIndex)
                finish()
            }

        }

    }

    private fun toastError(text:String){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}