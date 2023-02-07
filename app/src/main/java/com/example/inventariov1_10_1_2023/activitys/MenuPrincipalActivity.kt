package com.example.inventariov1_10_1_2023.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.inventariov1_10_1_2023.fragments.GestionBotonesMenu
import com.example.inventariov1_10_1_2023.R
import com.example.inventariov1_10_1_2023.databinding.ActivityMenuPrincipalBinding
import com.example.inventariov1_10_1_2023.fragments.ConsultarFragment
import com.example.inventariov1_10_1_2023.fragments.InsertarFragment
import com.example.inventariov1_10_1_2023.fragments.ModificarFragment
import com.google.firebase.auth.FirebaseAuth

/**
 * Clase que gestiona el menú
 */
class MenuPrincipalActivity : AppCompatActivity() {

    lateinit var binding: ActivityMenuPrincipalBinding
    lateinit var toogle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Menú
        toogle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction().apply{
            replace(R.id.fragment_container_view, GestionBotonesMenu())
            commit()
            true
        }

        /**
         * Navegación de los botones para todos los fragments
         */
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_Inicio ->{
                    startActivity(Intent(this, MenuPrincipalActivity::class.java))
                }
                R.id.nav_Consultar ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container_view, ConsultarFragment())
                        commit()
                        true
                    }

                }
                R.id.nav_Insertar ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container_view, InsertarFragment())
                        commit()
                        true
                    }
                }

                R.id.nav_Modificar ->{
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fragment_container_view, ModificarFragment())
                        commit()
                        true
                    }
                }

                R.id.nav_CerrarSesion -> logOut()

/*
                R.id.nav_Insertar -> startActivity(Intent(this, InsertarActivity::class.java))
                R.id.nav_Modificar -> startActivity(Intent(this, ModificarActivity::class.java))
                // R.id.nav_Ajustes -> startActivity(Intent(this,MainActivity::class.java))*/

            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }



    /**
     * Función que cierra sesión
     */
    private fun logOut() {
        FirebaseAuth.getInstance().signOut()

        // Y volvemos al main
        startActivity(Intent(this, MainActivity::class.java))
    }

    /**
     * Menú drawers
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            return true

        }
        return super.onOptionsItemSelected(item)
    }

}