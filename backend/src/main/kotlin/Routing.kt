package com.example

import com.example.models.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respond(mapOf("message" to "Welcome to the Park2Share API"))
        }

        route("/users") {
            get {
                val users = transaction {
                    User.selectAll().map { row ->
                        Users(
                            uid = row[User.uid],
                            firstName = row[User.firstName],
                            lastName = row[User.lastName],
                            email = row[User.email],
                            password = row[User.password],
                            phone = row[User.phone],
                        )
                    }
                }
                call.respond(users)
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

            put("/{uid}") {
                val uid = call.parameters["uid"]!!.toInt()
                val updatedData = call.receive<Map<String, String>>()
                transaction {
                    User.update({ User.uid eq uid }) {
                        it[firstName] = updatedData["first_name"]!!
                        it[lastName] = updatedData["last_name"]!!
                        it[email] = updatedData["email"]!!
                        it[password] = updatedData["password"]!!
                        it[phone] = updatedData["phone"]!!
                    }
                }
                call.respond("User updated successfully")
            }

            delete("/{uid}") {
                val uid = call.parameters["uid"]!!.toInt()
                transaction {
                    Booking.deleteWhere { Booking.uid eq uid }
                    Car.deleteWhere { Car.uid eq uid }
                    User.deleteWhere { User.uid eq uid }
                }

                call.respond("User deleted successfully")
            }
        }

        route("/cars") {
            get {
                val cars = transaction {
                    Car.selectAll().map { row ->
                        Cars(
                            cid = row[Car.cid],
                            uid = row[Car.uid],
                            brand = row[Car.brand],
                            model = row[Car.model],
                            color = row[Car.color],
                            plate = row[Car.plate],
                            capacity = row[Car.capacity],
                            location = row[Car.location],
                            price = row[Car.price],
                            isAvailable = row[Car.isAvailable]
                        )
                    }
                }
                call.respond(cars)
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
                        it[capacity] = car["capacity"]!!.toInt()
                        it[location] = car["location"]!!
                        it[price] = car["total"]!!.toFloat()
                        it[isAvailable] = car["isAvailable"]!!.toBoolean()
                    }
                }
                call.respond("Car added successfully")
            }

            put("/{cid}") {
                val cid = call.parameters["cid"]!!.toInt()
                val updatedData = call.receive<Map<String, String>>()
                transaction {
                    Car.update({ Car.cid eq cid }) {
                        it[uid] = updatedData["uid"]!!.toInt()
                        it[brand] = updatedData["brand"]!!
                        it[model] = updatedData["model"]!!
                        it[color] = updatedData["color"]!!
                        it[plate] = updatedData["plate"]!!
                        it[capacity] = updatedData["capacity"]!!.toInt()
                        it[location] = updatedData["location"]!!
                        it[price] = updatedData["total"]!!.toFloat()
                        it[isAvailable] = updatedData["isAvailable"]!!.toBoolean()
                    }
                }
                call.respond("Car updated successfully")
            }

            delete("/{cid}") {
                val cid = call.parameters["cid"]!!.toInt()
                transaction {
                    Booking.deleteWhere { Booking.cid eq cid }
                    Car.deleteWhere { Car.cid eq cid }
                }
                call.respond("Car deleted successfully")
            }
        }

        route("/bookings") {
            get {
                val bookings = transaction {
                    Booking.selectAll().map { row ->
                        Bookings(
                            bid = row[Booking.bid],
                            cid = row[Booking.cid],
                            uid = row[Booking.uid],
                            start = row[Booking.start],
                            end = row[Booking.end],
                            total = row[Booking.total],
                            status = row[Booking.status]
                        )
                    }
                }
                call.respond(bookings)
            }

            post {
                val booking = call.receive<Map<String, String>>()
                transaction {
                    Booking.insert {
                        it[cid] = booking["cid"]!!.toInt()
                        it[uid] = booking["uid"]!!.toInt()
                        it[start] = booking["start"]!!
                        it[end] = booking["end"]!!
                        it[total] = booking["total"]!!.toFloat()
                        it[status] = booking["status"]!!
                    }
                }
                call.respond("Booking added successfully")
            }

            put("/{bid}") {
                val bid = call.parameters["bid"]!!.toInt()
                val updatedData = call.receive<Map<String, String>>()
                transaction {
                    Booking.update({ Booking.bid eq bid }) {
                        it[cid] = updatedData["cid"]!!.toInt()
                        it[uid] = updatedData["uid"]!!.toInt()
                        it[start] = updatedData["start"]!!
                        it[end] = updatedData["end"]!!
                        it[total] = updatedData["total"]!!.toFloat()
                        it[status] = updatedData["status"]!!
                    }
                }
                call.respond("Booking updated successfully")
            }

            delete("/{bid}") {
                val bid = call.parameters["bid"]!!.toInt()
                transaction {
                    Booking.deleteWhere { Booking.bid eq bid }
                }
                call.respond("Booking deleted successfully")
            }
        }
    }
}

