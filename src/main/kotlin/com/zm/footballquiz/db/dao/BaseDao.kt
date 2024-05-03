package com.zm.footballquiz.db.dao

import org.jetbrains.exposed.sql.Table

abstract class BaseDao : Table() {
    protected val dateCreated = long("dateCreated")
    protected val dateUpdated = long("dateUpdated")
}
