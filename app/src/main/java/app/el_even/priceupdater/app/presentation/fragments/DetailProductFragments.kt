package app.el_even.priceupdater.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.el_even.priceupdater.R
import app.el_even.priceupdater.databinding.DetailProductFragmentBinding
import app.el_even.priceupdater.app.presentation.viewmodels.DetailProductViewModel

class DetailProductFragments : Fragment() {

    private val viewModel: DetailProductViewModel by viewModels()
    private lateinit var binding: DetailProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.detail_product_fragment, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

}