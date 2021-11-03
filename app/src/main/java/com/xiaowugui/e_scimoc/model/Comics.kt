package com.xiaowugui.e_scimoc.model

import android.os.Parcelable
import com.squareup.moshi.FromJson
import kotlinx.parcelize.Parcelize

/**
 * model of a comics
 */
@Parcelize
data class Comics(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val description: String? = null,
    val isbn: String,
    val format: String,
    val pageCount: Int,
    val series: String,
    val onSaleDate: String,
    val price: Double
) : Parcelable {
    object ADAPTER {
        /**
         * Adapter to merge the thumbnail, the series, the dates and the price in a single field
         */
        data class ComicsWithThumbnail(
            val id: Int,
            val title: String,
            val thumbnail: Thumbnail,
            val description: String? = null,
            val isbn: String,
            val format: String,
            val pageCount: Int,
            val series: Series,
            val dates: List<ComicsDate>,
            val prices: List<Price>
        )

        @FromJson
        fun fromJson(json: ComicsWithThumbnail): Comics {
            val date = json.dates.find { comicsDate -> comicsDate.type == "onsaleDate" }
            val price = json.prices.find { price -> price.type == "printPrice" }

            return Comics(
                json.id,
                json.title,
                "${json.thumbnail.path}.${json.thumbnail.extension}",
                json.description,
                json.isbn,
                json.format,
                json.pageCount,
                json.series.name,
				date?.date ?: "",
                price?.price ?: 0.0
            )
        }
    }
}

