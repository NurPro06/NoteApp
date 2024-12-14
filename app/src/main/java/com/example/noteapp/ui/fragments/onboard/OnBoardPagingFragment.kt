package com.example.noteapp.ui.fragments.onboard

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardPagingBinding

class OnBoardPagingFragment : Fragment() {

    private lateinit var binding: FragmentOnBoardPagingBinding

    companion object{
        const val ARG_ONBOARD_POSITION = "OnBoardPosition"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() = with(binding) {
        when(requireArguments().getInt(ARG_ONBOARD_POSITION)){
            0->{



                lottieAnimation.setAnimation(R.raw.first)
                txtTitle.text = "Удобство"
                txtBody.text = "Создавайте заметки в два клика! Записывайте мысли, идеи и важные задачи мгновенно."
            }
            1->{


                lottieAnimation.setAnimation(R.raw.sigma)
                txtTitle.text = "Организация"
                txtBody.text = "Организуйте заметки по папкам и тегам. Легко находите нужную информацию в любое время."
            }
            2->{


                lottieAnimation.setAnimation(R.raw.third)
                txtTitle.text = "Синхронизация"
                txtBody.text = "Синхронизация на всех устройствах. Доступ к записям в любое время и в любом месте."
            }
            else -> {
                txtTitle.text = "Ошибка"
                lottieAnimation.setAnimation(R.raw.sigma)
                txtBody.text = "Что-то пошло не по плану."
            }
        }

    }


}