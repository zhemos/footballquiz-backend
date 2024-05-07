package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.dto.CreateUserBody
import com.zm.footballquiz.model.User
import com.zm.footballquiz.model.dto.UserUpdateBody
import com.zm.footballquiz.statuspages.ApplicationException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableUsers : BaseDao("users"), UserDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val login = varchar("login", 30)
    val password = varchar("password", 100)
    val role = varchar("role", 20)
    val email = varchar("email", 30)
    val nickname = varchar("nickname", 30)
    val country = varchar("country", 2)
    val singleModeStatisticsId = reference(
        name = "singleModeStatisticsId",
        refColumn = TableSingleModeStatistics.id,
        onDelete = ReferenceOption.RESTRICT,
        onUpdate = ReferenceOption.CASCADE,
    )

    override fun insertUser(createUserBody: CreateUserBody, userRole: User.Role): Int? {
        val singleModeId = TableSingleModeStatistics.insertStatistics() ?: return null
        return insert {
            it[login] = createUserBody.login
            it[email] = createUserBody.email
            it[password] = createUserBody.password
            it[role] = userRole.value
            it[nickname] = "nickname"//todo user#
            it[country] = createUserBody.country
            it[singleModeStatisticsId] = singleModeId
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun getUserById(userId: Int): User? {
        return (TableUsers innerJoin TableSingleModeStatistics).select {
            id eq userId
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun getUserByLogin(login: String): User? {
        return (TableUsers innerJoin TableSingleModeStatistics).select {
            (TableUsers.login eq login) or (email eq login)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun getUserByLoginOrEmail(login: String, email: String): User? {
        return (TableUsers innerJoin TableSingleModeStatistics).select {
            (TableUsers.login eq login) or (TableUsers.email eq email)
        }.map {
            it.mapRowToUser()
        }.singleOrNull()
    }

    override fun deleteUserById(userId: Int): Int {
        val user = getUserById(userId) ?: return -1
        val id = deleteWhere { id eq userId }
        TableSingleModeStatistics.deleteStatisticsById(user.singleModeStatistics.id)
        return id
    }

    override fun getUsers(): List<User> {
        return (TableUsers innerJoin TableSingleModeStatistics)
            .selectAll().map {
                it.mapRowToUser()
            }
    }

    override fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): User? {
        update({ id eq userId }) { statement ->
            userUpdateBody.nickname?.let {
                statement[nickname] = it
            }
            userUpdateBody.country?.let {
                statement[country] = it
            }
        }
        return getUserById(userId)
    }
}

fun ResultRow.mapRowToUser(): User {
    val singleModeStatistics = SingleModeStatistics(
        id = this[TableUsers.singleModeStatisticsId],
        countOfPoints = this[TableSingleModeStatistics.countOfPoints],
    )
    return User(
        id = this[TableUsers.id],
        login = this[TableUsers.login],
        email = this[TableUsers.email],
        password = this[TableUsers.password],
        role = this[TableUsers.role],
        nickname = this[TableUsers.nickname],
        country = this[TableUsers.country],
        singleModeStatistics = singleModeStatistics
    )
}

interface UserDao {
    fun insertUser(createUserBody: CreateUserBody, userRole: User.Role): Int?
    fun getUserById(userId: Int): User?
    fun getUserByLogin(login: String): User?
    fun getUserByLoginOrEmail(login: String, email: String): User?
    fun deleteUserById(userId: Int): Int
    fun getUsers(): List<User>
    fun updateUser(userId: Int, userUpdateBody: UserUpdateBody): User?
}