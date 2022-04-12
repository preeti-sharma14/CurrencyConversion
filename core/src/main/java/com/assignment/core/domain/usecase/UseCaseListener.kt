package com.assignment.core.domain.usecase

interface UseCaseListener {
    fun onPreExecute()
    fun onPostExecute()
}