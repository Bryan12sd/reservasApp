package com.example.reservasalasapp.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.reservasalasapp.data.ReservaDBHelper
import com.example.reservasalasapp.databinding.FragmentSlideshowBinding
import com.example.reservasalasapp.model.Reserva

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        binding.btnGuardar.setOnClickListener {
            val usuario = binding.etUsuario.text.toString().trim()
            val fecha = binding.etFecha.text.toString().trim()
            val sala = binding.etSala.text.toString().trim()

            if (usuario.isEmpty() || fecha.isEmpty() || sala.isEmpty()) {
                Toast.makeText(requireContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val reserva = Reserva(
                    id = 0, // El ID lo genera SQLite automáticamente
                    usuario = usuario,
                    fecha = fecha,
                    sala = sala,
                    estado = "Activa"
                )

                val dbHelper = ReservaDBHelper(requireContext())
                dbHelper.insertarReserva(reserva)

                Toast.makeText(requireContext(), "Reserva guardada correctamente", Toast.LENGTH_SHORT).show()

                // Limpiar campos
                binding.etUsuario.text.clear()
                binding.etFecha.text.clear()
                binding.etSala.text.clear()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
