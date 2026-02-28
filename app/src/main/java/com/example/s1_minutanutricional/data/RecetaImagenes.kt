package com.example.s1_minutanutricional.data
object RecetaImagenes {

    private val imagenesPorDia = mapOf(
        "Lunes"     to "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=800",
        "Martes"    to "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?w=800",
        "Miércoles" to "https://images.unsplash.com/photo-1547592180-85f173990554?w=800",
        "Jueves"    to "https://images.unsplash.com/photo-1482049016688-2d3e1b311543?w=800",
        "Viernes"   to "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800",
        "Sábado"    to "https://images.unsplash.com/photo-1574484284002-952d92456975?w=800",
        "Domingo"   to "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800"
    )

    fun obtenerUrl(dia: String): String? = imagenesPorDia[dia]
}
