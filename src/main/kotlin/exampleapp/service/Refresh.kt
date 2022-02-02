package exampleapp.service

import exampleapp.data.Post
import exampleapp.data.PostComments
import exampleapp.data.RefreshItem
import exampleapp.data.User
import kotlinx.coroutines.Deferred
import ratpack.kotlin.coroutines.await
import ratpack.kotlin.coroutines.lazyAsync

class Refresh (
    private val id: Int,
    private val refreshService: RefreshService
) {
    suspend fun refresh(): RefreshItem {
        startCoroutines()

        return RefreshItem(
            post = post.await(),
            postComments = postComments.await(),
            userFirst = userFirst.await(),
            userLast = userLast.await(),
            timeout = doHttpTimeout.await(),
            hostUnknown = doHttpHostNotFound.await(),
            connectError = doHttpConnectError.await(),
            hostUnreachable = doHttpHostUnreachable.await()
        )
    }

    private val createPost: Deferred<Post> by lazyAsync {
        val dummyPost = Post(
            userId = "1",
            title = "a test",
            body = "some test this is"
        )
        refreshService.jsonPlaceholder.createPost(dummyPost).await()
    }

    private val post: Deferred<Post> by lazyAsync {
        // Pretend we are getting the post that we created
        // but really we just do this to chain another call for testing
        createPost.await().id
        // Inject a timeout
        doHttpTimeout.await()
        // Just use post id 1 since the http post method doesn't actually create an object
        val postId = 1
        refreshService.jsonPlaceholder.getSinglePost(postId).await()
    }

    private val postComments: Deferred<PostComments> by lazyAsync {
        val postId = post.await().id
        refreshService.jsonPlaceholder.getPostComments(postId).await()
    }

    private val userFirst: Deferred<User> by lazyAsync {
        val userId = postComments.await().first().id
        refreshService.jsonPlaceholder.getUser(userId).await()
    }

    private val userLast: Deferred<User> by lazyAsync {
        val userId = postComments.await().last().id
        refreshService.jsonPlaceholder.getUser(userId).await()
    }

    private val doHttpTimeout: Deferred<Int> by lazyAsync {
        refreshService.jsonPlaceholder.getURLThatHasTimeout().await()
    }

    private val doHttpHostNotFound: Deferred<Int> by lazyAsync {
        refreshService.jsonPlaceholder.getURLThatHasHostUnknown().await()
    }

    private val doHttpConnectError: Deferred<Int> by lazyAsync {
        refreshService.jsonPlaceholder.getURLThatHasConnectError().await()
    }

    private val doHttpHostUnreachable: Deferred<Int> by lazyAsync {
        refreshService.jsonPlaceholder.getHostUnreachableError().await()
    }

    private fun startCoroutines() {
        val coroutines = listOf(
            createPost,
            post,
            postComments,
            userFirst,
            userLast,
            doHttpTimeout,
            doHttpHostNotFound,
            doHttpConnectError,
            doHttpHostUnreachable
        )
        coroutines.forEach { deferredFunc ->
            deferredFunc.start()
        }
    }
}