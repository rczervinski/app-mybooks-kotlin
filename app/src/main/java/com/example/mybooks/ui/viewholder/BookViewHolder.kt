package com.example.mybooks.ui.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.R
import com.example.mybooks.databinding.ItemBookBinding
import com.example.mybooks.entity.BookEntity
import com.example.mybooks.ui.listener.BookListener

class BookViewHolder(private val item : ItemBookBinding,private val listener: BookListener) : RecyclerView.ViewHolder(item.root) {
    fun bind(book: BookEntity) {
        item.textviewTitle.text = book.title
        item.textviewGenre.text = book.genre
        item.textviewAutor.text = book.author

        item.textviewTitle.setOnClickListener{ listener.onClick(book.id)}
        item.favorite.setOnClickListener{ listener.onFavoriteClick(book.id)}
        setGenreBackground(book.genre)
        setIsFavorite(book.favorite)

    }


   fun setGenreBackground(genre: String) {
        when (genre) {
            "Fantasia" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
            }

            "Terror" -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_red)
            }

            else -> {
                item.textviewGenre.setBackgroundResource(R.drawable.rounded_teal)
            }
        }
    }

    private fun setIsFavorite(favorite : Boolean){
        if(favorite){
            item.favorite.setImageResource(R.drawable.ic_favorite)
        }else{
            item.favorite.setImageResource((R.drawable.ic_favorite_empty))
        }
    }


    }
