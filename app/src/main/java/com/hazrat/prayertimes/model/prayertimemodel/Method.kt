package com.hazrat.prayertimes.model.prayertimemodel

data class Method(
    val id: Int,
    val location: Location,
    val name: String,
    val params: Params
)