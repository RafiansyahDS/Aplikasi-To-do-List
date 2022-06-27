package com.dicoding.todoapp.ui.add

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DatePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()
    private lateinit var addTaskViewModel: AddTaskViewModel

    private lateinit var edtTitle: TextInputEditText
    private lateinit var edtDesc: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)
        val factory = ViewModelFactory.getInstance(this)
        addTaskViewModel = ViewModelProvider(this, factory).get(AddTaskViewModel::class.java)

        edtTitle = findViewById(R.id.add_ed_title)
        edtDesc = findViewById(R.id.add_ed_description)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                val title = if (!edtTitle.text.isNullOrBlank()){
                    edtTitle.text.toString()
                }else {
                    "No Title"
                }
                val desc = if(!edtDesc.text.isNullOrBlank()){
                    edtTitle.text.toString()
                }else{
                    "No Description"
                }
                val date = dueDateMillis
                val task = Task(title = title, description = desc, dueDateMillis = date)
                addTaskViewModel.insertTask(task)

                Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, TaskActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}