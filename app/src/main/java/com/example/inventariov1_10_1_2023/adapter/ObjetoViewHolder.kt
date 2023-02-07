package com.example.inventariov1_10_1_2023.adapter

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.inventariov1_10_1_2023.dataclass.Objeto
import com.example.inventariov1_10_1_2023.databinding.ItemObjetosBinding

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore


class ObjetoViewHolder(view:View, c: Context):RecyclerView.ViewHolder(view) {

    val binding = ItemObjetosBinding.bind(view)
    var contexto = c

    var vista = view


    fun render(objetoModel: Objeto){
        binding.TvNombreObjeto.text ="Nombre: " +  objetoModel.nombre
        binding.TvEtiqueta.text = "Etiqueta: " +objetoModel.etiqueta
        binding.TvArmario.text ="Armario: " + objetoModel.armario
        binding.TvHabitacion.text = "Habitación: " +objetoModel.habitacion
        binding.TvDescripcion.text = "Descripción: " +objetoModel.descripcion
        // falta la foto




    }


    fun deleteItem(documentId: String) {


        val db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Objetos").document(documentId)
        docRef.delete()
            .addOnSuccessListener {
                Toast.makeText(contexto,"Eliminado!",Toast.LENGTH_SHORT).show()
                // aqui se elimina
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Error deleting document: ", e)
            }
    }



}

