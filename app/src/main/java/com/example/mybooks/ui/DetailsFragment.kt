package com.example.mybooks.ui

import android.content.DialogInterface
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.LifecycleOwner
import com.example.mybooks.R
import com.example.mybooks.viewmodel.DetailsFragmentViewModel
import com.example.mybooks.databinding.FragmentDetailsBinding
import com.example.mybooks.helper.BookConstants
import com.example.mybooks.ui.viewholder.BookViewHolder

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!



    private val viewModel: DetailsFragmentViewModel by viewModels()


    private var bookId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)

        bookId = arguments?.getInt(BookConstants.KEY.BOOK_ID) ?: 0
        viewModel.getBookById(bookId)
        setObserver()
        setListeners()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners(){
        binding.imageviewArrow.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.buttonRemove.setOnClickListener {
            handleRemove()
        }

        binding.checkboxFavorite.setOnClickListener {
            handleFavorite()
        }
    }


    private fun handleFavorite(){
        viewModel.changeFavorite(bookId)
    }

    private fun handleRemove(){
        var builder = AlertDialog.Builder(requireContext())
        builder.setMessage(getString(R.string.dialog_message_delete_item)).setPositiveButton(
            getString(R.string.dialog_positive_button_yes)){ dialog, which -> viewModel.deleteBook(bookId)

        }.setNegativeButton(getString(R.string.dialog_negative_button_no)){
            dialog, which ->
        }

        builder.create().show()
    }

    private fun setObserver(){
        viewModel.book.observe(viewLifecycleOwner){
            binding.textviewdetailsTitle.text = it.title
            binding.textviewGenre.text = it.genre
            binding.textviewAutor.text = it.author
            binding.checkboxFavorite.isChecked = it.favorite
            setGenreBackground(it.genre)

        }
        viewModel.bookRemoval.observe(viewLifecycleOwner) {

            if(it){
                Toast.makeText(requireContext(),
                    getString(R.string.msg_removed_successfully),
                    Toast.LENGTH_SHORT).show()
            }
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    fun setGenreBackground(genre: String) {
        when (genre) {
            "Fantasia" -> {
                binding.textviewGenre.setBackgroundResource(R.drawable.rounded_label_fantasy)
            }

            "Terror" -> {
                binding.textviewGenre.setBackgroundResource(R.drawable.rounded_red)
            }

            else -> {
                binding.textviewGenre.setBackgroundResource(R.drawable.rounded_teal)
            }
        }
    }


}