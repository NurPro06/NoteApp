package com.example.noteapp.ui.fragments.onboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentOnBoardBinding
import com.example.noteapp.ui.adapters.OnBoardViewpagerAdapter
import com.example.noteapp.utlis.PreferenceHelper


class OnBoardFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardBinding
    private lateinit var sharedPreferences: PreferenceHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedPreferences = PreferenceHelper()
        sharedPreferences.init(requireContext())
        binding = FragmentOnBoardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        if (sharedPreferences.isOnBoardingCompleted(true)) {
            findNavController().navigate(R.id.action_onBoardFragment_to_signInFragment)
        } else {
            initialize()
            setupListeners()
        }
    }


    private fun initialize() {
        binding.viewpager2.adapter = OnBoardViewpagerAdapter(this)
        binding.dots1.attachTo(binding.viewpager2)

    }

    private fun setupListeners() = with(binding.viewpager2) {
        registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.btnStart.visibility = View.VISIBLE
                    binding.txtSkip.visibility = View.GONE
                } else {
                    binding.btnStart.visibility = View.GONE
                    binding.txtSkip.visibility = View.VISIBLE
                    binding.txtSkip.setOnClickListener {
                        if (currentItem < 3) {
                            setCurrentItem(currentItem + 2, true)
                        }
                    }
                }
            }
        })
        binding.btnStart.setOnClickListener {
            sharedPreferences.setOnBoardingCompleted(true)
            findNavController().navigate(R.id.action_onBoardFragment_to_signInFragment)

        }
    }

}