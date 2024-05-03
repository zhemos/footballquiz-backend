package com.zm.footballquiz.db.dao

import com.zm.db.dao.Users.autoIncrement
import com.zm.model.CreateUserBody
import org.junit.Assert.*
import org.junit.Test
import org.junit.jupiter.api.TestInstance
import kotlin.math.log
import org.jetbrains.exposed.sql.*



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UsersTest : BaseDaoTest() {

    @Test
    fun test() {
        setup()
        withTables(TestTable) {
            val createUserBody = CreateUserBody(
                login = "",
                email = "",
                password = "",
                dateOfBirth = "",
                country = ""
            )
            val user = TestTable.insert("111")
            println(user)
        }
    }
}

object TestTable : Table("roles") {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 45)

    fun insert(name: String) {
        insert {
            it[TestTable.name] = name
        }
    }
}