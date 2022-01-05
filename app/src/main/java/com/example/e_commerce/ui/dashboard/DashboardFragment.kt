package com.example.e_commerce.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerce.AddProductActivity
import com.example.e_commerce.MapsActivity
import com.example.e_commerce.R
import com.example.e_commerce.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val cameraGalleryButton = view.findViewById<Button>(R.id.button2)
        cameraGalleryButton.setOnClickListener{
            val intent = Intent(view.context, AddProductActivity::class.java)
            startActivity(intent)
        }
        val Map = view.findViewById<Button>(R.id.button3)
        Map.setOnClickListener{
            val intent = Intent(view.context, MapsActivity::class.java)
            startActivity(intent)
        }


        return view
    }

}