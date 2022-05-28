package ru.kolyagin.allstock.data.model

import com.google.gson.annotations.SerializedName

data class Stock(
    @SerializedName("p")
    val p: Double,
    @SerializedName("s")
    val s: String,
    @SerializedName("t")
    val t: Long,
    @SerializedName("v")
    val v: Double
)