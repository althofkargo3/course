package com.dicoding.courseschedule.ui.add

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.list.ListActivity
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val edCourseName: EditText = findViewById(R.id.add_ed_name)
        val edLecturer: EditText = findViewById(R.id.add_ed_lecturer)
        val edNote: EditText = findViewById(R.id.add_ed_note)
        val edDay: Spinner = findViewById(R.id.add_sp_day)

        val factory = AddViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)

        edCourseName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setCourseName(s.toString())
            }
        })
        edLecturer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setLecturer(s.toString())
            }
        })
        edNote.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                viewModel.setNote(s.toString())
            }
        })
        edDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                viewModel.setDay(p2)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                viewModel.apply {
                    insertCourse(
                        courseName = courseName.value.toString(),
                        lecturer = lecturer.value.toString(),
                        note = note.value.toString(),
                        day = day.value!!,
                        endTime = endTime.value.toString(),
                        startTime = startTime.value.toString()
                    )
                }
                startActivity(Intent(this, ListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment(view.tag.toString())
        dialogFragment.show(supportFragmentManager, view.id.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val paddedHour = hour.toString().padStart(2, '0')
        val paddedMinute = minute.toString().padStart(2, '0')
        when (tag) {
            getString(R.string.start_time) -> {
                findViewById<TextView>(R.id.tv_start_time).text = "$paddedHour:$paddedMinute"
                viewModel.setStartTime("$paddedHour:$paddedMinute")
            }
            getString(R.string.end_time) -> {
                findViewById<TextView>(R.id.tv_end_time).text = "$paddedHour:$paddedMinute"
                viewModel.setEndTime("$paddedHour:$paddedMinute")
            }
        }
    }
}