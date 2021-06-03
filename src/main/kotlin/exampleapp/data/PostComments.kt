package exampleapp.data

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class PostComments : ArrayList<PostCommentItem>()