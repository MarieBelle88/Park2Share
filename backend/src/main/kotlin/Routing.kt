package com.example

import com.example.models.User
import com.example.models.Car
import com.example.models.Booking

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {
        // User Routes
        route("/User") {
            get {
                val User = transaction {
                    User.selectAll().map { row ->
                        mapOf(
                            "uid" to row[User.uid],
                            "first_name" to row[User.firstName],
                            "last_name" to row[User.lastName],
                            "email" to row[User.email],
                            "phone" to row[User.phone]
                        )
                    }
                }
                call.respond(User)
            }

            post {
                val user = call.receive<Map<String, String>>()
                transaction {
                    User.insert {
                        it[firstName] = user["first_name"]!!
                        it[lastName] = user["last_name"]!!
                        it[email] = user["email"]!!
                        it[password] = user["password"]!!
                        it[phone] = user["phone"]!!
                    }
                }
                call.respond("User added successfully")
            }
        }

        // Car Routes
        route("/Car") {
            get {
                val Car = transaction {
                    Car.selectAll().map { row ->
                        mapOf(
                            "cid" to row[Car.cid],
                            "uid" to row[Car.uid],
                            "brand" to row[Car.brand],
                            "model" to row[Car.model],
                            "color" to row[Car.color],
                            "plate" to row[Car.plate],
                            "location" to row[Car.location],
                            "price" to row[Car.price],
                            "isAvailable" to row[Car.isAvailable]
                        )
                    }
                }
                call.respond(Car)
            }

            post {
                val car = call.receive<Map<String, String>>()
                transaction {
                    Car.insert {
                        it[uid] = car["uid"]!!.toInt()
                        it[brand] = car["brand"]!!
                        it[model] = car["model"]!!
                        it[color] = car["color"]!!
                        it[plate] = car["plate"]!!
                        it[location] = car["location"]!!
                        it[price] = car["total"]!!.toFloat()
                        it[isAvailable] = car["isAvailable"]!!.toBoolean()
                    }
                }
                call.respond("Car added successfully")
            }
        }

        // Booking Routes
        route("/Booking") {
            get {
                val bookings  = transaction {
                    Booking.selectAll().map { row ->
                        mapOf(
                            "bid" to row[Booking.bid],
                            "cid" to row[Booking.cid],
                            "uid" to row[Booking.uid],
                            "start" to row[Booking.start],
                            "end" to row[Booking.end],
                            "total" to row[Booking.total],
                            "status" to row[Booking.status]
                        )
                    }
                }
                call.respond(bookings )
            }

            post {
                val bookings = call.receive<Map<String, String>>()
                transaction {
                    Booking.insert {
                        it[cid] = bookings["cid"]!!.toInt()
                        it[uid] = bookings["uid"]!!.toInt()
                        it[start] = bookings["start"]!!
                        it[end] = bookings["end"]!!
                        it[total] = bookings["total"]!!.toFloat()
                        it[status] = bookings["status"]!!
                    }
                }
                call.respond("Booking added successfully")
            }
        }
    }
}

fun initDatabase() {
    val dbUrl = "jdbc:mysql://localhost:3306"
    val dbUser = "root"
    val dbPassword = "secret"

    Database.connect(
        url = dbUrl,
        driver = "com.mysql.cj.jdbc.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(User, Car, Booking)
    }
}
