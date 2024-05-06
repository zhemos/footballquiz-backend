package com.zm.footballquiz.routing

import com.google.gson.Gson
import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin
import java.lang.reflect.Type

abstract class BaseRoutingTest {

    private val gson = Gson()
    protected var koinModules: Module? = null
    protected var moduleList: Application.() -> Unit = { }

    init {
        stopKoin()
    }

    fun <R> withBaseTestApplication(test: TestApplicationEngine.() -> R) {
        withTestApplication({
            install(ContentNegotiation) { gson { } }
            koinModules?.let {
                install(Koin) {
                    modules(it)
                }
            }
            moduleList()
        }) {
            test()
        }
    }

    fun toJsonBody(obj: Any): String = gson.toJson(obj)

    fun <R> TestApplicationResponse.parseBody(clazz: Class<R>): R {
        return gson.fromJson(content, clazz)
    }

    fun <R> TestApplicationResponse.parseBody(type: Type): R {
        return gson.fromJson(content, type)
    }
}