package com.example.reservasalasapp.ui.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.reservasalasapp.LoginActivity
import com.example.reservasalasapp.R

class GalleryFragment : Fragment() {

    private val PREFS_NAME = "UserPrefs"
    private val KEY_NAME = "name"
    private val KEY_PHONE = "phone"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_IS_LOGGED = "isLoggedIn"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)

        // SharedPreferences
        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // EditText y Botones
        val etName = root.findViewById<EditText>(R.id.etProfileName)
        val etPhone = root.findViewById<EditText>(R.id.etProfilePhone)
        val etEmail = root.findViewById<EditText>(R.id.etProfileEmail)
        val btnSave = root.findViewById<Button>(R.id.btnSaveProfile)
        val btnLogout = root.findViewById<Button>(R.id.btnLogout)

        // Cargar datos guardados
        etName.setText(prefs.getString(KEY_NAME, ""))
        etPhone.setText(prefs.getString(KEY_PHONE, ""))
        etEmail.setText(prefs.getString(KEY_EMAIL, ""))

        // Guardar cambios
        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val email = etEmail.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString(KEY_NAME, name)
                .putString(KEY_PHONE, phone)
                .putString(KEY_EMAIL, email)
                .apply()

            Toast.makeText(requireContext(), "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
        }

        // Cerrar sesión
        btnLogout.setOnClickListener {
            prefs.edit()
                .putBoolean(KEY_IS_LOGGED, false)
                .apply()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return root
    }
}
