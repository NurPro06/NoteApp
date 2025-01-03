package com.example.noteapp.ui.fragments.note

import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteapp.App
import com.example.noteapp.R
import com.example.noteapp.data.models.NoteModel
import com.example.noteapp.databinding.FragmentNote2Binding
import com.example.noteapp.ui.adapters.NoteAdapter
import com.example.noteapp.ui.interfaces.OnClickItem
import com.example.noteapp.utlis.PreferenceHelper
import java.util.jar.Manifest


class NoteFragment : Fragment(), OnClickItem {

    private lateinit var binding: FragmentNote2Binding
    private val noteAdapter = NoteAdapter(this, this)
    private lateinit var sharedPreferences: PreferenceHelper
    private var layoutManager = true

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Разрешение получено", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Разрешение не получено", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNote2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestNotificationPermission()
        initialize()
        setupListener()
        getData()
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when{
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {

                } shoultShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
            }else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            }
        }
    }

    private fun initialize() = with(binding) {
        sharedPreferences.init(requireContext())
        if (sharedPreferences.isLinearLayout()){
            rvNote.layoutManager = LinearLayoutManager(context)
            btnChange.setImageResource(R.drawable.widjets)
        }else{
            rvNote.layoutManager = GridLayoutManager(context, 2)
            btnChange.setImageResource(R.drawable.search)
        }
       rvNote.adapter = noteAdapter
        }


    private fun setupListener() : Unit = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }
        btnChange.setOnClickListener{
            if (noteAdapter.currentList.isNotEmpty()){
                layoutManager = !layoutManager
                if (layoutManager){
                    sharedPreferences.layoutManager = true
                    rvNote.layoutManager = LinearLayoutManager(context)
                    btnChange.setImageResource(R.drawable.widjets)
                }else{
                    sharedPreferences.layoutManager = false
                    rvNote.layoutManager = GridLayoutManager(context, 2)
                    btnChange.setImageResource(R.drawable.search)
                }
            }
        }

    }

    private fun getData() {
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner) { listNote ->
            noteAdapter.submitList(listNote)
        }
    }

    override fun onLongClick(noteModel: NoteModel) {
        val builder = AlertDialog.Builder(requireContext())
        with(builder) {
            setTitle("Удалить заметку")
            setPositiveButton("Удалить") { dialog, _ ->
                App.appDatabase?.noteDao()?.deleteNote(noteModel)
            }
            setNegativeButton("Отмена") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
        builder.create()
    }

    override fun onClick(noteModel: NoteModel) {
        Log.e("ololo", "OnCLick: working note $noteModel")
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }

}

