package com.xiaowugui.e_scimoc.model

/**
 * model of informations returned with the list of comics
 */
data class Data(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val results: List<Comics>
)
