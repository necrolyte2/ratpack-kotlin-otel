package sample


import exampleapp.ApplicationModule
import exampleapp.service.JsonPlaceholder
import http.V1Chain
import mu.KotlinLogging
import ratpack.jackson.Jackson.json
import ratpack.kotlin.handling.ratpack
import ratpack.server.ServerConfig
import ratpack.service.Service
import ratpack.service.StartEvent
import javax.inject.Inject
import javax.inject.Singleton


fun main() {
    app()
}

fun app(bindings: List<Any> = listOf()) = ratpack {
    System.setProperty("log4j2.contextSelector", "org.apache.logging.log4j.core.async.AsyncLoggerContextSelector")

    bindings {
        for (b in bindings) {
            bindInstance(b)
        }
        module(ApplicationModule(serverConfig))
        bind(StartupService::class.java)
    }

    handlers {
        prefix("v1", V1Chain::class.java)
    }
}

@Singleton
class StartupService @Inject constructor(val serverConfig: ServerConfig) : Service {
    val logger = KotlinLogging.logger {StartupService::class.java}
    override fun onStart(event: StartEvent?) {
        logger.info {
            "STARTED"
        }
    }
}