package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ProductView

class ProductViewHolder(
    val parent: ViewGroup,
) : ItemViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_product, parent, false),
) {
    private val binding = ItemProductBinding.bind(itemView)

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        val productItem = productView as ProductView.ProductItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.productImage)

        binding.productName.text = productItem.name
        binding.productPrice.text = parent.context.getString(R.string.price_format, productItem.price)
        binding.root.setOnClickListener { onClick(productItem) }
    }
}
