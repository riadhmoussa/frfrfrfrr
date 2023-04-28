package com.pipay.myfeed

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostDataSource(private val scope: CoroutineScope): PositionalDataSource<Post>() {

    private val postService = HttpClient.postService
    private val loadCount = 10

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Post>) {
        val position = computeInitialLoadPosition(params, 0)
        val loadSize = computeInitialLoadPosition(params, loadCount)
        scope.launch(Dispatchers.IO) {
            try {
                val result = postService.posts(10, position)
                callback.onResult(result, position, result.size)
            } catch (ie: Exception) {
                ie.printStackTrace()
            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Post>) {
        scope.launch(Dispatchers.IO) {
            try {
                val result = postService.posts(params.loadSize, params.startPosition)
                callback.onResult(result)
            } catch (ie: Exception) {

            }
        }
    }

    class Factory(private val scope: CoroutineScope): DataSource.Factory<Int, Post>() {
        override fun create(): DataSource<Int, Post> {
            return PostDataSource(scope)
        }
    }

}