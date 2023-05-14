package woowacourse.shopping.feature.list.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import woowacourse.shopping.R
import woowacourse.shopping.databinding.ItemCartProductBinding
import woowacourse.shopping.feature.list.item.CartProductItem
import java.text.DecimalFormat

class CartProductViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_cart_product, parent, false),
) {
    private val binding = ItemCartProductBinding.bind(itemView)

    fun bind(cartProductItem: CartProductItem, onClick: (CartProductItem) -> Unit) {
        Glide.with(binding.root.context)
            .load(cartProductItem.productImageUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.cartProductImageView)

        binding.cartProductNameTv.text = cartProductItem.productName
        binding.cartProductPriceTv.text = "${DecimalFormat("#,###").format(cartProductItem.productPrice)}원"
        binding.cartClearImageView.setOnClickListener { onClick(cartProductItem) }
    }
}
