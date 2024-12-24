package com.example.noteapp.ui.fragments.note

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNoteDetail2Binding
import java.text.SimpleDateFormat
import java.util.Date

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetail2Binding
    private var noteId: Int? = null
    private var color: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetail2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvDate.text = getCurrentTime()
        if (binding.titleEditText.text != null && binding.titleEditText.text != null) {
            binding.tvSave.visibility = View.VISIBLE
        }
        updateNote()
        setupListener()

    }

    private fun updateNote() {
        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
        }
        if (noteId != -1) {
            val id = App.appDatabase?.noteDao()?.getById(noteId!!)
            id?.let { item ->
                binding.titleEditText.setText(item.title)
                binding.textEditText.setText(item.description)
                binding.tvDate.text = item.date
            }
        }
    }

    private fun setupListener() = with(binding) {
        ivColor.setOnClickListener {
            Log.d("nurba", "ivColor click")
            showColorDialog()
        }
        ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        tvSave.setOnClickListener {
            val title = titleEditText.text.toString()
            val text = textEditText.text.toString()
            val data = tvDate.text.toString()
            val color = color
            if (noteId != -1) {
                val updateNote = NoteModel(title, text, data, color.hashCode())
                updateNote.id = noteId!!
                App.appDatabase?.noteDao()?.updateNote(updateNote)

            } else {
                App.appDatabase?.noteDao()?.insertNote(NoteModel(title, text, data, color.hashCode()))
            }
            findNavController().navigateUp()
        }
    }

    private fun showColorDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.picked, null)
        builder.setView(dialogView)
        val dialog = builder.create()
        dialogView.findViewById<View>(R.id.color_yellow).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.yellow)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color_purple).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.purple)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color_pink).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.pink)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color_red).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.red)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color_green).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.green)
            dialog.dismiss()
        }
        dialogView.findViewById<View>(R.id.color_blue).setOnClickListener {
            color = ContextCompat.getColor(requireContext(), R.color.blue)
            dialog.dismiss()
        }
        dialog.show()

        val window = dialog.window
        val layoutParams = window?.attributes

        layoutParams?.gravity = Gravity.END or Gravity.TOP
        layoutParams?.x = 100
        layoutParams?.y = 100

        window?.attributes = layoutParams
    }


    private fun getCurrentTime(): String {
        val date = SimpleDateFormat("dd MMMM HH:mm")
        return date.format(Date())
    }
}