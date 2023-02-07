package com.example.inventariov1_10_1_2023.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.inventariov1_10_1_2023.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegistroBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        // variables para la gestión de comprobaciones
        var escontraseniaValida=false
        var correoValido = false
        var puedeRegistrar = false

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * Evento de dejar el focus en el campo contraseña
         */
        binding.EtPass.setOnFocusChangeListener{view, hasFocus ->
            if (!hasFocus) {
                escontraseniaValida = isPasswordValid(binding.EtPass.text.toString())
                // si la contraseña no es valida, lo avisamos cn un toast y ponems el campo en rojo
                if(escontraseniaValida==false){
                    binding.errorPass.visibility= View.VISIBLE
                }
                else{
                    binding.errorPass.visibility = View.INVISIBLE
                }
            }


        }
        /**
         * Evento del botón registrar
         */
        binding.BtRegistrarse.setOnClickListener {
            // 1º que no haya nada vacio
            if(!binding.EtEmailRegistro.text.isNullOrEmpty()&&!binding.EtApellidos.text.isNullOrEmpty()
                && !binding.EtNombre.text.isNullOrEmpty()&&!binding.EtPass.text.isNullOrEmpty()){
                // 2º comprobamos que sea un correo valido
                var correoValido:Boolean
                correoValido = isEmailValid(binding.EtEmailRegistro.text.toString())
                if(correoValido && escontraseniaValida){
                    Registrar()
                }
                else{
                    Toast.makeText(this,"Correo o contraseña incorrectos    .",Toast.LENGTH_SHORT).show()
                    binding.EtEmailRegistro.text.clear()
                    //binding.EtEmailRegistro.Focus()
                }

            }
            else{
                Toast.makeText(this,"No puedes dejar ningun campo vacio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * método que comprueba si es un email valido:
     * - Debe ser un email del centro iesLuiscarrillo
     * - Para ello debe tener despues del único @ iesluiscarrillodesotomayor.org, o el correo no será valido
     */
    fun isEmailValid(email: String): Boolean {
        val pattern = Regex("^[^@]+@luiscarrillodesotomayor.org\$")
        return pattern.matches(email)
    }
    /**
     * Función que comprueba si la contraseña cumple con lso requisitos mínimos de seguridad
     */
    fun isPasswordValid(password: String): Boolean {
        val pattern = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+\$).{8,}")
        return pattern.matches(password)
    }
    /**
     * Evento del boton registrar
     * ->Registra al usuario en la base de datos Firebase
     */
    private fun Registrar() {
        val db = FirebaseFirestore.getInstance()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            binding.EtEmailRegistro.text.toString(), binding.EtPass.text.toString()
        ).addOnCompleteListener{
            if(it.isSuccessful){
                db.collection("usuarios").document(binding.EtEmailRegistro.text.toString())
                    .set(mapOf(
                        "nombre" to binding.EtNombre.text.toString(),
                        "apellidos" to binding.EtApellidos.text.toString()
                    ))

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Error en el registro del nuevo usuario", Toast.LENGTH_SHORT).show()
            }

        }
    }
}