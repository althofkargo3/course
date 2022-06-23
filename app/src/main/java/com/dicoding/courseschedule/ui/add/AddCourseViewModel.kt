package com.dicoding.courseschedule.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.util.Event

class AddCourseViewModel(private val repository: DataRepository) : ViewModel() {

    private val _saved = MutableLiveData<Event<Boolean>>()
    val saved: LiveData<Event<Boolean>>
        get() = _saved

    private val _courseName = MutableLiveData<String>()
    val courseName: LiveData<String> = _courseName
    fun setCourseName(name: String) {
        _courseName.value = name
    }

    private val _day = MutableLiveData<Int>()
    val day: LiveData<Int> = _day
    fun setDay(name: Int) {
        _day.value = name
    }

    private val _startTime = MutableLiveData<String>()
    val startTime: LiveData<String> = _startTime
    fun setStartTime(time: String) {
        _startTime.value = time
    }

    private val _endTime = MutableLiveData<String>()
    val endTime: LiveData<String> = _endTime
    fun setEndTime(time: String) {
        _endTime.value = time
    }

    private val _lecturer = MutableLiveData<String>()
    val lecturer: LiveData<String> = _lecturer
    fun setLecturer(name: String) {
        _lecturer.value = name
    }

    private val _note = MutableLiveData<String>()
    val note: LiveData<String> = _note
    fun setNote(name: String) {
        _note.value = name
    }

    fun insertCourse(
        courseName: String,
        day: Int,
        startTime: String,
        endTime: String,
        lecturer: String,
        note: String
    ) {
        if (courseName.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            _saved.value = Event(false)
            return
        }

        val course = Course(
            courseName = courseName,
            day = day + 1,
            startTime = startTime,
            endTime = endTime,
            lecturer = lecturer,
            note = note
        )
        repository.insert(course)
        _saved.value = Event(true)
    }
}