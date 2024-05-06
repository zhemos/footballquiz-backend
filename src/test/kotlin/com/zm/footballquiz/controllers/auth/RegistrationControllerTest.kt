package com.zm.footballquiz.controllers.auth

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.config.TokenProvider
import com.zm.footballquiz.controllers.BaseControllerTest
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenCredentialsResponse
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenValidCreateUserBody
import com.zm.footballquiz.instrumentation.UserInstrumentation.givenUser
import com.zm.footballquiz.model.User
import com.zm.footballquiz.modules.auth.AuthController
import com.zm.footballquiz.modules.auth.AuthControllerImpl
import com.zm.footballquiz.statuspages.ApplicationException
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegistrationControllerTest : BaseControllerTest() {

    private val userApi: UserApi = mockk()
    private val tokenProvider: TokenProvider = mockk()
    private val controller: AuthController by lazy { AuthControllerImpl() }
    private val userId = 12
    private val role = User.Role.User

    init {
        startInjection(
            module {
                single { userApi }
                single { tokenProvider }
            }
        )
    }

    @Before
    override fun before() {
        super.before()
        clearMocks(userApi)
        clearMocks(tokenProvider)
    }

    @Test
    fun `when creating user and email or password are not valid, we throw exception`() {
        val createUserBody = givenValidCreateUserBody()

        assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.createUser(createUserBody.copy(email = "email"), role) }
            runBlocking { controller.createUser(createUserBody.copy(password = "root"), role) }
        }
    }

    @Test
    fun `when creating user with login or email already taken, we throw exception`() {
        val createUserBody = givenValidCreateUserBody()
        val createdUser = givenUser(userId)

        coEvery { userApi.getUserByLoginOrEmail(any(), any()) } returns createdUser

        assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.createUser(createUserBody, role) }
        }
    }

    @Test
    fun `when creating user with correct information and user not taken, we return a valid CredentialsResponse`() {
        val createUserBody = givenValidCreateUserBody()
        val createdUser = givenUser(userId)
        val fakeCredentials = givenCredentialsResponse()

        coEvery { userApi.getUserByLoginOrEmail(any(), any()) } returns null
        coEvery { userApi.createUser(any(), any()) } returns createdUser
        coEvery { tokenProvider.createTokens(any()) } returns fakeCredentials

        runBlocking {
            val credentials = controller.createUser(createUserBody, role)
            assertEquals(fakeCredentials, credentials)
        }
    }
}