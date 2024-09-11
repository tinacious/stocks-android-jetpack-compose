package com.tinaciousdesign.interviews.stocks.networking.api

import com.tinaciousdesign.interviews.stocks.models.Stock
import retrofit2.http.GET

interface StocksApi {
    // This endpoint will allow you to search for "omni" or "lol" and see matches where exact matches are prioritized.
    // To use, comment out the provided endpoint GET("...") and comment this one back in
//    @GET("tinacious/a3ddc32e49c04b5de21e4bb30eb47e68/raw/5b590f6f369fb92fc49e33a14ab2275eb5629c24/mock-stocks.json")

    // Provided endpoint
    @GET("priyanshrastogi/0e1d4f8d517698cfdced49f5e59567be/raw/9158ad254e92aaffe215e950f4846a23a0680703/mock-stocks.json")
    suspend fun getStocks(): List<Stock>
}
