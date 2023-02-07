package com.example.inventariov1_10_1_2023.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.databinding.FragmentInsertarBinding
import com.google.firebase.firestore.FirebaseFirestore

class InsertarFragment : Fragment(R.layout.fragment_insertar) {

    private var _binding: FragmentInsertarBinding? = null
    private val binding get() = _binding!!
    lateinit var imagen: ImageButton
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        uri ->
        if(uri!=null){
            imagen.setImageURI(uri)
            binding.ImagenMostrar.visibility = View.VISIBLE

        }
        else{
            tostada("no se ha seleccionado ningun campo")
        }
    }

    /**
     * Gestión fotos
     */
    val pickFoto = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val image = it.data?.extras?.get("data") as Bitmap
        binding.ImagenMostrar.setImageBitmap(image)
        binding.ImagenMostrar.visibility = View.VISIBLE

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container:ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInsertarBinding.inflate(inflater,container,false)
        var view = binding.root

        val db = FirebaseFirestore.getInstance()

        /**
         * Botón añadir
         */
        binding.BtAnadir.setOnClickListener {
            if(binding.EtArmario.text.isNotEmpty()&&binding.EtDescripcion.text.isNotEmpty()&&binding.EtEtiqueta.text.isNotEmpty()
                &&binding.EtHabitacion.text.isNotEmpty()&&binding.EtNombreObjeto.text.isNotEmpty()){

                db.collection("Objetos").document(binding.EtEtiqueta.text.toString()).set(mapOf(
                    "nombre" to binding.EtNombreObjeto.text.toString(),
                    "habitacion" to binding.EtHabitacion.text.toString(),
                    "armario" to binding.EtArmario.text.toString(),
                    "descripcion" to binding.EtDescripcion.text.toString(),
                    "etiqueta" to binding.EtEtiqueta.text.toString()
                )).addOnSuccessListener {
                    tostada("Registro insertado con exito. Puede insertar mas si lo desea.")
                    binding.EtEtiqueta.text.clear()
                    binding.EtNombreObjeto.text.clear()
                    binding.EtHabitacion.text.clear()
                    binding.EtArmario.text.clear()
                    binding.EtDescripcion.text.clear()

                }
                    .addOnFailureListener{
                        tostada("Error en la insercción de un nuevo registro")
                    }

            }
            else{
                tostada("No puede dejar ningún campo vacío.")
            }
        }


        /**
         * Botón subir imagen
         */
        imagen = binding.ImagenMostrar
        binding.IvAdjuntar.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        /**x
         * botón hacer foto
         */
        binding.IvTomarFoto.setOnClickListener {
            pickFoto.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        // Inflate the layout for this fragment
        return view
    }

    /**
     * Método gérico que hace un toast
     */
    private fun tostada(mensaje:String){
        Toast.makeText(getActivity(),mensaje, Toast.LENGTH_SHORT).show()
    }

}