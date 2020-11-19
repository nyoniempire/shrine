package com.google.codelabs.mdc.kotlin.shrine

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.codelabs.mdc.kotlin.shrine.network.ProductEntry
import com.google.codelabs.mdc.kotlin.shrine.staggeredgridlayout.StaggeredProductCardRecyclerViewAdapter

class ProductGridFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.shr_product_grid_fragment, container, false)

        setUpToolbar(view)

        val myAdapter = ProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
        val largePadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large)
        val smallPadding = resources.getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.HORIZONTAL,false).apply {
                spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
                    override fun getSpanSize(position: Int): Int {
                        return when(position%3){
                            2->2
                            else->1
                        }
                    }
                }
            }
            adapter = StaggeredProductCardRecyclerViewAdapter(ProductEntry.initProductEntryList(resources))
            addItemDecoration(ProductGridItemDecoration(largePadding,smallPadding))

        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            view.findViewById<NestedScrollView>(R.id.product_grid).setBackgroundResource(R.drawable.shr_product_grid_background_shape)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private fun setUpToolbar(view: View){
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.app_bar)
        val activity = activity as AppCompatActivity
        if(activity != null){
            activity.setSupportActionBar(toolbar!!)
        }

        toolbar.setNavigationOnClickListener(NavigationIconClickListener(requireContext()
                ,view.findViewById(R.id.product_grid),
                AccelerateDecelerateInterpolator(),
                requireContext().resources.getDrawable(R.drawable.shr_branded_menu),
                requireContext().resources.getDrawable(R.drawable.shr_close_menu)))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.shr_toolbar_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
