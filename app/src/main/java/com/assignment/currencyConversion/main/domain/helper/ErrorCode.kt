package com.assignment.currencyConversion.main.domain.helper

annotation class ErrorCode{
    companion object{
        const val  HTTP_ACCESS_KEY_INVALID="HTTP_101"
        const val HTTP_MONTHLY_PLAN_EXHAUST="HTTP_104"
        const val HTTP_END_POINT_NOT_EXIST="HTTP_103"
        const val HTTP_ACCOUNT_INACTIVE="HTTP_102"
        const val UNKNOWN_ERROR="Unknown Error"
    }
}
