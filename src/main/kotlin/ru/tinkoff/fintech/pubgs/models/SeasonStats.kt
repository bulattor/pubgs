package ru.tinkoff.fintech.pubgs.models

class SeasonStats(
    val bestRankPoint: Double,
    val duo: MutableMap<String, Number>,
    val duofpp: MutableMap<String, Number>,
    val solo: MutableMap<String, Number>,
    val solofpp: MutableMap<String, Number>,
    val squad: MutableMap<String, Number>,
    val squadfpp: MutableMap<String, Number>,
)