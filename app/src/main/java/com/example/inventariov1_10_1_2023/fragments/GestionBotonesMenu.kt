package com.example.inventariov1_10_1_2023.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.activitys.MainActivity
import com.example.inventariov1_10_1_2023.databinding.FragmentGestionBotonesMenuBinding
import com.google.firebase.auth.FirebaseAuth


class GestionBotonesMenu : Fragment(R.layout.fragment_gestion_botones_menu) {
    private var _binding: FragmentGestionBotonesMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGestionBotonesMenuBinding.inflate(inflater,container,false)
        val view = binding.root

        /**
         * paso de fragment a fragment
         */
        binding.BtConsultar.setOnClickListener{
            val fragmentManager = ConsultarFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_view, fragmentManager)?.addToBackStack(null)
                ?.commit()
        }

        binding.BtInsertar.setOnClickListener {
            val fragmentManager = InsertarFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view,fragmentManager)
                ?.addToBackStack(null)?.commit()
        }

        binding.BtModificar.setOnClickListener {
            val fragmentManager = ModificarFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view,fragmentManager)
                ?.addToBackStack(null)?.commit()
        }
        binding.BtLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            // Y volvemos al main

            val intent = Intent(this.context, MainActivity::class.java)
            startActivity(intent)


        }

        return view
    }



}