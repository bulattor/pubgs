package ru.tinkoff.fintech.pubgs

import org.springframework.data.repository.CrudRepository

interface PlayerRepository : CrudRepository<PlayerInfo, String> {
    //fun findById(login: String): PlayerInfo?
}