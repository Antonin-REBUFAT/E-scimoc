package com.xiaowugui.e_scimoc.model

import com.squareup.moshi.FromJson

data class Comics(
 val id: Int,
 val title: String,
 val thumbnail: String,
 val description: String? = null
) {
 object ADAPTER{
  /**
   * Adapter to merge the thumbnail field
   */
  data class ComicsWithThumbnail (
   val id: Int,
   val title: String,
   val thumbnail: Thumbnail,
   val description: String? = null
  )

  @FromJson
  fun fromJson(json: ComicsWithThumbnail): Comics {
   return Comics(json.id, json.title, "${json.thumbnail.path}.${json.thumbnail.extension}", json.description)
  }
 }
}

