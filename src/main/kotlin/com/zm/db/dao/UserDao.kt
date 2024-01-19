package com.zm.db.dao

import com.zm.model.CreateUserBody
import com.zm.model.User
import org.jetbrains.exposed.sql.*

object Users : Table(), UserDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val login = varchar("login", 30)
    val password = varchar("password", 30)
    val email = varchar("email", 30)
    val nickname = varchar("nickname", 30)
    val role = varchar("role", 20)
    val country = varchar("country", 2)
    val dateCreated = text("dateCreated")
    val dateUpdated = text("dateUpdated")

    override fun insertUser(createUserBody: CreateUserBody): Int? {
        TODO("Not yet implemented")
    }

    override fun getUserById(userId: Int): User? {
        TODO("Not yet implemented")
    }

    override fun getUserByLogin(login: String): User? {
        return select {
            (Users.login eq login)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }
}

fun ResultRow.mapRowToUser() = User(
    id = this[Users.id],
    login = this[Users.login],
    password = "pass",
    role = "test"
)

interface UserDao {
    fun insertUser(createUserBody: CreateUserBody): Int?
    fun getUserById(userId: Int): User?
    fun getUserByLogin(login: String): User?
}