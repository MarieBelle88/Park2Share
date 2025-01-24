package com.example

import com.example.models.*

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respond(mapOf("message" to "Welcome to the Park2Share API"))
        }

        route("/user") {
            get {
                log.info("Handling GET request at /user")
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
        }

        route("/car") {
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
        }

        route("/booking") {
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
        }
    }
}

fun initDatabase() {
    val dbHost = "localhost"
    val dbPort = "3306"
    val dbName = "park2share"
    val dbUser = "root"
    val dbPassword = "secret"
    val dbUrl = "jdbc:mysql://$dbHost:$dbPort/"

    Database.connect(
        url = dbUrl,
        driver = "com.mysql.cj.jdbc.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        // CREATE DB
        exec("CREATE DATABASE IF NOT EXISTS $dbName")
    }

    Database.connect(
        url = "$dbUrl$dbName",
        driver = "com.mysql.cj.jdbc.Driver",
        user = dbUser,
        password = dbPassword
    )

    transaction {
        SchemaUtils.create(User, Car, Booking)

        // USER SAMPLE DATA
        if (User.selectAll().empty()) {
            User.insert {
                it[firstName] = "Martin"
                it[lastName] = "Mirchevski"
                it[email] = "m.mirchevski@student.xu-university.de"
                it[password] = "whatcouldthisbe123"
                it[phone] = "+359878884010"
            }
            User.insert {
                it[firstName] = "Marie-Belle"
                it[lastName] = "Khaddage"
                it[email] = "m.khaddage@@student.xu-university.de"
                it[password] = "password567"
                it[phone] = "+49123456789"
            }
        }

        // CAR SAMPLE DATA
        if (Car.selectAll().empty()) {
            Car.insert {
                it[uid] = 1
                it[brand] = "BMW"
                it[model] = "X3"
                it[color] = "Dark Blue"
                it[plate] = "CB8805TK"
                it[capacity] = 4
                it[location] = "Babelsberg"
                it[price] = 50.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 2
                it[brand] = "Honda"
                it[model] = "Civic"
                it[color] = "Black"
                it[plate] = "XYZ789"
                it[capacity] = 4
                it[location] = "Neukoln"
                it[price] = 60.0f
                it[isAvailable] = true
            }
        }

        // BOOKING SAMPLE DATA
        if (Booking.selectAll().empty()) {
            Booking.insert {
                it[cid] = 1
                it[uid] = 1
                it[start] = "2025-01-20 10:00:00"
                it[end] = "2025-01-20 18:00:00"
                it[total] = 100.0f
                it[status] = "Confirmed"
            }
            Booking.insert {
                it[cid] = 2
                it[uid] = 2
                it[start] = "2025-01-21 08:00:00"
                it[end] = "2025-01-21 12:00:00"
                it[total] = 60.0f
                it[status] = "Pending"
            }
        }
    }
}
