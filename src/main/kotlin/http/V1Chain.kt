package http

import com.google.inject.Inject
import exampleapp.service.RefreshService
import ratpack.jackson.Jackson.json
import ratpack.kotlin.handling.KChain
import ratpack.kotlin.handling.KChainAction

class V1Chain @Inject constructor(
    private val refreshService: RefreshService
): KChainAction() {
    override fun execute() {
        get("hi") {
            render("hey there")
        }
        path(":id") {
            val id = allPathTokens.getOrDefault("id", "1")
            refreshService.refresh(id.toInt()).then {
                render(json(it))
            }
        }
    }
}