package com.pipay.myfeed

import com.google.gson.annotations.SerializedName

data class Post(
    @field:SerializedName("id")
    val id: Long? = null,

    @field:SerializedName("userId")
    val userId: Long? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null
) {
    val imageUrl: String? get() {
        val id = id ?: return null
        return "https://i.picsum.photos/id/${400+id}/500/500.jpg"
    }

    val thumbnailUrl: String? get() {
        val id = id ?: return null
        return "https://i.picsum.photos/id/${400+id}/50/50.jpg"
    }
    
    val thumbnailUrl2: String? get() {
        val id = id ?: return null
        return "https://i.picsum.photos/id/${400+id}/100/100.jpg"
    }
}