package com.example

import com.example.models.Booking
import com.example.models.Car
import com.example.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

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
