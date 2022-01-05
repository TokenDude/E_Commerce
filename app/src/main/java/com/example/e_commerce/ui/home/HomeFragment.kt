package com.example.e_commerce.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.e_commerce.adapter.MyAdapter
import com.example.e_commerce.model.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.json.JSONObject

class HomeFragment : Fragment() {
    companion object {
        @JvmStatic
        lateinit var ListProductAdapter: MyAdapter
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {val view = inflater.inflate(R.layout.fragment_home, container, false)
        initRecycleView(view)
        addDataSet()
        return view
    }

    private fun addDataSet() {
        val database = Firebase.database
        val itemRef = database.getReference("Products")

        val valueEventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val all_json = JSONObject(dataSnapshot.value.toString())

                    val json_result = all_json.getJSONObject(ds.key!!)

                    val log = json_result.getJSONArray("Product")

                    val name = log.getString(0)
                    val price = log.getString(1)
                    val quantity = log.getString(2)

                    val newProduct = Product(name, price, quantity)
                    ListProductAdapter.add(newProduct)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error", databaseError.message)
            }
        }
        itemRef.addListenerForSingleValueEvent(valueEventListener)
    }

    private fun initRecycleView(view: View) {
        val recycleView = view.findViewById<RecyclerView>(R.id.product_recycler_view)
        recycleView.layoutManager = LinearLayoutManager(this@HomeFragment.context)
        ListProductAdapter = MyAdapter()
        recycleView.adapter = ListProductAdapter
    }
}