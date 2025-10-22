package com.example.reservasalasapp.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.reservasalasapp.model.Reserva

class ReservaDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "reservas.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "reservas"

        private const val COLUMN_ID = "id"
        private const val COLUMN_SALA = "sala"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_USUARIO = "usuario"
        private const val COLUMN_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SALA TEXT,
                $COLUMN_FECHA TEXT,
                $COLUMN_USUARIO TEXT,
                $COLUMN_ESTADO TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // ✅ Insertar una reserva
    fun insertarReserva(reserva: Reserva) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_SALA, reserva.sala)
            put(COLUMN_FECHA, reserva.fecha)
            put(COLUMN_USUARIO, reserva.usuario)
            put(COLUMN_ESTADO, reserva.estado)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // ✅ Obtener todas las reservas
    fun obtenerReservas(): List<Reserva> {
        val lista = mutableListOf<Reserva>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val reserva = Reserva(
                    sala = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SALA)),
                    fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA)),
                    usuario = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USUARIO)),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ESTADO))
                )
                lista.add(reserva)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return lista
    }

    // ✅ Actualizar estado de una reserva
    fun actualizarEstado(fecha: String, sala: String, nuevoEstado: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ESTADO, nuevoEstado)
        }
        db.update(TABLE_NAME, values, "$COLUMN_FECHA=? AND $COLUMN_SALA=?", arrayOf(fecha, sala))
        db.close()
    }

    // ✅ Eliminar todas las reservas
    fun limpiarReservas() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}
