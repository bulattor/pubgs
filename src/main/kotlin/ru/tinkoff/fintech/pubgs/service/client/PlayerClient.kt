package ru.tinkoff.fintech.pubgs.service.client

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.tinkoff.fintech.pubgs.models.SeasonStats
import ru.tinkoff.fintech.pubgs.models.Player


@Service
class PlayerClient(
    private val restTemplate: RestTemplate,
    private val apiSettings: HttpEntity<String>
) {

    fun getPlayersByName(platform: String, names: String): MutableList<Player> {

        val response = restTemplate.exchange<String>(
            "https://api.pubg.com/shards/$platform/players?filter[playerNames]=$names",
            HttpMethod.GET,
            apiSettings
        )

        val parser: Parser = Parser.default()
        val stringBuilder: StringBuilder = StringBuilder(response.body.toString())
        val playersJsonObjects: JsonArray<JsonObject> = ((parser.parse(stringBuilder) as JsonObject)["data"] as JsonArray<JsonObject>)
        var players = mutableListOf<Player>()
        playersJsonObjects.forEach {
            var p = Player(
                (it["attributes"] as JsonObject)["name"].toString(),
                it["id"].toString(),
                (it["attributes"] as JsonObject)["shardId"].toString(),
                null
            )
            players.add(p)
        }

        return players
    }

    fun getSeasonForPlayer(seasonId: String, player: Player)
    {
        val response = restTemplate.exchange<String>(
            "https://api.pubg.com/shards/${player.platform}/players/${player.id}/seasons/$seasonId",
            HttpMethod.GET,
            apiSettings
        )
        val parser: Parser = Parser.default()
        val stringBuilder: StringBuilder = StringBuilder(response.body.toString())
        val gameModeStats: JsonObject = ((((
                parser.parse(stringBuilder) as JsonObject)
                ["data"] as JsonObject)
                ["attributes"] as JsonObject)
                ["gameModeStats"] as JsonObject)

        player.seasonStats = SeasonStats(
            0.0,
            (gameModeStats["duo"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
            (gameModeStats["duo-fpp"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
            (gameModeStats["solo"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
            (gameModeStats["solo-fpp"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
            (gameModeStats["squad"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
            (gameModeStats["squad-fpp"] as JsonObject).toMutableMap() as MutableMap<String, Number>,
        )
    }
}

