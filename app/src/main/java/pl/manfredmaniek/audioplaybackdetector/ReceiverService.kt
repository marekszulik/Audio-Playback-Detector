package pl.manfredmaniek.audioplaybackdetector

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReceiverService {
    @GET("YamahaExtendedControl/v1/main/setPower?power=on")
    suspend fun turnOn(): Response<ResponseBody>

    @GET("YamahaExtendedControl/v1/main/setInput")
    suspend fun selectInput(@Query("input") input: String): Response<ResponseBody>

    @GET("YamahaExtendedControl/v1/main/setVolume")
    suspend fun setVolume(@Query("volume") volume: String): Response<ResponseBody>
}
