package com.example.todomanager.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todomanager.R
import com.example.todomanager.model.ToDo

class ToDoAdapter(
    private val deleteCallback: (ToDo) -> Unit,
    private val updateStatusCallback: (ToDo) -> Unit,
    private val detailCallback: (ToDo) -> Unit
) : ListAdapter<ToDo, ToDoAdapter.ToDoViewHolder>(ToDoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ToDoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tvTodoTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvTodoDescription)
        private val dueDateTextView: TextView = itemView.findViewById(R.id.tvTodoDuedate)
        private val isCompletedCheckBox: CheckBox = itemView.findViewById(R.id.cbCompleted)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.ibDelete)

        fun bind(todo: ToDo) {
            titleTextView.text = todo.title
            descriptionTextView.text = todo.description
            dueDateTextView.text = todo.dueDate
            isCompletedCheckBox.isChecked = todo.isCompleted

            itemView.setOnClickListener {
                detailCallback(todo)
            }

            deleteButton.setOnClickListener {
                deleteCallback(todo)
            }

            isCompletedCheckBox.setOnCheckedChangeListener { _, isChecked ->
                updateStatusCallback(todo.copy(isCompleted = isChecked))
            }
        }
    }

    class ToDoDiffCallback : DiffUtil.ItemCallback<ToDo>() {
        override fun areItemsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ToDo, newItem: ToDo): Boolean {
            return oldItem == newItem
        }
    }
}
