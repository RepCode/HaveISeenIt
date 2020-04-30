package com.utn.haveiseenit.services

import com.utn.haveiseenit.database.UserDao
import com.utn.haveiseenit.entities.User

class UserAuthentication(private val userDao: UserDao) {

    fun login(
        email: String,
        password: String,
        callback: (user: User?, error: AuthenticationError?) -> Unit
    ) {
        val result = authenticateUser(email, password)
        ApplicationUser.userId = result.first?.id
        callback(result.first, result.second)
    }

    fun createUser(
        userName: String,
        email: String,
        password: String, callback: (error: AuthenticationError?) -> Unit
    ) {
        callback(
            try {
                userDao.insertPerson(User(0, userName, email, hashPassword(password)))
                null
            } catch (ex: Throwable) {
                AuthenticationError("Error creating user")
            }
        )
    }

    // TODO: implement hashing
    private fun hashPassword(password: String): String {
        return password
    }

    private fun authenticateUser(
        email: String,
        password: String
    ): Pair<User?, AuthenticationError?> {

        val user = userDao.loadPersonByEmail(email)

        if(user == null || user?.passHash != hashPassword(password)){
            return Pair(null, AuthenticationError("Invalid credentials"))
        }
        return Pair(user, null)
    }

    class AuthenticationError(private val message: String) {
        fun getErrorMessage(): String {
            return message
        }
    }
}

object ApplicationUser{
    var userId: Int? = null
}