package com.udacity.asteroidradar.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.HeaderDayBinding
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ASTEROID = 1

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 ***************************************************************************************************
 */
class AsteroidListAdapter(private val itemListener: ItemListener) :
	ListAdapter<DataItem, RecyclerView.ViewHolder>(DiffUtilCallback())
{
	fun submitAsteroidList(list: List<Asteroid>?) {
		CoroutineScope(Dispatchers.Default).launch {
			val items = mutableListOf<DataItem>()
			var today = ""

			list?.forEach{
				if(it.closeApproachDate == today){
					items.add(DataItem.AsteroidItem(it))
				}else{
					today = it.closeApproachDate
					items.add(DataItem.DayHeader(it.closeApproachDate))
					items.add(DataItem.AsteroidItem(it))
				}
			}

			withContext(Dispatchers.Main){ submitList(items) }
		}
	}

	override fun getItemViewType(position: Int): Int {
		return when(getItem(position)) {
			is DataItem.DayHeader -> ITEM_VIEW_TYPE_HEADER
			is DataItem.AsteroidItem -> ITEM_VIEW_TYPE_ASTEROID
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		return when(viewType) {
			ITEM_VIEW_TYPE_HEADER -> DayViewHolder.createObject(parent)
			ITEM_VIEW_TYPE_ASTEROID -> AsteroidViewHolder.createObject(parent)
			else -> throw java.lang.ClassCastException("Unknown viewType $viewType")
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when(holder) {
			is DayViewHolder -> {
				val item = getItem(position) as DataItem.DayHeader
				holder.bind(item.day)
			}
			is AsteroidViewHolder -> {
				val item = getItem(position) as DataItem.AsteroidItem
				holder.bind(item.asteroid , itemListener)
			}
		}
	}

	/**
	 * DiffUtilCallback
	 *******************************************************************************************
	 */
	class DiffUtilCallback : DiffUtil.ItemCallback<DataItem>()
	{
		override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
			return newItem.id == oldItem.id
		}

		override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
			return newItem == oldItem
		}
	}

	/**
	 * ItemListener for Asteroid items.
	 *******************************************************************************************
	 */
	class ItemListener(private val _onClick: (asteroid: Asteroid) -> Unit)
	{
		fun onClick(asteroid: Asteroid) = _onClick(asteroid)
	}

}

/**
 * DataItem sealed class that contains classes for all different items in the recycler view.
 ***************************************************************************************************
 */
sealed class DataItem
{
	// This id is at the areItemsTheSame function of DiffUtilCallback
	abstract val id: Long

	data class AsteroidItem(val asteroid: Asteroid) : DataItem() {
		override val id = asteroid.id
	}

	data class DayHeader(val day: String) : DataItem() {
		override val id = Long.MIN_VALUE
	}

}

/**
 * ViewHolder for Asteroid items. All work is done by data binding.
 ***************************************************************************************************
 */
class AsteroidViewHolder private constructor(private val binding: ItemAsteroidBinding) :
	RecyclerView.ViewHolder(binding.root)
{
	companion object {
		fun createObject(parent: ViewGroup): AsteroidViewHolder {
			val binding: ItemAsteroidBinding = DataBindingUtil.inflate(
				LayoutInflater.from(parent.context),
				R.layout.item_asteroid, parent,
				false
			)
			return AsteroidViewHolder(binding)
		}
	}

	fun bind(item: Asteroid, itemListener: AsteroidListAdapter.ItemListener) {
		binding.asteroid = item
		binding.listener = itemListener
		binding.executePendingBindings()
	}
}

/**
 * ViewHolder for DayHeader items. All work is done by data binding.
 ***************************************************************************************************
 */
class DayViewHolder private constructor(private val binding: HeaderDayBinding) :
	RecyclerView.ViewHolder(binding.root)
{
	companion object {
		fun createObject(parent: ViewGroup): DayViewHolder {
			val binding: HeaderDayBinding = DataBindingUtil.inflate(
				LayoutInflater.from(parent.context),
				R.layout.header_day, parent,
				false
			)
			return DayViewHolder(binding)
		}
	}

	fun bind(day: String) {
		binding.day = day
		binding.executePendingBindings()
	}
}

/*

/**
 * RecyclerView Adapter for setting up data binding on the items in the list.
 ***************************************************************************************************
 */
class AsteroidListAdapter(private val itemListener: ItemListener) :
	ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(DiffUtilCallback())
{
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder.createObject(parent)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind(getItem(position), itemListener)
	}

	/**
	 * ViewHolder for Asteroid items. All work is done by data binding.
	 *******************************************************************************************
	 */
	class ViewHolder private constructor(private val binding: ItemAsteroidBinding) :
		RecyclerView.ViewHolder(binding.root)
	{
		companion object {
			fun createObject(parent: ViewGroup): ViewHolder {
				val binding: ItemAsteroidBinding = DataBindingUtil.inflate(
					LayoutInflater.from(parent.context),
					R.layout.item_asteroid, parent,
					false
				)
				return ViewHolder(binding)
			}
		}

		fun bind(item: Asteroid, itemListener: ItemListener) {
			binding.asteroid = item
			binding.listener = itemListener
			binding.executePendingBindings()
		}
	}

	/**
	 * DiffUtilCallback for Asteroid items.
	 *******************************************************************************************
	 */
	class DiffUtilCallback : DiffUtil.ItemCallback<Asteroid>()
	{
		override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
			return newItem.id == oldItem.id
		}

		override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
			return newItem == oldItem
		}

	}

	/**
	 * ItemListener for Asteroid items.
	 *******************************************************************************************
	 */
	class ItemListener(private val _onClick: (asteroid: Asteroid) -> Unit)
	{
		fun onClick(asteroid: Asteroid) = _onClick(asteroid)
	}

}
/**************************************************************************************************/

*/