package com.example.noteapp.ui.adapters

import androidx.recyclerview.widget.ListAdapter
import com.example.noteapp.ui.fragments.noteapp.data.models.NoteModel

class NoteAdapter: ListAdapter<NoteModel, NoteAdapter.ViewHolder>(DiffUtil()) {
}