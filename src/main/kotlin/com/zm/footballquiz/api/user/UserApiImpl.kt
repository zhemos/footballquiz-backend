package com.zm.footballquiz.api.user

import com.zm.footballquiz.db.dao.UserDao
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.UserUpdateBody
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.PasswordManagerContract
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object UserApiImpl : UserApi, KoinComponent {

    private val usersDao by inject<UserDao>()
    private val passwordEncryption by inject<PasswordManagerContract>()

    override fun createUser(createUserBody: CreateUserBody, userRole: User.Role): User {
        val encryptedUser = createUserBody.copy(
            password = passwordEncryption.encryptPassword(createUserBody.password)
        )
        val key = usersDao.insertUser(encryptedUser, userRole)
        return key?.let {
            usersDao.getUserById(it)
        } ?: throw ApplicationException.Generic("invalid create user")
    }

    override fun getUsers(): List<User> {
        return usersDao.getUsers()
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

    override fun deleteUserById(userId: Int) {
        usersDao.deleteUserById(userId)
    }

    override fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): User? {
        return usersDao.updateUser(userId, userUpdateBody)
    }
}