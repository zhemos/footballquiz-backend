package com.zm.footballquiz.routing.statistics

import com.zm.footballquiz.instrumentation.StatisticsInstrumentation.givenUpdateSingleModeStatisticsBodyOfTypeUpdate
import com.zm.footballquiz.modules.statistics.StatisticsController
import com.zm.footballquiz.modules.statistics.statisticsModule
import com.zm.footballquiz.routing.BaseRoutingTest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import org.koin.dsl.module
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UpdateSingleModeStatisticsRoutingTest : BaseRoutingTest() {

    private val statisticsController: StatisticsController = mockk()

    @Before
    fun setup() {
        koinModules = module {
            single { statisticsController }
        }

        moduleList = {
            install(Routing) {
                statisticsModule()
            }
        }
    }

    @Before
    fun clearMocks() {
        io.mockk.clearMocks(statisticsController)
    }

    @Test
    fun `test`() = withBaseTestApplication {
        val body = toJsonBody(givenUpdateSingleModeStatisticsBodyOfTypeUpdate())
        val call = handleRequest(HttpMethod.Post, "/statistics/singleMode/update") {
            addHeader("Authorization", "Beared dfsg")
            addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            setBody(body)
        }
        with(call) {
            assertEquals(HttpStatusCode.OK, response.status())
        }
    }
}