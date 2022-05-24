package ru.tinkoff.fintech.pubgs

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import ru.tinkoff.fintech.pubgs.service.client.PlayerClient

@Controller
class HtmlController(
    private val playerClient: PlayerClient
) {
    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "PUBGs"
        return "index"
    }

    @GetMapping("/stats/{platform}")
    fun getPlayerByName(
        model: Model,
        @PathVariable platform: String,
        @RequestParam seasonId: String,
        @RequestParam playerNames: String,
        @RequestParam sortIn: String?,
        @RequestParam sortBy: String?
    ): String {

        var players = playerClient.getPlayersByName(platform, playerNames)
        players.forEach {
            playerClient.getSeasonForPlayer(seasonId, it)
        }

        if (sortBy != null) {
            model["selected"] = sortBy
            when(sortIn){
                "solo" -> players.sortByDescending { it.seasonStats?.solo?.get(sortBy)?.toDouble() }
                "solofpp" -> players.sortByDescending { it.seasonStats?.solofpp?.get(sortBy)?.toDouble() }
                "duo" -> players.sortByDescending { it.seasonStats?.duo?.get(sortBy)?.toDouble() }
                "duofpp" -> players.sortByDescending { it.seasonStats?.duofpp?.get(sortBy)?.toDouble() }
                "squad" -> players.sortByDescending { it.seasonStats?.squad?.get(sortBy)?.toDouble() }
                "squadfpp" -> players.sortByDescending { it.seasonStats?.squadfpp?.get(sortBy)?.toDouble() }
                else -> players.sortByDescending {
                    0 +
                            it.seasonStats?.solo?.get(sortBy)?.toDouble()!! +
                            it.seasonStats?.solofpp?.get(sortBy)?.toDouble()!! +
                            it.seasonStats?.duo?.get(sortBy)?.toDouble()!! +
                            it.seasonStats?.duofpp?.get(sortBy)?.toDouble()!! +
                            it.seasonStats?.squad?.get(sortBy)?.toDouble()!! +
                            it.seasonStats?.squadfpp?.get(sortBy)?.toDouble()!!
                }
            }
        }
        else model["selected"] = "ooo"

        model["title"] = "PUBG Stats ($platform)"

        model["players"] = players

        return "player"
    }
}