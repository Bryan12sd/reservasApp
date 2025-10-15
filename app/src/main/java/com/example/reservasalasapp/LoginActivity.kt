package com.example.reservasalasapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private val PREFS_NAME = "UserPrefs"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_IS_LOGGED = "isLoggedIn"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegisterLogin = findViewById<Button>(R.id.btnRegisterLogin)

        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isLogged = prefs.getBoolean(KEY_IS_LOGGED, false)
        // Si ya está logueado, abrir MainActivity directamente
        if (isLogged) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        btnRegisterLogin.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val emailInput = etEmail.text.toString().trim()
            val passInput = etPassword.text.toString()

            if (emailInput.isEmpty() || passInput.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedEmail = prefs.getString(KEY_EMAIL, null)
            val savedPass = prefs.getString(KEY_PASSWORD, null)

            if (savedEmail == null || savedPass == null) {
                Toast.makeText(this, "No existe una cuenta registrada. Regístrate primero.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (emailInput == savedEmail && passInput == savedPass) {
                // Guardar estado de sesión
                prefs.edit().putBoolean(KEY_IS_LOGGED, true).apply()

                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
