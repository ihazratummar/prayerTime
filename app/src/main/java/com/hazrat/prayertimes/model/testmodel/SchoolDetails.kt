package com.hazrat.prayertimes.model.testmodel

data class SchoolDetails(
    val number: Int,
    val name: String
)

val schoolDetailsList = listOf(
    SchoolDetails(
        number = 0,
        name = "Shafi'i, Maliki & Hanbali"
    ),
    SchoolDetails(
        number = 1,
        name = "Hanafi"
    )
)
