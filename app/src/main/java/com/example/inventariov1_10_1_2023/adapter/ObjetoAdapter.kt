package com.example.inventariov1_10_1_2023.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.inventariov1_10_1_2023.dataclass.Objeto
import com.example.inventariov1_10_1_2023.R

class ObjetoAdapter(private var listaObjetos:List<Objeto>) : RecyclerView.Adapter<ObjetoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjetoViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.item_objetos, parent,false)
        return ObjetoViewHolder(layoutInflater,parent.context)
    }

    override fun onBindViewHolder(holder: ObjetoViewHolder, position: Int) {
        var item = listaObjetos[position]
        holder.render(item)
        var size = listaObjetos.size

        holder.binding.papelerita.setOnClickListener {

            val builder = AlertDialog.Builder(holder.contexto)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Esta seguro de que desea eliminar el objeto?")

            builder.setPositiveButton(" Estoy seguro") { dialog, which ->
                // Acción a realizar cuando se presiona el botón "Sí"
                holder.deleteItem(item.etiqueta)
                listaObjetos = listaObjetos.filterNot { it.etiqueta == item.etiqueta }
                notifyDataSetChanged()
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, size)

            }

            builder.setNegativeButton("Cancelar, ") { dialog, which ->
                // Acción a realizar cuando se presiona el botón "Cancelar"
                Toast.makeText(holder.contexto,"Cancelado",Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    override fun getItemCount(): Int = listaObjetos.size
}