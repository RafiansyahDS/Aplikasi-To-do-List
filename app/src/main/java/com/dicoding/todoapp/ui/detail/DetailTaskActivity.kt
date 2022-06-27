package com.dicoding.todoapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.add.AddTaskActivity
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText

class DetailTaskActivity : AppCompatActivity() {

    private lateinit var edtTitle: TextInputEditText
    private lateinit var edtDesc: TextInputEditText
    private lateinit var edtDueDate: TextInputEditText
    private lateinit var btnHapus: Button

    private lateinit var taskDetailViewModel: DetailTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        edtTitle = findViewById(R.id.detail_ed_title)
        edtDesc = findViewById(R.id.detail_ed_description)
        edtDueDate = findViewById(R.id.detail_ed_due_date)
        btnHapus = findViewById(R.id.btn_delete_task)

        val factory = ViewModelFactory.getInstance(this)
        taskDetailViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)

        val taskId = intent.getIntExtra(TASK_ID, -1)
        if(taskId != -1){
            taskDetailViewModel.setTaskId(taskId)
            taskDetailViewModel.task.observe(this){
                edtTitle.setText(it.title)
                edtDueDate.setText(DateConverter.convertMillisToString(it.dueDateMillis))
                edtDesc.setText(it.description)
            }
        }

        btnHapus.setOnClickListener {
            taskDetailViewModel.deleteTask()
            taskDetailViewModel.task.removeObservers(this)
            finish()
        }
    }
}