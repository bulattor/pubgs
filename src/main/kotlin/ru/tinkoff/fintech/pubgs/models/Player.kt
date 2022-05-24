package ru.tinkoff.fintech.pubgs.models

class Player(
    val name: String = "",
    val id: String = "",
    val platform: String = "",
    var seasonStats: SeasonStats?,
)