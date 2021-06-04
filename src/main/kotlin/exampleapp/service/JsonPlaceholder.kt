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
            .http()
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
            .http()
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
            .http()
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
}