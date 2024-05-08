package com.zm.footballquiz.db.dao

import com.zm.footballquiz.model.SingleModeStatistics
import com.zm.footballquiz.model.UpdateSingleModeStatistics
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

object TableSingleModeStatistics : BaseDao("singleModeStatistics"), SingleModeStatisticsDao {
    val id = integer("id").autoIncrement()
    override val primaryKey = PrimaryKey(id)
    val countOfPoints = integer("points")

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
        updateSingleModeStatistics: UpdateSingleModeStatistics,
    ): SingleModeStatistics? {
        update({ id eq updateSingleModeStatistics.id }) { statement ->
            statement[countOfPoints] = updateSingleModeStatistics.countOfPoints
        }
        return getStatisticsById(updateSingleModeStatistics.id)
    }

    override fun deleteStatisticsById(id: Int): Int {
        return deleteWhere { TableSingleModeStatistics.id eq id }
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
    fun updateStatistics(updateSingleModeStatistics: UpdateSingleModeStatistics): SingleModeStatistics?
    fun deleteStatisticsById(id: Int): Int
}