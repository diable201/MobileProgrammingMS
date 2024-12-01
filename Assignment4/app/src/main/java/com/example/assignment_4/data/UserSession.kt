package com.example.assignment_4.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSession @Inject constructor() {
    var currentUser: String? = null
}