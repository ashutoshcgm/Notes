package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(),INotesRVAdapter {
    lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView)
        val noteText=findViewById<EditText>(R.id.textEnter)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=NotesRVAdapter(this,this)
        recyclerView.adapter=adapter

        viewModel=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, Observer {list ->
            list?.let{
                adapter.updateList(it)
            }
        })
        val submit:Button=findViewById(R.id.submit)
        submit.setOnClickListener {
            val finalSubmitText=noteText.editableText.toString()
            if (finalSubmitText.isNotEmpty()){
                viewModel.insertNote(Note(finalSubmitText))
                Toast.makeText(this,"Inserted New Note ",Toast.LENGTH_SHORT).show()
                noteText.setText("")
            }

        }

    }
    override fun onItemClicked(note:Note){
        viewModel.deleteNote(note)
        Toast.makeText(this,"Deleted Note",Toast.LENGTH_SHORT).show()

    }
}