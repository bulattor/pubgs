package ru.tinkoff.fintech.pubgs.configuration

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.pubgs.PlayerInfo
import ru.tinkoff.fintech.pubgs.PlayerRepository
import java.time.Duration

@Configuration
class ServiceConfiguration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(30L))
        .setReadTimeout(Duration.ofSeconds(60L))
        .build()

    @Bean
    fun apiSettings(): HttpEntity<String>
    {
        val headers = HttpHeaders()
        headers.set("Accept", "application/vnd.api+json")
        headers.set(
            "Authorization",
            "Bearer $API_KEY")
        return HttpEntity<String>(headers)
    }

    @Bean
    fun databaseInitializer(playerRepository: PlayerRepository) = ApplicationRunner {
        val bulattor = playerRepository.save(PlayerInfo("account.107a252b59da453f98e4418627a7ac85","Sleepy one"))
    }
}

private const val API_KEY = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxOGJmN2ExMC1iNDM2LTAxM2EtN2RiMS0xNzRhYzM1YTM1ZGYiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNjUyMzY5MzA4LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Ii01OTA0YzMxYi03ZTZiLTQ4Y2UtODBjNS1hNzQ5MTM3ZWM2NzAifQ.R6ku0FQpY86hnFqc96gbgbpvFDsb8t5PJZiDL4PTofM"