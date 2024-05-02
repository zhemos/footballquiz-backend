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
    val dateOfBirth = varchar("dateOfBirth", 30)
    val country = varchar("country", 2)
    val dateCreated = varchar("dateCreated", 30)
    val dateUpdated = varchar("dateUpdated", 30)

    override fun insertUser(createUserBody: CreateUserBody): Int? {
        val a = insert {
            it[login] = "root1"
            it[password] = "root1"
            it[email] = "email"
            it[nickname] = "nickname"
            it[role] = "role"
            it[dateOfBirth] = "dateOfBirth"
            it[country] = "aa"
            it[dateCreated] = "datecreated"
            it[dateUpdated] = "dateupdated"
        }
        println("!!!")
        val user = getUserById(a[id])
        println(user)
        println(a[id])
        println(a[login])
        println(a[role])
        println(a[country])
        return null
    }

    override fun getUserById(userId: Int): User? {
        return select {
            (id eq userId)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun getUserByLogin(login: String): User? {
        return select {
            (Users.login eq login)
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
    password = this[Users.password],
    role = this[Users.role]
)

interface UserDao {
    fun insertUser(createUserBody: CreateUserBody): Int?
    fun getUserById(userId: Int): User?
    fun getUserByLogin(login: String): User?
    fun getUserByLoginOrEmail(login: String, email: String): User?
}