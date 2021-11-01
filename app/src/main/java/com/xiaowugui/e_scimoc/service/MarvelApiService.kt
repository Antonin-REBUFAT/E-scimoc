package com.xiaowugui.e_scimoc.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.xiaowugui.e_scimoc.BuildConfig
import com.xiaowugui.e_scimoc.model.Comics
import com.xiaowugui.e_scimoc.model.ComicsResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * init moshi
 */
private val moshi = Moshi.Builder()
    .add(Comics.ADAPTER)
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * init retrofit
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(ApiConstant.BASE_URL)
    .build()

interface MarvelApiService {

    /**
     * get a list of comics between the offset and the offset+limit
     * can be filtered by title
     */
    @GET("comics")
    suspend fun getComics(
        @Query("ts") ts: String = ApiConstant.ts,
        @Query("apikey") apiKey: String = BuildConfig.MARVEL_API_KEY,
        @Query("hash") hash: String = ApiConstant.hash(),
        @Query("limit") limit: Int = ApiConstant.limit,
        @Query("offset") offset: Int = 0,
        @Query("titleStartsWith") titleStartsWith: String? = null
    ): ComicsResponse


    companion object MarvelApi {
        private val retrofitService: MarvelApiService by lazy {
            retrofit.create(MarvelApiService::class.java)
        }

        fun create(): MarvelApiService { return retrofitService }


    }
}