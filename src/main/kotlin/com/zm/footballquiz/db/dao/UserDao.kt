package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.User
import org.jetbrains.exposed.sql.*

object Users : Table(), UserDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val login = varchar("login", 30)
    val password = varchar("password", 100)
    val role = varchar("role", 20)
    val email = varchar("email", 30)
    val nickname = varchar("nickname", 30)
    val country = varchar("country", 2)
    private val dateCreated = long("dateCreated")
    private val dateUpdated = long("dateUpdated")

    override fun insertUser(createUserBody: CreateUserBody): Int? {
        return insert {
            it[login] = createUserBody.login
            it[email] = createUserBody.email
            it[password] = createUserBody.password
            it[role] = createUserBody.role ?: User.Role.User.value
            it[nickname] = "nickname"
            it[country] = createUserBody.country
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun getUserById(userId: Int): User? {
        return select {
            id eq userId
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun getUserByLogin(login: String): User? {
        return select {
            (Users.login eq login) or (email eq login)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun getUserByLoginOrEmail(login: String, email: String): User? {
        return select {
            (Users.login eq login) or (Users.email eq email)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }
}

fun ResultRow.mapRowToUser() = User(
    id = this[Users.id],
    login = this[Users.login],
    email = this[Users.email],
    password = this[Users.password],
    role = this[Users.role],
    nickname = this[Users.nickname],
    country = this[Users.country],
)

interface UserDao {
    fun insertUser(createUserBody: CreateUserBody): Int?
    fun getUserById(userId: Int): User?
    fun getUserByLogin(login: String): User?
    fun getUserByLoginOrEmail(login: String, email: String): User?
}