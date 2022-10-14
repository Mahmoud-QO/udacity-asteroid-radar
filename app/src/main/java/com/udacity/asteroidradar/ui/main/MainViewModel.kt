package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.network.Retrofit
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * MainViewModel designed to store and manage UI-related data in a lifecycle conscious way. This
 * allows data to survive configuration changes such as screen rotations. In addition, background
 * work such as fetching network results can continue through configuration changes and deliver
 * results after the new Fragment or Activity is available.
 *
 * @param app The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 **************************************************************************************************/

class MainViewModel(app: Application) : AndroidViewModel(app)
{
	private val _asteroids = MutableLiveData<List<Asteroid>>()
	val asteroids: LiveData<List<Asteroid>> get() = _asteroids

	private val _picOfDay = MutableLiveData<PictureOfDay>()
	val picOfDay: LiveData<PictureOfDay> get() = _picOfDay

	private val _navToDetail = MutableLiveData<Asteroid>()
	val navToDetail: LiveData<Asteroid> get() = _navToDetail
	fun navToDetail(asteroid: Asteroid) { _navToDetail.value = asteroid }
	fun onNavToDetail() { _navToDetail.value = null }

	init {
		viewModelScope.launch { getPicOfDay() }
		viewModelScope.launch { getAsteroids() }
	}

	private suspend fun getAsteroids() {
		try {
			val jsonObject = JSONObject(Retrofit.neoApiService.getAsteroids())
			_asteroids.value = parseAsteroidsJsonResult(jsonObject)
		} catch (e: Exception) {
			_asteroids.value = listOf(
				Asteroid(0, e.toString(), "22-7-15", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(1, "ABCD-2357", "22-7-15", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(2, "ABCD-2357", "22-7-15", 1.5, 2.5, 3.5, 4.5, true),
				Asteroid(3, "ABCD-2357", "22-7-16", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(4, "ABCD-2357", "22-7-16", 1.5, 2.5, 3.5, 4.5, true),
				Asteroid(5, "ABCD-2357", "22-7-18", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(6, "ABCD-2357", "22-7-18", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(7, "ABCD-2357", "22-7-19", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(8, "ABCD-2357", "22-7-19", 1.5, 2.5, 3.5, 4.5, true)
			)
		}
	}

	private suspend fun getPicOfDay() {
		try {
			_picOfDay.value = Retrofit.planetaryApiService.getPictureOfDay()
		} catch (e: Exception) {
			_asteroids.value = listOf(
				Asteroid(0, e.toString(), "22-7-15", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(1, "ABCD-2357", "22-7-15", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(2, "ABCD-2357", "22-7-15", 1.5, 2.5, 3.5, 4.5, true),
				Asteroid(3, "ABCD-2357", "22-7-16", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(4, "ABCD-2357", "22-7-16", 1.5, 2.5, 3.5, 4.5, true),
				Asteroid(5, "ABCD-2357", "22-7-18", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(6, "ABCD-2357", "22-7-18", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(7, "ABCD-2357", "22-7-19", 1.5, 2.5, 3.5, 4.5, false),
				Asteroid(8, "ABCD-2357", "22-7-19", 1.5, 2.5, 3.5, 4.5, true)
			)
		}
	}

	/**
	 * Factory for constructing MainViewModel with parameter
	 ******************************************************************************************/

	class Factory(private val app: Application) : ViewModelProvider.Factory {
		override fun <T : ViewModel> create(modelClass: Class<T>): T {
			if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
				@Suppress("UNCHECKED_CAST")
				return MainViewModel(app) as T
			}
			throw IllegalArgumentException("Unable to construct viewmodel")
		}
	}

}