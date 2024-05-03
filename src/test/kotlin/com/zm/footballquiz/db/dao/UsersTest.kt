package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.dto.CreateUserBody
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.junit.Test
import org.junit.jupiter.api.TestInstance


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
                country = "",
                role = null
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