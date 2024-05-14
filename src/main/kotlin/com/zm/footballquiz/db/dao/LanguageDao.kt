package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.Language
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

object TableLanguages : BaseDao("languages"), LanguageDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val code = varchar("code", 2)
    val name = varchar("name", 20)

    override fun insertLanguage(language: Language): Int? {
        return insert {
            it[code] = language.code
            it[name] = language.name
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun getLanguageByCode(code: String): Language? {
        return select {
            TableLanguages.code eq code
        }.map {
            it.mapRowToLanguage()
        }.singleOrNull()
    }

    override fun getCount(): Long = selectAll().count()
}

fun ResultRow.mapRowToLanguage() = Language(
    code = this[TableLanguages.code],
    name = this[TableLanguages.name],
)

interface LanguageDao {
    fun insertLanguage(language: Language): Int?
    fun getLanguageByCode(code: String): Language?
    fun getCount(): Long
}