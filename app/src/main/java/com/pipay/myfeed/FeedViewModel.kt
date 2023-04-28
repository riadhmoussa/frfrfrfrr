package com.pipay.myfeed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FeedViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    private val _posts = MutableLiveData<DataResult<List<Post>>>()
    val posts: LiveData<DataResult<List<Post>>> = _posts
    val isLoading: LiveData<Boolean> = _isLoading
    val pagedPosts: LiveData<PagedList<Post>>

    init {
        val factory = PostDataSource.Factory(viewModelScope)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setInitialLoadSizeHint(10)
            .build()

        pagedPosts = LivePagedListBuilder(factory, config).build()
    }


    fun fetchPosts(offset: Int = 0, limit: Int = 10) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val posts = HttpClient.postService.posts(limit, offset)
                _posts.postValue(createSuccess(posts))
            } catch (ie: IOException) {
                _posts.postValue(
                    createError("Unable to get posts")
                )
            } catch (he: HttpException) {
                _posts.postValue(
                    createError("Unable to get posts")
                )
            } finally {
                _isLoading.postValue(false)
            }
        }
    }


    private fun createSuccess(value: List<Post>): DataResult.Success<List<Post>> {
        return DataResult.Success(value)
    }

    private fun createError(message: String): DataResult.Failure<List<Post>> {
        return DataResult.Failure(message, null)
    }
}