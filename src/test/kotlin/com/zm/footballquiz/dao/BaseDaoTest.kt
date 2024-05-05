package com.zm.footballquiz.dao

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Before
import org.junit.jupiter.api.BeforeEach

abstract class BaseDaoTest {

    @Before
    open fun setup() {
        val url = "jdbc:mysql://localhost:3306/footballquiztest"
        Database.connect(url, driver = "com.mysql.jdbc.Driver", user = "root", password = "root")
    }

    fun withTables(vararg tables: Table, test: Transaction.() -> Unit) {
        transaction {
            SchemaUtils.create(*tables)
            try {
                test()
                commit()
            } finally {
                SchemaUtils.drop(*tables)
                commit()
            }
        }
    }
}