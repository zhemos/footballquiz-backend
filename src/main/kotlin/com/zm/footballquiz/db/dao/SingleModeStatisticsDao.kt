package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.dto.SingleModeStatisticsBody
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableSingleModeStatistics : BaseDao("singleModeStatistics"), SingleModeStatisticsDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val countOfPoints = integer("countOfPoints")

    override fun insertStatistics(): Int? {
        return insert {
            it[countOfPoints] = 0
            it[dateCreated] = System.currentTimeMillis()
            it[dateUpdated] = System.currentTimeMillis()
        }.getOrNull(id)
    }

    override fun getStatisticsById(id: Int): SingleModeStatistics? {
        return select {
            TableSingleModeStatistics.id eq id
        }.map {
            it.mapRowToSingleModeStatistics()
        }.singleOrNull()
    }

    override fun getStatistics(): List<SingleModeStatistics> {
        return selectAll().map {
            it.mapRowToSingleModeStatistics()
        }
    }

    override fun updateStatistics(
        id: Int,
        singleModeStatisticsBody: SingleModeStatisticsBody
    ): SingleModeStatistics? {
        update({ TableSingleModeStatistics.id eq id }) { statement ->
            statement[countOfPoints] = singleModeStatisticsBody.countOfPoints
        }
        return getStatisticsById(id)
    }

    override fun deleteStatisticsById(id: Int) {
        deleteWhere { TableSingleModeStatistics.id eq id }
    }
}

fun ResultRow.mapRowToSingleModeStatistics() = SingleModeStatistics(
    id = this[TableSingleModeStatistics.id],
    countOfPoints = this[TableSingleModeStatistics.countOfPoints],
)

interface SingleModeStatisticsDao {
    fun insertStatistics(): Int?
    fun getStatisticsById(id: Int): SingleModeStatistics?
    fun getStatistics(): List<SingleModeStatistics>
    fun updateStatistics(id: Int, singleModeStatisticsBody: SingleModeStatisticsBody): SingleModeStatistics?
    fun deleteStatisticsById(id: Int)
}