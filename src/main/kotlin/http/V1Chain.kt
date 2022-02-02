package http

import com.google.inject.Inject
import exampleapp.service.DataRepository
import exampleapp.service.RefreshService
import ratpack.exec.util.ParallelBatch
import ratpack.jackson.Jackson.json
import ratpack.kotlin.handling.KChain
import ratpack.kotlin.handling.KChainAction

class V1Chain @Inject constructor(
    private val refreshService: RefreshService//,
    //private val dataRepository: DataRepository
): KChainAction() {
    override fun execute() {
        get("hi") {
            render("hey there")
        }
        path("connectiontimeout") {
            refreshService.jsonPlaceholder.getURLThatHasConnectError().then {
                render("fetched conn error host")
            }
        }
        path("unknownhost") {
            refreshService.jsonPlaceholder.getURLThatHasHostUnknown().then {
                render("fetched unknown host")
            }
        }
        path("onlyhttp/:id") {
            val id = allPathTokens.getOrDefault("id", "1")
            refreshService.refresh(id.toInt()).then {
                render(json(it))
            }
        }
        path("localthing") {
            val qParams = this.request.queryParams
            var hostname = "localhost"
            if ("hostname" in qParams) {
                hostname = qParams["hostname"].toString()
            }
            refreshService.jsonPlaceholder.getLocalURL(hostname).then {
                render("did local url for $hostname<br/>$it")
            }
        }
//        path("cassandrahttp/:id") {
//            val id = allPathTokens.getOrDefault("id", "1")
//            val promises = listOf(
//                dataRepository.get(id)
//            )
//            ParallelBatch.of(promises).yieldAll()
//            .flatMap {
//                val dataRow = it[0].value
//                refreshService.refresh(dataRow.id.toInt())
//            }.then {
//                render(json(it))
//            }
//        }
//        get("onlycassandra/:id") {
//            val id = allPathTokens.getOrDefault("id", "1")
//            dataRepository.get(id).then {
//                render(json(it))
//            }
//        }
    }
}