package app.el_even.priceupdater.configs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.el_even.priceupdater.databinding.ProductItemBinding
import app.el_even.priceupdater.models.local.Product

/**
 * @author el_even
 * @version 1.0
 */
class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallBack) {
    companion object DiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id === newItem.id
        }
    }

    inner class ProductViewHolder(private var binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.product = product
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context))
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}