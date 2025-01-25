package com.example.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.ForeignKeyConstraint
import org.jetbrains.exposed.sql.ReferenceOption


object User : Table() {
    val uid = integer("uid").autoIncrement()
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val phone = varchar("phone", 15)

    override val primaryKey = PrimaryKey(uid)
}

object Car : Table() {
    val cid = integer("cid").autoIncrement()
    val uid = integer("uid").references(User.uid, onDelete = ReferenceOption.CASCADE)
    val brand = varchar("brand", 20)
    val model = varchar("model", 50)
    val color = varchar("color", 10)
    val plate = varchar("plate", 10)
    val capacity = integer("capacity")
    val location = varchar("location", 50)
    val price = float("price")
    val isAvailable = bool("isAvailable")


    override val primaryKey = PrimaryKey(cid)
}

object Booking : Table() {
    val bid = integer("bid").autoIncrement()
    val cid = integer("cid").references(Car.cid, onDelete = ReferenceOption.CASCADE)
    val uid = integer("uid").references(User.uid, onDelete = ReferenceOption.CASCADE)
    val start = varchar("start", 50)
    var end = varchar("end", 50)
    val total = float("total")
    val status = varchar("status", 20)

    override val primaryKey = PrimaryKey(bid)
}
