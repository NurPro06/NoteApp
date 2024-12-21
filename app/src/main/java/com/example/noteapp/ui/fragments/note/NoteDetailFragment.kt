package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetail2Binding
import java.text.SimpleDateFormat
import java.util.Date

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetail2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (binding.titleEditText.text?.isNotEmpty() == true &&
            binding.textEditText.text?.isNotEmpty() == true) {
            binding.tvSave.visibility = View.VISIBLE
        }
        setupListener()
    }

    private fun setupListener() = with(binding) {
        tvSave.setOnClickListener {
            val title = titleEditText.text.toString()
            val text = textEditText.text.toString()
            val data = getCurrentTime()
            App.appDatabase?.noteDao()?.insertNote(NoteModel(title, text, data))
            findNavController().navigateUp()
        }
    }
    private fun getCurrentTime(): String {
        val date = SimpleDateFormat("dd MMMM HH:mm")
        return date.format(Date())
    }
}