package com.moviedb.feature.movieDetails.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moviedb.databinding.ItemListCreditsBinding
import com.moviedb.network.model.TMDbMovieCredits

class MovieCreditsAdapter :
    ListAdapter<TMDbMovieCredits.Cast, MovieCreditsAdapter.CreditsViewHolder>(DiffCallback) {
    class CreditsViewHolder(private val binding: ItemListCreditsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(credits: TMDbMovieCredits.Cast) {
            binding.credits = credits
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<TMDbMovieCredits.Cast>() {
        override fun areItemsTheSame(
            oldItem: TMDbMovieCredits.Cast,
            newItem: TMDbMovieCredits.Cast
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: TMDbMovieCredits.Cast,
            newItem: TMDbMovieCredits.Cast
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditsViewHolder {
        return CreditsViewHolder(
            ItemListCreditsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CreditsViewHolder, position: Int) {
        val credit = getItem(position)
        holder.bind(credit)
    }

}