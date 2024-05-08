package com.zm.footballquiz.modules.statistics

import com.zm.footballquiz.model.dto.UpdateSingleModeStatisticsBody
import com.zm.footballquiz.modules.checkAdminPermission
import com.zm.footballquiz.modules.fetchSingleModeStatistics
import com.zm.footballquiz.modules.receive
import com.zm.footballquiz.modules.successResult
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.statisticsModule() {

    val controller by inject<StatisticsController>()

    route("statistics") {

        post("singleMode/update") {
            receive<UpdateSingleModeStatisticsBody> { updateStatisticsBody ->
                fetchSingleModeStatistics { statisticsId ->
                    val statistics = controller.updateSingleModeStatistics(statisticsId, updateStatisticsBody)
                    call.respond(successResult(statistics))
                }
            }
        }

        post("singleMode/update/{id}") {
            val statisticsId = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
            receive<UpdateSingleModeStatisticsBody> { updateStatisticsBody ->
                checkAdminPermission {
                    val statistics = controller.updateSingleModeStatistics(statisticsId, updateStatisticsBody)
                    call.respond(successResult(statistics))
                }
            }
        }
    }
}