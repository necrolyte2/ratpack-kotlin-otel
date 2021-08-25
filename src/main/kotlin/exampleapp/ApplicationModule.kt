package exampleapp

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import exampleapp.service.DataRepository
import exampleapp.service.RefreshService
import http.V1Chain
import ratpack.server.ServerConfig
import ratpackkotlinotel.promisesession.PromiseSession
import ratpackkotlinotel.promisesession.PromiseSessionProvider

class ApplicationModule constructor(val serverConfig: ServerConfig) : AbstractModule() {
    override fun configure() {
        //bind(PromiseSession::class.java).toProvider(PromiseSessionProvider::class.java).`in`(Scopes.SINGLETON)
        bind(RefreshService::class.java).`in`(Scopes.SINGLETON)
        bind(V1Chain::class.java).`in`(Scopes.SINGLETON)
        //bind(DataRepository::class.java).`in`(Scopes.SINGLETON)
    }
}