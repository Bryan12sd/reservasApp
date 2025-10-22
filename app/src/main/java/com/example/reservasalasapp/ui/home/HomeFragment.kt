package com.example.reservasalasapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import com.example.reservasalasapp.R
import com.example.reservasalasapp.data.ReservaDBHelper
import com.example.reservasalasapp.model.Reserva

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val contenedor = root.findViewById<LinearLayout>(R.id.containerReservas)
        val textoNoReservas = root.findViewById<TextView>(R.id.textNoReservas)
        val dbHelper = ReservaDBHelper(requireContext())

        // 🔹 Limpiamos el contenedor por si se recarga el fragmento
        contenedor.removeAllViews()

        // 🔹 Obtener todas las reservas desde SQLite
        val reservas = dbHelper.obtenerReservas()

        if (reservas.isEmpty()) {
            textoNoReservas.visibility = View.VISIBLE
            contenedor.visibility = View.GONE
        } else {
            textoNoReservas.visibility = View.GONE
            contenedor.visibility = View.VISIBLE

            reservas.forEach { reserva ->
                val cardView = layoutInflater.inflate(R.layout.item_reserva_card, contenedor, false)
                val tvInfo = cardView.findViewById<TextView>(R.id.tvReservaInfo)
                val spinnerEstado = cardView.findViewById<Spinner>(R.id.spinnerEstado)
                val card = cardView.findViewById<androidx.cardview.widget.CardView>(R.id.cardReserva)

                // Información de la reserva
                tvInfo.text = "Sala: ${reserva.sala}\nFecha: ${reserva.fecha}\nUsuario: ${reserva.usuario}"

                // Lista de estados
                val estados = listOf("Pendiente", "Confirmada", "Cancelada")
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, estados)
                spinnerEstado.adapter = adapter
                spinnerEstado.setSelection(estados.indexOf(reserva.estado))

                // Función para aplicar color según estado
                fun aplicarColor(estado: String) {
                    val colorRes = when (estado) {
                        "Confirmada" -> R.color.confirmation   // Verde
                        "Pendiente" -> R.color.warning         // Amarillo
                        "Cancelada" -> R.color.error           // Rojo
                        else -> android.R.color.white
                    }
                    card.setCardBackgroundColor(ContextCompat.getColor(requireContext(), colorRes))
                }

                aplicarColor(reserva.estado)

                // Evento cuando el usuario cambia el estado
                spinnerEstado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                        val nuevoEstado = estados[position]
                        if (nuevoEstado != reserva.estado) {
                            dbHelper.actualizarEstado(reserva.fecha, reserva.sala, nuevoEstado)
                            aplicarColor(nuevoEstado)
                            Toast.makeText(requireContext(), "Estado actualizado a $nuevoEstado", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

                contenedor.addView(cardView)
            }
        }

        return root
    }
}
