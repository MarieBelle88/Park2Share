package com.example

import com.example.models.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import io.ktor.http.HttpStatusCode

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
                try {
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
                    // Return a JSON response
                    call.respond(mapOf("message" to "User added successfully"))
                } catch (e: Exception) {
                    // Handle errors and return a JSON response
                    call.respond(mapOf("error" to "An error occurred: ${e.message}"))
                }
            }

            post("/login") {
                val credentials = call.receive<Map<String, String>>()
                val email = credentials["email"]!!
                val password = credentials["password"]!!

                val user = transaction {
                    User.select { User.email eq email }
                        .firstOrNull()
                        ?.let { row ->
                            Users(
                                uid = row[User.uid],
                                firstName = row[User.firstName],
                                lastName = row[User.lastName],
                                email = row[User.email],
                                password = row[User.password],
                                phone = row[User.phone]
                            )
                        }
                }

                if (user != null && user.password == password) {
                    call.respond(user)
                } else {
                    call.respond(mapOf("error" to "Invalid email or password"))
                }
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
                try {
                    val car = call.receive<Map<String, String>>()

                    // Validate required fields
                    if (car["uid"].isNullOrEmpty() || car["brand"].isNullOrEmpty() || car["model"].isNullOrEmpty() ||
                        car["color"].isNullOrEmpty() || car["plate"].isNullOrEmpty() || car["capacity"].isNullOrEmpty() ||
                        car["location"].isNullOrEmpty() || car["price"].isNullOrEmpty() || car["isAvailable"].isNullOrEmpty()) {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Missing required fields"))
                        return@post
                    }

                    // Insert the car into the database
                    transaction {
                        Car.insert {
                            it[uid] = car["uid"]!!.toInt()
                            it[brand] = car["brand"]!!
                            it[model] = car["model"]!!
                            it[color] = car["color"]!!
                            it[plate] = car["plate"]!!
                            it[capacity] = car["capacity"]!!.toInt()
                            it[location] = car["location"]!!
                            it[price] = car["price"]!!.toFloat()
                            it[isAvailable] = car["isAvailable"]!!.toBoolean()
                        }
                    }

                    // Return a success response
                    call.respond(mapOf("message" to "Car added successfully"))
                } catch (e: Exception) {
                    // Handle errors
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An error occurred: ${e.message}"))
                }
            }

            put("/{cid}") {
                try {
                    val cid = call.parameters["cid"]?.toIntOrNull()
                    if (cid == null) {
                        call.respond(HttpStatusCode.BadRequest, "Invalid car ID")
                        return@put
                    }

                    val updatedData = call.receive<Map<String, String>>()

                    // Validate required fields
                    val requiredFields = listOf("uid", "brand", "model", "color", "plate", "capacity", "location", "price", "isAvailable")
                    val missingFields = requiredFields.filter { it !in updatedData }
                    if (missingFields.isNotEmpty()) {
                        call.respond(HttpStatusCode.BadRequest, "Missing fields: ${missingFields.joinToString(", ")}")
                        return@put
                    }

                    // Update the car in the database
                    transaction {
                        Car.update({ Car.cid eq cid }) {
                            it[uid] = updatedData["uid"]!!.toInt()
                            it[brand] = updatedData["brand"]!!
                            it[model] = updatedData["model"]!!
                            it[color] = updatedData["color"]!!
                            it[plate] = updatedData["plate"]!!
                            it[capacity] = updatedData["capacity"]!!.toInt()
                            it[location] = updatedData["location"]!!
                            it[price] = updatedData["price"]!!.toFloat()
                            it[isAvailable] = updatedData["isAvailable"]!!.toBoolean()
                        }
                    }

                    call.respond("Car updated successfully")
                } catch (e: Exception) {
                    // Log the error for debugging
                    println("Error updating car: ${e.message}")
                    call.respond(HttpStatusCode.InternalServerError, "Failed to update car: ${e.message}")
                }
            }

            get("/{cid}") {
                val cid = call.parameters["cid"]?.toIntOrNull()

                if (cid == null) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Invalid Car ID"))
                    return@get
                }

                val car = transaction {
                    Car.select { Car.cid eq cid }
                        .map { row ->
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
                        .firstOrNull()
                }

                if (car != null) {
                    call.respond(car)
                } else {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to "Car not found"))
                }
            }

            get("/user/{uid}") {
                val uid = call.parameters["uid"]?.toIntOrNull()
                if (uid == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid User ID")
                    return@get
                }

                val userListings = transaction {
                    Car.select { Car.uid eq uid }.map { row ->
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

                if (userListings.isNotEmpty()) {
                    call.respond(userListings)
                } else {
                    call.respond(HttpStatusCode.NotFound, "No listings found for user $uid")
                }
            }



            delete("/{cid}") {
                val cid = call.parameters["cid"]!!.toInt()
                transaction {
                    Booking.deleteWhere { Booking.cid eq cid }
                    Car.deleteWhere { Car.cid eq cid }
                }
                call.respond(HttpStatusCode.OK, mapOf("message" to "Car deleted successfully"))
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

