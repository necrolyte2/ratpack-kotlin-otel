package exampleapp.service

import exampleapp.data.Post
import exampleapp.data.PostComments
import exampleapp.data.RefreshItem
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
            postComments = postComments.await()
        )
    }

    private val post: Deferred<Post> by lazyAsync {
        refreshService.jsonPlaceholder.getSinglePost(id).await()
    }

    private val postComments: Deferred<PostComments> by lazyAsync {
        refreshService.jsonPlaceholder.getPostComments(id).await()
    }

    private fun startCoroutines() {
        post
        postComments
    }
}