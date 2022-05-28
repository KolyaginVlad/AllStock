package ru.kolyagin.allstock.data.model

import com.google.gson.annotations.SerializedName


data class SymbolsData(

    @SerializedName("currency") var currency: String,
    @SerializedName("description") var description: String,
    @SerializedName("displaySymbol") var displaySymbol: String,
    @SerializedName("figi") var figi: String,
    @SerializedName("mic") var mic: String,
    @SerializedName("symbol") var symbol: String,
    @SerializedName("type") var type: String

)