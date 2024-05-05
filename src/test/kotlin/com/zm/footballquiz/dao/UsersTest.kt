package com.zm.footballquiz.dao

import com.zm.footballquiz.controllers.instrumentation.RegistrationControllerInstrumentation.givenValidCreateUserBody
import com.zm.footballquiz.db.dao.Users
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserUpdateBody
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersTest : BaseDaoTest() {

    @Test
    fun `insert user`() = withTables(Users) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = Users.insertUser(createUserBody, role)
        userId?.let {
            val user = Users.getUserById(userId)
            val expected = User(
                id = userId,
                login = createUserBody.login,
                email = createUserBody.email,
                password = createUserBody.password,
                role = role.value,
                nickname = "nickname",
                country = createUserBody.country,
            )
            assertEquals(expected, user)
        } ?: throw IllegalStateException("UserId cannot be null")
    }

    @Test
    fun `get user by login`() = withTables(Users) {
        val login = "login"
        val email = "username@domain.com"
        val createUserBody = CreateUserBody(
            login = login,
            email = email,
            password = "root",
            country = "by"
        )
        val role = User.Role.User
        val userId = Users.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val userByLogin = Users.getUserByLogin(login)
        val userByEmail = Users.getUserByLogin(email)
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody.country,
        )
        assertEquals(expected, userByLogin)
        assertEquals(expected, userByEmail)
    }

    @Test
    fun `get user by login or email`() = withTables(Users) {
        val login = "login"
        val email = "username@domain.com"
        val createUserBody = CreateUserBody(
            login = login,
            email = email,
            password = "root",
            country = "by"
        )
        val role = User.Role.User
        val userId = Users.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val user = Users.getUserByLoginOrEmail(login, email)
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody.country,
        )
        assertEquals(expected, user)
    }

    @Test
    fun `get users`() = withTables(Users) {
        val role = User.Role.User
        val createUserBody1 = CreateUserBody(
            login = "login",
            email = "username@domain.com",
            password = "root",
            country = "by"
        )
        val createUserBody2 = CreateUserBody(
            login = "login2",
            email = "username2@domain.com",
            password = "root",
            country = "by"
        )
        val user1Id = Users.insertUser(createUserBody1, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val user2Id = Users.insertUser(createUserBody2, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val actual = Users.getUsers()
        val expected1 = User(
            id = user1Id,
            login = createUserBody1.login,
            email = createUserBody1.email,
            password = createUserBody1.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody1.country,
        )
        val expected2 = User(
            id = user2Id,
            login = createUserBody2.login,
            email = createUserBody2.email,
            password = createUserBody2.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody2.country,
        )
        assertEquals(listOf(expected1, expected2), actual)
    }

    @Test
    fun `delete user by id`() = withTables(Users) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = Users.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        Users.deleteUserById(userId)
        val users = Users.getUsers()
        assertEquals(0, users.size)
    }

    @Test
    fun `update user`() = withTables(Users) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = Users.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val userUpdateBody = UserUpdateBody("new nickname", "ua")
        val actual = Users.updateUser(userId, userUpdateBody)
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "new nickname",
            country = "ua",
        )
        assertEquals(expected, actual)
    }
}