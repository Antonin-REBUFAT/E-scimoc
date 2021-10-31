package com.xiaowugui.e_scimoc.model

data class Comics(
 val id: Int,
 val title: String,
 val thumbnail: Thumbnail,
 val description: String? = null
)