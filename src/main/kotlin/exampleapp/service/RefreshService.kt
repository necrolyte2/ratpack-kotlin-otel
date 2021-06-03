package exampleapp.service

import exampleapp.data.RefreshItem
import ratpack.exec.Promise
import ratpack.kotlin.coroutines.promise
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshService @Inject constructor(
    val jsonPlaceholder: JsonPlaceholder
) {
    fun refresh(id: Int): Promise<RefreshItem> = promise {
        Refresh(id, this@RefreshService).refresh()
    }
}