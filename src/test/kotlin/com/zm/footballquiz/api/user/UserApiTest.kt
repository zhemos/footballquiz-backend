package com.zm.footballquiz.api.user

import com.zm.footballquiz.api.BaseApiTest
import com.zm.footballquiz.db.dao.UserDao
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenValidCreateUserBody
import com.zm.footballquiz.instrumentation.UserInstrumentation.givenUser
import com.zm.footballquiz.model.User
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.PasswordManagerContract
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserApiTest : BaseApiTest() {

    private val userDao: UserDao = mockk()
    private val passwordEncryption: PasswordManagerContract = mockk()
    private val api: UserApi = UserApiImpl

    init {
        startInjection(
            module {
                single { userDao }
                single { passwordEncryption }
            }
        )
    }

    @Before
    fun before() {
        clearMocks(userDao)
        clearMocks(passwordEncryption)
    }

    @Test
    fun `when creating user and it is success, we return user`() {
        val role = User.Role.User
        val createUserBody = givenValidCreateUserBody()
        val user = givenUser()
        every { passwordEncryption.encryptPassword(any()) } returns ""
        every { userDao.insertUser(any(), any()) } returns 1
        every { userDao.getUserById(any()) } returns user

        val actual = api.createUser(createUserBody, role)
        assertEquals(user, actual)
    }

    @Test
    fun `when creating user and it is fail, we return`() {
        val role = User.Role.User
        val createUserBody = givenValidCreateUserBody()
        val user = givenUser()
        every { passwordEncryption.encryptPassword(any()) } returns ""
        every { userDao.insertUser(any(), any()) } returns null
        every { userDao.getUserById(any()) } returns user

        assertThrows(ApplicationException.Generic::class.java) {
            api.createUser(createUserBody, role)
        }
    }

    @Test
    fun `when fetching all users, we return all users`() {
        val users = listOf(
            givenUser(1),
            givenUser(2),
            givenUser(3),
        )
        every { userDao.getUsers() } returns users

        val actual = api.getUsers()
        assertEquals(users, actual)
    }

    @Test
    fun `when fetching user by id and user exists, we return user`() {
        val id = 123
        val user = givenUser(id)
        every { userDao.getUserById(any()) } returns user

        val actual = api.getUserById(id)
        assertEquals(user, actual)
    }

    @Test
    fun `when fetching user by login and user exists, we return user`() {
        val user = givenUser()
        every { userDao.getUserByLogin(any()) } returns user

        val actual = api.getUserByLogin("login")
        assertEquals(user, actual)
    }

    @Test
    fun `when fetching user by login and it does not exist, we return null`() {
        every { userDao.getUserByLogin(any()) } returns null

        val actual = api.getUserByLogin("login")
        assertEquals(null, actual)
    }

    @Test
    fun `when fetching user by login or email and user exists, we return user`() {
        val user = givenUser()
        every { userDao.getUserByLoginOrEmail(any(), any()) } returns user

        val actual = api.getUserByLoginOrEmail("login", "email")
        assertEquals(user, actual)
    }

    @Test
    fun `when fetching user by login or email and it does not exist, we return null`() {
        every { userDao.getUserByLoginOrEmail(any(), any()) } returns null

        val actual = api.getUserByLoginOrEmail("login", "email")
        assertEquals(null, actual)
    }
}