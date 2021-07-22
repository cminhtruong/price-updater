package app.el_even.priceupdater.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.el_even.priceupdater.R
import app.el_even.priceupdater.views.viewmodels.ListProductFragmentsViewModel

class ListProductFragments : Fragment() {

    companion object {
        fun newInstance() = ListProductFragments()
    }

    private lateinit var viewModel: ListProductFragmentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_product_fragments_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListProductFragmentsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}