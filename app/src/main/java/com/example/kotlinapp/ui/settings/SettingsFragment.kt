package com.example.kotlinapp.ui.settings

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.example.kotlinapp.databinding.FragmentSettingsBinding
import com.google.android.material.switchmaterial.SwitchMaterial

private const val IS_RATING_9_KEY = "IS_RATING_9_KEY"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel: SettingsViewModel
    private lateinit var switchRating9: SwitchMaterial

    companion object {
        fun newInstance() = SettingsFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        switchRating9 = binding.switchRating9
        switchRating9.isChecked = isRating9FromSP()
        
        switchRating9.setOnClickListener(){
            saveRatingToSP(switchRating9.isChecked)
        }

    }

    private fun saveRatingToSP(checked: Boolean) {
        activity?.let {
            it.getPreferences(Context.MODE_PRIVATE).edit().putBoolean(IS_RATING_9_KEY, checked).commit()
        }
    }

    private fun isRating9FromSP() : Boolean{
        activity?.let {
            return it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_RATING_9_KEY, false)
        }.run {
            return false
        }
    }
}