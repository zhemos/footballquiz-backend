package com.zm.footballquiz.controllers.auth

import com.zm.footballquiz.api.user.UserApi
import com.zm.footballquiz.config.TokenProvider
import com.zm.footballquiz.controllers.BaseControllerTest
import com.zm.footballquiz.controllers.instrumentation.UserModuleInstrumentation.givenUser
import com.zm.footballquiz.modules.auth.AuthController
import com.zm.footballquiz.modules.auth.AuthControllerImpl
import com.zm.footballquiz.routing.instrumentation.AuthControllerInstrumentation.givenCredentialsResponse
import com.zm.footballquiz.routing.instrumentation.AuthControllerInstrumentation.givenLoginCredentialsBody
import com.zm.footballquiz.statuspages.ApplicationException
import com.zm.footballquiz.util.PasswordManagerContract
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginControllerTest : BaseControllerTest() {

    private val userApi: UserApi = mockk()
    private val passwordManager: PasswordManagerContract = mockk()
    private val tokenProvider: TokenProvider = mockk()
    private val controller: AuthController by lazy { AuthControllerImpl() }

    init {
        startInjection(
            module {
                single { userApi }
                single { passwordManager }
                single { tokenProvider }
            }
        )
    }

    @Before
    override fun before() {
        super.before()
        clearMocks(userApi)
        clearMocks(passwordManager)
        clearMocks(tokenProvider)
    }

    @Test
    fun `when we login and user does not exist, we throw exception`() {
        val loginCredentialsBody = givenLoginCredentialsBody()
        coEvery { userApi.getUserByLogin(any()) } returns null

        Assert.assertThrows(ApplicationException.DataNotFound::class.java) {
            runBlocking { controller.login(loginCredentialsBody) }
        }
    }

    @Test
    fun `when we login and password is not valid, we throw exception`() {
        val loginCredentialsBody = givenLoginCredentialsBody()
        coEvery { userApi.getUserByLogin(any()) } returns givenUser()
        coEvery { passwordManager.validatePassword(any(), any()) } returns false

        val exception = Assert.assertThrows(ApplicationException.Generic::class.java) {
            runBlocking { controller.login(loginCredentialsBody) }
        }
        assertEquals(exception.message, "incorrect password")
    }

    @Test
    fun `when we login correctly and user not taken, we return a valid CredentialsResponse`() {
        val loginCredentialsBody = givenLoginCredentialsBody()
        val fakeCredentials = givenCredentialsResponse()

        coEvery { userApi.getUserByLogin(any()) } returns givenUser()
        coEvery { passwordManager.validatePassword(any(), any()) } returns true
        coEvery { tokenProvider.createTokens(any()) } returns fakeCredentials

        runBlocking {
            val credentials = controller.login(loginCredentialsBody)
            assertEquals(fakeCredentials, credentials)
        }
    }
}