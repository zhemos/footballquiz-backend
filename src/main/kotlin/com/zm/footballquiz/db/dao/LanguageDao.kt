package com.zm.footballquiz.db.dao

object TableLanguages : BaseDao("languages"), LanguageDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val name = varchar("name", 20)
    val code = varchar("code", 2)
}

interface LanguageDao