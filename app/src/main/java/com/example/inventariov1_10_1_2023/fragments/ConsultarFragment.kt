package com.example.inventariov1_10_1_2023.fragments

import android.os.Bundle
import android.view.*
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventariov1_10_1_2023.dataclass.Objeto
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.adapter.ObjetoAdapter
import com.example.inventariov1_10_1_2023.databinding.FragmentConsultarBinding
import com.google.firebase.firestore.FirebaseFirestore


class ConsultarFragment : Fragment() {

    private var binding: FragmentConsultarBinding? = null

    var recycler: RecyclerView? = null
    lateinit var adapter: ObjetoAdapter
    val db = FirebaseFirestore.getInstance()


    /**
     * En este fragment estará el recycler view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConsultarBinding.inflate(inflater, container, false)


        var view = binding?.root


        val objetosList = ArrayList<Objeto>()
        adapter= ObjetoAdapter(objetosList)
        //cargamos el recycler y lo mostramos en un linear laoçyout
        recycler = view?.findViewById(R.id.objetosRecycler)
        recycler?.layoutManager = LinearLayoutManager(context)
        recycler?.adapter = adapter

        db.collection("Objetos").get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val objeto = document.toObject(Objeto::class.java)
                    objetosList.add(objeto)
                }
                // notificamos si cambian
                adapter.notifyDataSetChanged()

            }


        return view
    }





}
