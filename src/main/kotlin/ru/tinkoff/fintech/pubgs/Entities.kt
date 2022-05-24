package ru.tinkoff.fintech.pubgs

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class PlayerInfo(
    @Id var id: String,
    var info: String = "",
)
/*

@Entity
class SeasonStats(
    val bestRankPoint: Double,
    val duo: MutableMap<String, Number>,
    val duofpp: MutableMap<String, Number>,
    val solo: MutableMap<String, Number>,
    val solofpp: MutableMap<String, Number>,
    val squad: MutableMap<String, Number>,
    val squadfpp: MutableMap<String, Number>,
) {
    @Id @GeneratedValue
    var id: Long? = null
}*/
