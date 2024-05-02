package com.zm.api.user

import com.zm.db.dao.UserDao
import com.zm.model.CreateUserBody
import com.zm.model.User
import com.zm.util.PasswordManagerContract
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UserApiImpl : UserApi, KoinComponent {

    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()

    override fun createUser(createUserBody: CreateUserBody): User? {
        val encryptedUser = createUserBody.copy(
            password = passwordEncryption.encryptPassword(createUserBody.password)
        )
        usersDao.insertUser(encryptedUser)?.let { userId ->
            return usersDao.getUserById(userId)
        }
        return null
    }

    override fun getUserById(id: Int): User? {
        return null
    }

    override fun getUserByLogin(login: String): User? {
        return usersDao.getUserByLogin(login)
    }

    override fun getUserByLoginOrEmail(login: String, email: String): User? {
        return usersDao.getUserByLoginOrEmail(login, email)
    }
}