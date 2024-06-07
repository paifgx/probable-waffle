package com.example.todomanager.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.todomanager.R
import com.example.todomanager.adapter.ToDoAdapter
import com.example.todomanager.viewmodel.ToDoViewModel
import com.example.todomanager.model.ToDo
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var toDoAdapter: ToDoAdapter
    private val toDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        toDoAdapter = ToDoAdapter(
            { toDoViewModel.delete(it) },
            { toDoViewModel.update(it) },
            { findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavTodoDetail(it)) }
        )
        recyclerView.adapter = toDoAdapter

        toDoViewModel.allTodos.observe(viewLifecycleOwner) { todos ->
            toDoAdapter.submitList(todos)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_add_todo -> {
                        showAddTodoDialog()
                        true
                    }
                    R.id.action_delete_all -> {
                        toDoViewModel.deleteAll()
                        true
                    }
                    R.id.action_delete_completed -> {
                        toDoViewModel.clearCompleted()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun showAddTodoDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_todo, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.etTodoTitle)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.etTodoDescription)
        val dueDateEditText = dialogView.findViewById<EditText>(R.id.etTodoDueDate)

        dueDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                val date = "$dayOfMonth/${month + 1}/$year"
                dueDateEditText.setText(date)
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.show()
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add Todo")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = titleEditText.text.toString().trim()
                val description = descriptionEditText.text.toString().trim()
                val dueDate = dueDateEditText.text.toString().trim()

                if (title.isEmpty()) {
                    titleEditText.error = "Title is required"
                    return@setPositiveButton
                }

                if (dueDate.isEmpty()) {
                    dueDateEditText.error = "Due date is required"
                    return@setPositiveButton
                }

                toDoViewModel.insert(ToDo(title = title, description = description, dueDate = dueDate, isCompleted = false))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
