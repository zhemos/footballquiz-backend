package com.zm.footballquiz.routing.auth

import com.google.gson.reflect.TypeToken
import com.zm.footballquiz.instrumentation.AuthInstrumentation
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenLoginCredentialsBody
import com.zm.footballquiz.instrumentation.AuthInstrumentation.givenValidCreateUserBody
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.modules.WrapperResponse
import com.zm.footballquiz.modules.auth.AuthController
import com.zm.footballquiz.modules.auth.authModule
import com.zm.footballquiz.routing.BaseRoutingTest
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LoginControllerTestRoutingTest : BaseRoutingTest() {

    private val authController: AuthController = mockk()

    @Before
    fun setup() {
        koinModules = module {
            single { authController }
        }

        moduleList = {
            install(Routing) {
                authModule()
            }
        }
    }

    @Before
    fun clearMocks() {
        io.mockk.clearMocks(authController)
    }

    @Test
    fun `when login is success, we return response credentials`() = withBaseTestApplication {
        val credentialsResponse = AuthInstrumentation.givenCredentialsResponse()
        coEvery { authController.login(any()) } returns credentialsResponse

        val body = toJsonBody(givenLoginCredentialsBody())
        val call = handleRequest(HttpMethod.Post, "/auth/login") {
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(body)
        }
        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
            val type = object : TypeToken<WrapperResponse<CredentialsResponse>>() {}.type
            val wrapper: WrapperResponse<CredentialsResponse> = response.parseBody(type)
            assertEquals(wrapper.code, 200)
            assertEquals(credentialsResponse, wrapper.data)
        }
    }

    @Test
    fun `when login throws exception, we return 400 error`() = withBaseTestApplication {
        coEvery { authController.login(any()) } throws ApplicationException.Generic("exception")

        val body = toJsonBody(givenValidCreateUserBody())
        val exception = assertThrows<ApplicationException.Generic> {
            handleRequest(HttpMethod.Post, "/auth/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(body)
            }
        }
        assertEquals(exception.message, "exception")
    }
}