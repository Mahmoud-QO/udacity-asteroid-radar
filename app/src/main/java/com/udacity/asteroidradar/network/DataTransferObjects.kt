package com.udacity.asteroidradar.network

import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.domain.Asteroid

@JsonClass(generateAdapter = true)
data class NetworkAsteroid(
	val id: Long,
	val codename: String,
	val closeApproachDate: String,
	val absoluteMagnitude: Double,
	val estimatedDiameter: Double,
	val relativeVelocity: Double,
	val distanceFromEarth: Double,
	val isPotentiallyHazardous: Boolean
)

@JsonClass(generateAdapter = true)
data class NetworkAsteroids(val asteroids: List<NetworkAsteroid>)

fun NetworkAsteroids.asDomainModel(): List<Asteroid> {
	return asteroids.map {
		Asteroid(
			id = it.id,
			codename = it.codename,
			closeApproachDate = it.closeApproachDate,
			absoluteMagnitude = it.absoluteMagnitude,
			estimatedDiameter = it.estimatedDiameter,
			relativeVelocity = it.relativeVelocity,
			distanceFromEarth = it.distanceFromEarth,
			isPotentiallyHazardous = it.isPotentiallyHazardous
		)
	}
}

///**
// * Convert network results to database objects
// */
//fun NetworkVideoContainer.asDatabaseModel(): Array<DatabaseVideo> {
//	return videos.map {
//		DatabaseVideo(
//			title = it.title,
//			description = it.description,
//			url = it.url,
//			updated = it.updated,
//			thumbnail = it.thumbnail
//		)
//	}.toTypedArray()
//}