package com.xiaowugui.e_scimoc.model

/**
 * model of a date returned by the api
 */
data class ComicsDate(
    /**
     * type of date, like onSaleDate, or FOCDate
     */
    val type: String,
    val date: String
)
