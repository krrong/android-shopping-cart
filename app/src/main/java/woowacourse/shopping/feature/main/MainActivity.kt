package woowacourse.shopping.feature.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.domain.Product
import woowacourse.shopping.R
import woowacourse.shopping.data.product.ProductDbHandler
import woowacourse.shopping.data.product.ProductDbHelper
import woowacourse.shopping.databinding.ActivityMainBinding
import woowacourse.shopping.feature.cart.CartActivity
import woowacourse.shopping.feature.list.adapter.ProductListAdapter
import woowacourse.shopping.feature.list.item.ProductListItem
import woowacourse.shopping.feature.model.mapper.toItem
import woowacourse.shopping.feature.model.mapper.toUi
import woowacourse.shopping.feature.product.detail.ProductDetailActivity

class MainActivity : AppCompatActivity(), MainContract.View {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!
    private val dbHandler: ProductDbHandler by lazy {
        ProductDbHandler(ProductDbHelper(this).writableDatabase)
    }

    lateinit var adapter: ProductListAdapter
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this, dbHandler)

        initAdapter()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        val dummy = Product(1, "23", "name", 13000).toUi().toItem()
        adapter = ProductListAdapter(
            recentItems = listOf(dummy),
            onItemClick = { listItem ->
                when (listItem) {
                    is ProductListItem -> {
                        ProductDetailActivity.startActivity(this@MainActivity, listItem.toUi())
                    }
                }
            }
        )
        binding.productRv.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == ViewType.HORIZONTAL.ordinal) {
                    gridLayoutManager.spanCount
                } else 1
            }
        }
        binding.productRv.layoutManager = gridLayoutManager
    }

    override fun setProducts(products: List<Product>) {
        adapter.setItems(products.map { it.toUi().toItem() })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cart -> { CartActivity.startActivity(this) }
        }
        return true
    }
}
