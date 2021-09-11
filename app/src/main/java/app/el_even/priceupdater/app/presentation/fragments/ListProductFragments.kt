package app.el_even.priceupdater.app.presentation.fragments

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.el_even.priceupdater.R
import app.el_even.priceupdater.databinding.ListProductFragmentBinding
import app.el_even.priceupdater.app.presentation.viewmodels.ListProductViewModel
import app.el_even.priceupdater.app.presentation.viewmodels.ListProductFragmentsViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author el_even
 * @version 1.0
 */
class ListProductFragments : Fragment() {

    private val viewModel: ListProductViewModel by viewModels {
        ListProductFragmentsViewModelFactory(application = requireActivity().application)
    }

    private lateinit var binding: ListProductFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.list_product_fragment,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Init view
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isFabClicked.collect { isClicked ->
                    if (isClicked) {
                        openUrlDialog(requireContext())
                        viewModel.addNewUrlDone()
                    }
                }
            }
        }

        return binding.root
    }

    private fun openUrlDialog(context: Context) =
        AlertDialog.Builder(context).setCancelable(false).also { builder ->
            builder.setTitle(getString(R.string.url_dialog_title))
            val input = EditText(context).also { et ->
                et.hint = getString(R.string.url_hint)
                et.inputType = InputType.TYPE_TEXT_VARIATION_URI
            }
            builder.setView(input)
            builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
                viewModel.addNewProduct(input.text.toString())
                dialog.cancel()
            }
            builder.setNegativeButton(getString(R.string.cancel_btn)) { dialog, _ ->
                dialog.cancel()
            }
        }.show()

}