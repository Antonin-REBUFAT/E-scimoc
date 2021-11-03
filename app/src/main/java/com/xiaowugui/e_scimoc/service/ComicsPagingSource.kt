package com.xiaowugui.e_scimoc.service

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.xiaowugui.e_scimoc.model.Comics
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_OFFSET = 0

class ComicsPagingSource(
    private val service: MarvelApiService,
    private val startWith: String?
) : PagingSource<Int, Comics>() {
    override fun getRefreshKey(state: PagingState<Int, Comics>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(ApiConstant.limit)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(ApiConstant.limit)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Comics> {
        val position = params.key ?: STARTING_OFFSET
        return try {
            val response = service.getComics(offset = position, titleStartsWith = startWith)
            val comics = response.data.results
            val nextKey = if (comics.isEmpty()) null else position + params.loadSize
            LoadResult.Page(
                data = comics,
                prevKey = if (position == STARTING_OFFSET) null else position - params.loadSize,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}