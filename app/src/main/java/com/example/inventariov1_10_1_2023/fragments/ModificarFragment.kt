package com.example.inventariov1_10_1_2023.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.databinding.FragmentModificarBinding
import com.google.firebase.firestore.FirebaseFirestore


class ModificarFragment : Fragment(R.layout.fragment_modificar) {

    private var _binding: FragmentModificarBinding?=null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModificarBinding.inflate(inflater,container,false)

        val view = binding.root

        // Database lista
        val db = FirebaseFirestore.getInstance()

        /**
         * Evento sobre el botón buscar objeto para despues modificarlo:
         * -> Vuelve visibles el resto de campos
         * -> los rellena con los datos para facilitar la modificación
         */

        binding.BtComprobarObjeto.setOnClickListener {

            if(binding.etEtiqueta.text.isNotEmpty()){

                db.collection("Objetos").document(binding.etEtiqueta.text.toString()).get().addOnSuccessListener {
                    MostrarCampos()

                    binding.etNombreObjeto.setText(it.get("nombre") as String?)
                    binding.etHabitacion.setText(it.get("habitacion") as String?)
                    binding.etDescripcion.setText(it.get("descripcion") as String?)
                    binding.etArmario.setText(it.get("armario") as String?)

                }.addOnFailureListener{
                    tostada("No hay ningun objeto con esa etiqueta")
                }



            }
            else{
                tostada("Introduzca la etiqueta por favor")
            }


        }


        /**
         * Evento click en Modificar
         */
        binding.btModificar.setOnClickListener {
            if( binding.etNombreObjeto.text.isNotEmpty()&& binding.etHabitacion.text.isNotEmpty()
                && binding.etDescripcion.text.isNotEmpty() && binding.etEtiqueta.text.isNotEmpty()&&
                binding.etArmario.text.isNotEmpty()){


                // si no estan vacios procedemos a hacer el update
                db.collection("Objetos").document(binding.etEtiqueta.text.toString()).update(
                    "nombre",binding.etNombreObjeto.text.toString(),
                    "habitacion",binding.etHabitacion.text.toString(),
                    "armario", binding.etArmario.text.toString(),
                    "descripcion",binding.etDescripcion.text.toString()

                ).addOnSuccessListener {
                    tostada("Datos actualizados!")
                    limpiar()
                }
                    .addOnFailureListener{
                        tostada("Fallo al actualizar. Ha ocurrido un error.")
                    }

            }
            else{
                tostada("No puedes dejar ninguna casilla vacia")
            }
        }
        // Inflate the layout for this fragment
        return view
    }

    private fun MostrarCampos(){
        binding.btModificar.visibility = View.VISIBLE
        binding.etArmario.visibility = View.VISIBLE
        binding.etDescripcion.visibility = View.VISIBLE
        binding.etHabitacion.visibility = View.VISIBLE
        binding.etNombreObjeto.visibility = View.VISIBLE

    }
    private fun tostada(mensaje:String){
        Toast.makeText(getActivity(),mensaje, Toast.LENGTH_SHORT).show()
    }
    private fun limpiar(){
        binding.etEtiqueta.setText("")
        binding.etArmario.setText("")
        binding.etDescripcion.setText("")
        binding.etHabitacion.setText("")
        binding.etNombreObjeto.setText("")
    }

}