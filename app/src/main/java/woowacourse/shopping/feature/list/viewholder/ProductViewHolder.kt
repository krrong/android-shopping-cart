package woowacourse.shopping.feature.list.viewholder

import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemProductBinding
import woowacourse.shopping.feature.list.item.ProductView
import java.text.DecimalFormat

class ProductViewHolder(binding: ViewBinding) : ItemViewHolder(binding) {
    private val binding = binding as ItemProductBinding

    override fun bind(productView: ProductView, onClick: (ProductView) -> Unit) {
        val productItem = productView as ProductView.ProductItem

        Glide.with(binding.root.context)
            .load(productItem.imageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.productImage)

        binding.productName.text = productItem.name
        binding.productPrice.text = "${DecimalFormat("#,###").format(productItem.price)}원"
        binding.root.setOnClickListener { onClick(productItem) }
    }
}
