package com.sptest.project_SP_TroodTest.exceptions

class ProfileNotFoundException(userId: Long) : RuntimeException("Profile not found for user id $userId")