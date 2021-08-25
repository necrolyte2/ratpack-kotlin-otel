package exampleapp.data

data class RefreshItem(
    var post: Post,
    var postComments: PostComments,
    var userFirst: User,
    var userLast: User,
    var timeout: Int,
    var hostUnknown: Int,
    var connectError: Int
)