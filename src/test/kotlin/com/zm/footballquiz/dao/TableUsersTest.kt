package com.zm.footballquiz.dao

import com.zm.footballquiz.db.dao.TableSingleModeStatistics
import com.zm.footballquiz.db.dao.TableUsers
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenValidCreateUserBody
import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenSingleModeStatistics
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.dto.UserUpdateBody
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TableUsersTest : BaseDaoTest() {

    @Test
    fun `insert user`() = withTables(TableUsers, TableSingleModeStatistics) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = TableUsers.insertUser(createUserBody, role)
        val singleModeStatistics = givenSingleModeStatistics()
        userId?.let {
            val user = TableUsers.getUserById(it)
            val expected = User(
                id = it,
                login = createUserBody.login,
                email = createUserBody.email,
                password = createUserBody.password,
                role = role.value,
                nickname = "nickname",
                country = createUserBody.country,
                singleModeStatistics = singleModeStatistics,
            )
            assertEquals(expected, user)
        } ?: throw IllegalStateException("UserId cannot be null")
    }

    @Test
    fun `get user by login`() = withTables(TableUsers) {
        val login = "login"
        val email = "username@domain.com"
        val createUserBody = CreateUserBody(
            login = login,
            email = email,
            password = "root",
            country = "by"
        )
        val role = User.Role.User
        val userId = TableUsers.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val userByLogin = TableUsers.getUserByLogin(login)
        val userByEmail = TableUsers.getUserByLogin(email)
        val singleModeStatistics = givenSingleModeStatistics()
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody.country,
            singleModeStatistics = singleModeStatistics,
        )
        assertEquals(expected, userByLogin)
        assertEquals(expected, userByEmail)
    }

    @Test
    fun `get user by login or email`() = withTables(TableUsers) {
        val login = "login"
        val email = "username@domain.com"
        val createUserBody = CreateUserBody(
            login = login,
            email = email,
            password = "root",
            country = "by"
        )
        val role = User.Role.User
        val userId = TableUsers.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val user = TableUsers.getUserByLoginOrEmail(login, email)
        val singleModeStatistics = givenSingleModeStatistics()
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody.country,
            singleModeStatistics = singleModeStatistics,
        )
        assertEquals(expected, user)
    }

    @Test
    fun `get users`() = withTables(TableUsers) {
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
        val user1Id = TableUsers.insertUser(createUserBody1, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val user2Id = TableUsers.insertUser(createUserBody2, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val actual = TableUsers.getUsers()
        val singleModeStatistics = givenSingleModeStatistics()
        val expected1 = User(
            id = user1Id,
            login = createUserBody1.login,
            email = createUserBody1.email,
            password = createUserBody1.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody1.country,
            singleModeStatistics = singleModeStatistics,
        )
        val expected2 = User(
            id = user2Id,
            login = createUserBody2.login,
            email = createUserBody2.email,
            password = createUserBody2.password,
            role = role.value,
            nickname = "nickname",
            country = createUserBody2.country,
            singleModeStatistics = singleModeStatistics,
        )
        assertEquals(listOf(expected1, expected2), actual)
    }

    @Test
    fun `delete user by id statistics must delete also`() = withTables(TableUsers, TableSingleModeStatistics) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = TableUsers.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        TableUsers.deleteUserById(userId)
        val users = TableUsers.getUsers()
        val singleModeStatistics = TableSingleModeStatistics.getStatistics()
        assertEquals(0, users.size)
        assertEquals(0, singleModeStatistics.size)
    }

    @Test
    fun `update user`() = withTables(TableUsers) {
        val createUserBody = givenValidCreateUserBody()
        val role = User.Role.User
        val userId = TableUsers.insertUser(createUserBody, role)
            ?: throw IllegalStateException("UserId cannot be null")
        val userUpdateBody = UserUpdateBody("new nickname", "ua")
        val actual = TableUsers.updateUser(userId, userUpdateBody)
        val singleModeStatistics = givenSingleModeStatistics()
        val expected = User(
            id = userId,
            login = createUserBody.login,
            email = createUserBody.email,
            password = createUserBody.password,
            role = role.value,
            nickname = "new nickname",
            country = "ua",
            singleModeStatistics = singleModeStatistics,
        )
        assertEquals(expected, actual)
    }
}