package com.zm.footballquiz.api.user

import com.zm.footballquiz.db.dao.UserDao
import com.zm.footballquiz.model.CreateUserBody
import com.zm.footballquiz.model.User
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.PasswordManagerContract
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UserApiImpl : UserApi, KoinComponent {

    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()

    override fun createUser(createUserBody: CreateUserBody): User {
        val encryptedUser = createUserBody.copy(
            password = passwordEncryption.encryptPassword(createUserBody.password)
        )
        println("encryptedUser: $encryptedUser")
        val key = usersDao.insertUser(encryptedUser)
        println("KEY: $key")
//        val id = usersDao.insertUser(createUserBody)
        return key?.let {
            usersDao.getUserById(it)
        } ?: throw ApplicationException.Generic("invalid create user")
    }

    override fun getUserById(id: Int): User? {
        return usersDao.getUserById(id)
    }

    override fun getUserByLogin(login: String): User? {
        return usersDao.getUserByLogin(login)
    }

    override fun getUserByLoginOrEmail(login: String, email: String): User? {
        return usersDao.getUserByLoginOrEmail(login, email)
    }
}