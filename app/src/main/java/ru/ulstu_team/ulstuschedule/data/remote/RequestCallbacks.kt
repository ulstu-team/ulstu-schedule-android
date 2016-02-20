package ru.ulstu_team.ulstuschedule.data.remote

interface RequestCallbacks {
    fun onSuccess()
    fun onError(e: Exception)
}