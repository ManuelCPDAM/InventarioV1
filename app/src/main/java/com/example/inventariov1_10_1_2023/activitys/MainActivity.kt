package com.example.inventariov1_10_1_2023.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imagen = binding.IvLogo
        registerForContextMenu(imagen)


        /**
         * Evento para el botón de logearse
         */
        binding.BtAcedder.setOnClickListener {
            loguear()
        }

        /**
         * Evento para el botón de registrarse
         */
        binding.BtRegistrarse.setOnClickListener {
            intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Función que nos logea en firebase firestore
     */
    private fun loguear() {
        // verificamos que no esten vacios los campos

        if (!binding.EtEmail.text.isNullOrEmpty() && !binding.EtPassword.text.isNullOrEmpty()) {
            //Iniciamos sesion en firebase con Auth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.EtEmail.text.toString(),
                binding.EtPassword.text.toString()
            )
                .addOnCompleteListener {
                    //Si ha tenido exito el loggin
                    if (it.isSuccessful) {
                        //Accedemos a la pantalla del menú
                        var intent = Intent(this, MenuPrincipalActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Campos incorrectos, intentelo de nuevo.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Algún campo está vacío", Toast.LENGTH_SHORT).show()
        }
    }
    // accedemos a menu contextual
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.menu_contextual,menu)

    }// cuando pulsan el item
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.pagWeb ->{
                Toast.makeText(this,"pagina web",Toast.LENGTH_SHORT).show()
                true
            }
            else-> super.onContextItemSelected(item)
        }

    }
}