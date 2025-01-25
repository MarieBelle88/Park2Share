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
            Car.insert {
                it[uid] = 1
                it[brand] = "Mercedes"
                it[model] = "E-Class"
                it[color] = "Silver"
                it[plate] = "B-ME5678"
                it[capacity] = 5
                it[location] = "Charlottenburg"
                it[price] = 70.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Tesla"
                it[model] = "Model 3"
                it[color] = "Red"
                it[plate] = "B-TL4592"
                it[capacity] = 5
                it[location] = "Mitte"
                it[price] = 100.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Audi"
                it[model] = "Q5"
                it[color] = "White"
                it[plate] = "B-AQ9841"
                it[capacity] = 5
                it[location] = "Prenzlauer Berg"
                it[price] = 80.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Volkswagen"
                it[model] = "Passat"
                it[color] = "Grey"
                it[plate] = "B-VW7412"
                it[capacity] = 5
                it[location] = "Spandau"
                it[price] = 65.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Porsche"
                it[model] = "Panamera"
                it[color] = "Blue"
                it[plate] = "B-PO6297"
                it[capacity] = 4
                it[location] = "Steglitz"
                it[price] = 120.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Toyota"
                it[model] = "Corolla"
                it[color] = "Silver"
                it[plate] = "B-TY1236"
                it[capacity] = 5
                it[location] = "Friedrichshain"
                it[price] = 55.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Jaguar"
                it[model] = "XF"
                it[color] = "Green"
                it[plate] = "B-JG9812"
                it[capacity] = 4
                it[location] = "Kreuzberg"
                it[price] = 90.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Ford"
                it[model] = "Focus"
                it[color] = "Blue"
                it[plate] = "B-FO2390"
                it[capacity] = 5
                it[location] = "Tempelhof"
                it[price] = 50.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Opel"
                it[model] = "Astra"
                it[color] = "Black"
                it[plate] = "B-OP5623"
                it[capacity] = 5
                it[location] = "Marzahn"
                it[price] = 40.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Peugeot"
                it[model] = "3008"
                it[color] = "Gold"
                it[plate] = "B-PE7834"
                it[capacity] = 5
                it[location] = "Reinickendorf"
                it[price] = 60.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Hyundai"
                it[model] = "Tucson"
                it[color] = "Blue"
                it[plate] = "B-HY3245"
                it[capacity] = 5
                it[location] = "Lichtenberg"
                it[price] = 70.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Skoda"
                it[model] = "Octavia"
                it[color] = "White"
                it[plate] = "B-SK8492"
                it[capacity] = 5
                it[location] = "Treptow"
                it[price] = 55.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Volvo"
                it[model] = "XC60"
                it[color] = "Black"
                it[plate] = "B-VO2984"
                it[capacity] = 5
                it[location] = "Potsdam"
                it[price] = 85.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Chevrolet"
                it[model] = "Malibu"
                it[color] = "Silver"
                it[plate] = "B-CH5629"
                it[capacity] = 5
                it[location] = "Wilhelmstadt"
                it[price] = 60.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Fiat"
                it[model] = "500"
                it[color] = "Red"
                it[plate] = "B-FI4390"
                it[capacity] = 4
                it[location] = "Kopenick"
                it[price] = 45.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Mazda"
                it[model] = "CX-5"
                it[color] = "Blue"
                it[plate] = "B-MZ6721"
                it[capacity] = 5
                it[location] = "Charlottenburg"
                it[price] = 75.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Kia"
                it[model] = "Sportage"
                it[color] = "Orange"
                it[plate] = "B-KI8732"
                it[capacity] = 5
                it[location] = "Mitte"
                it[price] = 65.0f
                it[isAvailable] = true
            }
            Car.insert {
                it[uid] = 1
                it[brand] = "Renault"
                it[model] = "Megane"
                it[color] = "Yellow"
                it[plate] = "B-RE4732"
                it[capacity] = 5
                it[location] = "Zehlendorf"
                it[price] = 50.0f
                it[isAvailable] = true
            }
        }

        // BOOKING SAMPLE DATA
        if (Booking.selectAll().empty()) {
            Booking.insert {
                it[cid] = 1
                it[uid] = 2
                it[start] = "2025-01-20 10:00:00"
                it[end] = "2025-01-20 18:00:00"
                it[total] = 100.0f
                it[status] = "Confirmed"
            }
            Booking.insert {
                it[cid] = 2
                it[uid] = 1
                it[start] = "2025-01-21 08:00:00"
                it[end] = "2025-01-21 12:00:00"
                it[total] = 60.0f
                it[status] = "Pending"
            }
            for (i in 3..20) {
                Booking.insert {
                    it[cid] = i
                    it[uid] = 2
                    it[start] = "2025-01-${(i % 31) + 1} 09:00:00"
                    it[end] = "2025-01-${(i % 31) + 1} 17:00:00"
                    it[total] = (50 + (i * 5) % 100).toFloat()
                    it[status] = if (i % 3 == 0) "Cancelled" else if (i % 5 == 0) "Pending" else "Confirmed"
                }
            }
        }
    }
}
