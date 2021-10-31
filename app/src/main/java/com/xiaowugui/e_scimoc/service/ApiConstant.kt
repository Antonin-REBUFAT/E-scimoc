package com.xiaowugui.e_scimoc.service

import com.xiaowugui.e_scimoc.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class ApiConstant {
    companion object {
        const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        val ts = Timestamp(System.currentTimeMillis()).time.toString()


        fun hash(): String {
            val input = "$ts${BuildConfig.MARVEL_API_KEY_PRIVATE}${BuildConfig.MARVEL_API_KEY}"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}