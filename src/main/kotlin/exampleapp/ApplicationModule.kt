package exampleapp

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import exampleapp.service.DataRepository
import exampleapp.service.RefreshService
import http.V1Chain
import ratpack.server.ServerConfig

class ApplicationModule constructor(val serverConfig: ServerConfig) : AbstractModule() {
    override fun configure() {
        bind(RefreshService::class.java).`in`(Scopes.SINGLETON)
        bind(V1Chain::class.java).`in`(Scopes.SINGLETON)
        bind(DataRepository::class.java).`in`(Scopes.SINGLETON)
    }
}