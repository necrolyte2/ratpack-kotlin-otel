package http

import com.google.inject.Inject
import exampleapp.service.DataRepository
import exampleapp.service.RefreshService
import ratpack.jackson.Jackson.json
import ratpack.kotlin.handling.KChain
import ratpack.kotlin.handling.KChainAction

class V1Chain @Inject constructor(
    private val refreshService: RefreshService,
    private val dataRepository: DataRepository
): KChainAction() {
    override fun execute() {
        get("hi") {
            render("hey there")
        }
        path("onlyhttp/:id") {
            val id = allPathTokens.getOrDefault("id", "1")
            refreshService.refresh(id.toInt()).then {
                render(json(it))
            }
        }
        path("cassandrahttp/:id") {
            val id = allPathTokens.getOrDefault("id", "1")
            dataRepository.get(id).flatMap { dataRow ->
                refreshService.refresh(dataRow.id.toInt())
            }.then {
                render(json(it))
            }
        }
        get("onlycassandra/:id") {
            val id = allPathTokens.getOrDefault("id", "1")
            dataRepository.get(id).then {
                render(json(it))
            }
        }
    }
}