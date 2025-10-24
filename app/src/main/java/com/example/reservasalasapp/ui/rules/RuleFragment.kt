package com.example.reservasalasapp.ui.rules

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reservasalasapp.R
import android.webkit.WebView
import android.webkit.WebViewClient
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RuleFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rule, container, false)

        webView = view.findViewById(R.id.webviewRule)
        webView.webViewClient = WebViewClient() // evita que se abra navegador externo
        webView.settings.javaScriptEnabled = true // habilitar JS si la web lo necesita

        // Cargar URL, por ejemplo
        webView.loadUrl("https://www.egafutura.com/glosario/reservas")

        return view
    }
}