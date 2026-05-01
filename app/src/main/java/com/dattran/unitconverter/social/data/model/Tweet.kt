package com.dattran.unitconverter.social.data.model

data class Tweet(
    val id: String,
    val user: TweetUser,
    val content: String,
    val imageUrl: String? = null,
    val isVideo: Boolean = false,
    val likeCount: Int = 0,
    val commentCount: Int = 0,
    val retweetCount: Int = 0,
    val isLiked: Boolean = false,
    val isBookmarked: Boolean = false,
    val timeAgo: String = "",
    val location: String? = null,
    val hashtags: List<String> = emptyList()
)

data class TweetUser(
    val username: String,
    val displayName: String,
    val avatarUrl: String,
    val isVerified: Boolean = false
)

data class StoryItem(
    val id: String,
    val user: TweetUser,
    val isViewed: Boolean = false,
    val isOwn: Boolean = false
)

data class TweetReal(
    val _id: String,
    val user_id: String,
    val content: String,
    val type: Short = 0,  // "text", "image", "video"
    val audience: Short = 0,
    val parentId: String? = null,
    val hashTag: List<String>? = emptyList<String>(),
    val mentions: List<String>? = emptyList<String>(),
    val guest_views: Number? = 0,
    val user_views: Number? = 0,
    val created_at: String? = "",
    val updated_at: String? = "",
)

data class TweetCreateBody(
    val content: String,
    val type: Short = 0,  // "text", "image", "video"
    val audience: Short = 0,
    val parentId: String? = null,
    val hashTags: List<String>? = emptyList<String>(),
    val mentions: List<String>? = emptyList<String>()
)

data class TweetCreateResponse(
    val tweet: TweetReal
)

enum class PostType(val value: Int) {
    Tweet(0),
    ReTweet(1),
    Comment(2)
}

enum class PostAudience(val value: Int) {
    None(-1),
    Everyone(0),
    TwitterCircle(1)
}

data class PostUser(
    val _id: String,
    val name: String,
    val email: String,
    val avatar: String,
)

data class PostHashTags(
    val _id: String,
    val name: String,
    val created_at: String
)

data class Post(
    val id: String,
    val user: PostUser,
    val type: PostType,
    val audience: PostAudience? = PostAudience.None,
    val content: String,
    val parent: PostUser?,
    val hashTags: List<PostHashTags>?,
    val mentions: List<PostUser>?,
    val guestViews: Int,
    val userViews: Int,
    val createdAt: String,
    val updatedAt: String
)

data class PostResponse(
    val _id: String,
    val user: PostUser,
    val type: PostType,
    val audience: PostAudience? = PostAudience.None,
    val content: String,
    val parent: PostUser?,
    val hashTags: List<PostHashTags>?,
    val mentions: List<PostUser>?,
    val guest_views: Int,
    val user_views: Int,
    val created_at: String,
    val updated_at: String
)

data class GetPostResponse(
    val message: String,
    val data: List<PostResponse>
)
