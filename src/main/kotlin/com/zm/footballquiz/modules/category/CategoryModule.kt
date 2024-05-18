package com.zm.footballquiz.modules.category

import com.zm.footballquiz.model.dto.CreateCategoryBody
import com.zm.footballquiz.model.dto.UpdateCategoryBody
import com.zm.footballquiz.modules.checkAdminPermission
import com.zm.footballquiz.modules.receive
import com.zm.footballquiz.modules.successResult
import com.zm.footballquiz.statuspages.ApplicationException
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.categoryModule() {

    val controller by inject<CategoryController>()

    route("category") {

        post("create") {
            checkAdminPermission {
                receive<CreateCategoryBody> { createCategoryBody ->
                    controller.insertCategory(createCategoryBody)
                    call.respond(successResult(null))
                }
            }
        }

        post("update") {
            checkAdminPermission {
                receive<UpdateCategoryBody> { updateCategoryBody ->
                    controller.updateCategory(updateCategoryBody)
                }
            }
        }

        post("remove/{id}") {
            checkAdminPermission {
                val id = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
                controller.deleteCategoryById(id)
                call.respond(successResult(null))
            }
        }

        get("{id}") {
            val id = call.parameters["id"]?.toInt() ?: throw ApplicationException.BadRequest()
            val category = controller.getCategoryById(id)
            call.respond(successResult(category))
        }

        get("all") {
            val categories = controller.getCategories()
            call.respond(successResult(categories))
        }
    }
}