package com.example.noteapp.ui.fragments.noteapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentNoteDetailBinding
import com.example.noteapp.ui.fragments.noteapp.data.models.NoteModel
import java.text.SimpleDateFormat
import java.util.Date


class NoteDetailFragment : Fragment() {
    private lateinit var binding: FragmentNoteDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding.titleEdit.text != null && binding.textEdit.text != null){
            binding.tvSave.visibility = View.VISIBLE

        }
        setupListener()
    }

    private fun setupListener() {
        tvSave.setOnClickListener {
            val title = binding.titleEdit.text.toString()
            val text = binding.textEdit.text.toString()
            val date = getCurrentTime()
            App.appDatabase?.noteDao()?.insertNote(NoteModel(title, text, date))
            findNavController().navigateUp()
        }
    }
private fun getCurrentTime():String{
    val date = SimpleDateFormat("dd.MM.yyyy hh:mm")
    return date.format(Date())

}

}