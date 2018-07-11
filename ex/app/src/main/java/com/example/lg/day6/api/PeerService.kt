package com.example.lg.day6.api

import com.example.lg.day6.data.RnDInfoRes
import retrofit2.Call
import retrofit2.http.GET

interface PeerService{
    @GET("peer-list.json")
    fun getRnDPeerList(): Call<RnDInfoRes>
}