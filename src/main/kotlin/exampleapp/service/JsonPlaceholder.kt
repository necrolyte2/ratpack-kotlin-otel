package exampleapp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import exampleapp.data.Post
import exampleapp.data.PostComments
import exampleapp.data.User
import ratpack.exec.Promise
import ratpack.http.HttpMethod
import ratpack.http.HttpUrlBuilder
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse
import java.time.Duration

class JsonPlaceholder @Inject constructor(
    private val httpClient: HttpClient,
    private val objectMapper: ObjectMapper
){
    fun createPost(post: Post): Promise<Post> {
        val url = HttpUrlBuilder
            .http()
            .host("jsonplaceholder.typicode.com")
            .path("posts")
            .build()
        println(url)
        return httpClient.get(url) { spec ->
            spec.method(HttpMethod.POST)
            spec.headers.set("content-type", "application/json")
            spec.body {
                it.bytes(objectMapper.writeValueAsBytes(post))
            }
        }.map {
            val body = it.body.text
            println(body)
            objectMapper.readValue(body, Post::class.java)
        }
    }

    fun getSinglePost(id: Int): Promise<Post> {
        val url = HttpUrlBuilder
            .https()
            .host("jsonplaceholder.typicode.com")
            .path("posts")
            .path(id.toString())
            .build()
        println(url)
        return httpClient.get(url).map {
            val body = it.body.text
            println(body)
            objectMapper.readValue(body, Post::class.java)
        }
    }

    fun getPostComments(id: Int): Promise<PostComments> {
        val url = HttpUrlBuilder
            .https()
            .host("jsonplaceholder.typicode.com")
            .path("posts")
            .path(id.toString())
            .path("comments")
            .build()
        println(url)
        return httpClient.get(url).map {
            val body = it.body.text
            println(body)
            objectMapper.readValue(body, PostComments::class.java)
        }
    }

    fun getUser(id: Int): Promise<User> {
        val url = HttpUrlBuilder
            .https()
            .host("jsonplaceholder.typicode.com")
            .path("users")
            .path(id.toString())
            .build()
        println(url)
        return httpClient.get(url).map {
            val body = it.body.text
            println(body)
            objectMapper.readValue(body, User::class.java)
        }
    }

    fun getErrorURL(host: String, path: String, port: Int, params: Map<String, *>): Promise<Int> {
        val url = HttpUrlBuilder
            .http()
            .host(host)
            .port(port)
            .path(path)
            .params("duration", "5s")
            .build()
        println(url)

        return httpClient.copyWith {
            it.readTimeout(Duration.ofSeconds(1))
            it.connectTimeout( Duration.ofSeconds(1))
        }
            .get(url)
            .map {
                1
            }.mapError {
                println("an error was seen")
                1
            }
    }

    fun getURLThatHasTimeout(): Promise<Int> {
       return getErrorURL("localhost", "api/v1/timeout", 9999, mapOf("duration" to "60s"))
    }

    fun getURLThatHasHostUnknown(): Promise<Int> {
        return getErrorURL("invalid.host.com", "", 80, emptyMap<String, String>())
    }

    fun getURLThatHasConnectError(): Promise<Int> {
        return getErrorURL("localhost", "", 9998, emptyMap<String, String>())
    }
}