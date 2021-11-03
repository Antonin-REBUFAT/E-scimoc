package com.xiaowugui.e_scimoc.model

/**
 * model of a price returned by the api
 */
data class Price(
    /**
     * define what the price is for, exmple : printPrice
     */
    val type: String,
    val price: Double
)
