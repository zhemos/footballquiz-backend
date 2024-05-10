package com.zm.footballquiz.config

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.zm.footballquiz.model.dto.CredentialsResponse
import com.zm.footballquiz.model.User
import com.zm.footballquiz.statuspages.ApplicationException
import java.util.*

class Config(
    val host: String,
    val port: Int,
    val databaseHost: String,
    val databasePort: String,
    val databaseName: String,
    val databaseLogin: String,
    val databasePassword: String,
    val jwt: JwtConfig
)

class JwtConfig(
    val secret: String,
    val issuer: String,
    val audience: String,
    val realm: String
) : TokenProvider {
    private val algorithm = Algorithm.HMAC512(secret)
    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    override fun createTokens(user: User): CredentialsResponse {
        val jwtBuilder = JWT.create()
            .withSubject("Authentication")
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("id", user.id)
            .withClaim("login", user.login)
            .withClaim("role", user.role)
            .withClaim("singleModeStatisticsId", user.singleModeStatistics.id)
            .withClaim("walletId", user.wallet.id)
        return CredentialsResponse(
            accessToken = createAccessToken(jwtBuilder),
            refreshToken = createRefreshToken(jwtBuilder),
            expiresInMs = VALIDITY_IN_MS
        )
    }

    override fun verifyToken(token: String): Int? {
        return try {
            if (verifier.verify(token).claims["ati"] == null) {
                throw ApplicationException.Generic("it isn't refresh token")
            }
            verifier.verify(token).claims["id"]?.asInt()
        } catch (e: Exception) {
            println("catch refresh exception ${e.message}")
            throw ApplicationException.Generic("refresh token")
        }
    }

    private fun createAccessToken(jwtBuilder: JWTCreator.Builder): String {
        return jwtBuilder
            .withExpiresAt(getTokenExpiration())
            .sign(algorithm)
    }

    private fun createRefreshToken(jwtBuilder: JWTCreator.Builder): String {
        return jwtBuilder
            .withExpiresAt(getTokenExpiration(REFRESH_VALIDITY_IN_MS))
            .withClaim("ati", UUID.randomUUID().toString())
            .sign(algorithm)
    }

    private fun getTokenExpiration(validity: Long = VALIDITY_IN_MS): Date {
        return Date(System.currentTimeMillis() + validity)
    }
}

private const val VALIDITY_IN_MS = 1000L * 60L * 5L // 5 min
private const val REFRESH_VALIDITY_IN_MS = 3_600_000L * 24L * 60L //60 days

interface TokenProvider {
    fun createTokens(user: User): CredentialsResponse
    fun verifyToken(token: String): Int?
}