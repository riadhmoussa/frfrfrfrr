package com.pipay.myfeed

import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {

    @GET("posts")
    suspend fun posts(
        @Query("_limit") limit: Int,
        @Query("_start") offset: Int
    ): List<Post>

}