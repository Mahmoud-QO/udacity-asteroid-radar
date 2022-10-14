package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object Retrofit
{
	private val moshi = Moshi.Builder()
		.add(KotlinJsonAdapterFactory())
		.build()

	private val retrofit = Retrofit.Builder()
		.addConverterFactory(ScalarsConverterFactory.create())
		.addConverterFactory(MoshiConverterFactory.create(moshi))
		.baseUrl(Constants.BASE_URL)
		.build()

	val neoApiService: NeoApiService = retrofit.create(NeoApiService::class.java)
	val planetaryApiService: PlanetaryApiService = retrofit.create(PlanetaryApiService::class.java)

}

interface NeoApiService
{
	@GET("neo/rest/v1/feed")
	suspend fun getAsteroids(
		@Query("start_date") startDate: String = "",
		@Query("end_date") endDate: String = "",
		@Query("api_key") apiKey: String = Constants.API_KEY
	): String

}

interface PlanetaryApiService
{
	@GET("planetary/apod")
	suspend fun getPictureOfDay(
		@Query("api_key") apiKey: String = Constants.API_KEY
	): PictureOfDay

}
