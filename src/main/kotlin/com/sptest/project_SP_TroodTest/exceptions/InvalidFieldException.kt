package com.sptest.project_SP_TroodTest.exceptions

class InvalidFieldException(
    val fieldName: String,
    val errorMessage: String
) : RuntimeException("Invalid field: $fieldName. Error: $errorMessage") {

}