package com.zm.db.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object Testing : Table(), TestDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val count = integer("count")
    val str = varchar("str", 45)
    val time = text("time")

    override fun insert(count: Int, str: String): Int? {
        return insert {
            it[Testing.count] = count
            it[Testing.str] = str
            it[Testing.time] = "2000-01-01 00:00:00"
        }.getOrNull(id)
    }

    override fun getCountById(id: Int): Int {
        return select {
            (Testing.id eq id)
        }.map {
            it[count]
        }.singleOrNull() ?: 0
    }

    override fun getStringById(id: Int): String {
        return select {
            (Testing.id eq id)
        }.map {
            it[str]
        }.singleOrNull() ?: ""
    }
}

interface TestDao {
    fun insert(count: Int, str: String): Int?
    fun getCountById(id: Int): Int
    fun getStringById(id: Int): String
}