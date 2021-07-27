package app.el_even.priceupdater.views.fragments

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import app.el_even.priceupdater.R
import app.el_even.priceupdater.databinding.ListProductFragmentsFragmentBinding
import app.el_even.priceupdater.views.viewmodels.ListProductFragmentsViewModel
import app.el_even.priceupdater.views.viewmodels.ListProductFragmentsViewModelFactory
import timber.log.Timber

class ListProductFragments : Fragment() {

    private val viewModel: ListProductFragmentsViewModel by viewModels {
        ListProductFragmentsViewModelFactory(application = requireActivity().application)
    }

    private lateinit var binding: ListProductFragmentsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_product_fragments_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // Init view
        viewModel.isFabClicked.observe(viewLifecycleOwner) { isAdded ->
            if (isAdded) {
                Timber.d("Add new item")
                openUrlDialog(requireContext())
                viewModel.addNewUrlDone()
            }
        }

        return binding.root
    }

    private fun openUrlDialog(context: Context) =
        AlertDialog.Builder(context).setCancelable(false).also { builder ->
            builder.setTitle("Paste your URL here")
            val input = EditText(context).also { et ->
                et.hint = "http://www..."
                et.inputType = InputType.TYPE_TEXT_VARIATION_URI
            }
            builder.setView(input)
            builder.setPositiveButton("OK") { dialog, _ ->
                viewModel.addNewProduct(input.text.toString())
                dialog.cancel()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
        }.show()

}