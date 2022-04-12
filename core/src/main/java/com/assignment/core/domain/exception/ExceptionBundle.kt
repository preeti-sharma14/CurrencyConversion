package com.assignment.core.domain.exception

interface ExceptionBundle {
    fun getException():Exception?
    fun getMessage():String
}