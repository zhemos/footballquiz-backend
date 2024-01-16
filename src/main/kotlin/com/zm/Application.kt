package com.zm

import com.auth0.jwt.interfaces.JWTVerifier
import com.typesafe.config.ConfigFactory
import com.zm.api.injection.ApiInjection
import com.zm.api.user.UserApi
import com.zm.config.Config
import com.zm.config.JwtConfig
import com.zm.config.TokenProvider
import com.zm.modules.auth.authenticationModule
import com.zm.modules.injection.ModulesInjection
import com.zm.plugins.*
import com.zm.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.dsl.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun main() {
//    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
    val environment = System.getenv()["ENVIRONMENT"] ?: handleDefaultEnvironment()
    val config = extractConfig(environment, HoconApplicationConfig(ConfigFactory.load()))
    embeddedServer(Netty, host = config.host, port = config.port) {
        println("Starting instance in ${config.host}:${config.port}")
        module {
            install(Koin) {
                modules(
                    module {
                        single { config }
                        single<TokenProvider> { config.jwt }
                        single<JWTVerifier> { config.jwt.verifier }
                    },
                    ApiInjection.koinBeans,
                    ModulesInjection.koinBeans,
                )
            }
            initModules(config.jwt)
        }
    }.start(wait = true)
}

fun handleDefaultEnvironment(): String {
    println("Falling back to default environment 'dev'")
    return "dev"
}

fun extractConfig(environment: String, hoconConfig: HoconApplicationConfig): Config {
    val hoconEnvironment = hoconConfig.config("ktor.deployment.$environment")
    val jwtConfig = hoconConfig.config("jwt")
    return Config(
        host = hoconEnvironment.property("host").getString(),
        port = Integer.parseInt(hoconEnvironment.property("port").getString()),
        databaseHost = hoconEnvironment.property("databaseHost").getString(),
        databasePort = hoconEnvironment.property("databasePort").getString(),
        jwt = JwtConfig(
            secret = jwtConfig.property("secret").getString(),
            issuer = jwtConfig.property("issuer").getString(),
            audience = jwtConfig.property("audience").getString(),
            realm = jwtConfig.property("realm").getString()
        )
    )
}

fun Application.initModules(jwtConfig: JwtConfig) {
    val userApi by inject<UserApi>()
    val jwtVerifier by inject<JWTVerifier>()
    authenticationModule(userApi, jwtConfig, jwtVerifier)
    configureMonitoring()
    configureSerialization()
    configureRouting()
}
