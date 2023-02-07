package com.example.inventariov1_10_1_2023.dataclass

/**
 * Clase Objeto que guardará y gestionará los campos de los objetos
 */
data class Objeto(
    var etiqueta:String="", // etiqueta única o ID del objeto
    var habitacion:String="", // nombre de la habitación donde estará
    var armario:String="", // numero o tipo de armario en el que está
    var nombre: String="", // Nombre del objeto
    var descripcion: String="" // Descripción del objeto
)