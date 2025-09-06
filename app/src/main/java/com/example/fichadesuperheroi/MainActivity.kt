package com.example.fichadesuperheroi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var edtCodinome: EditText
    private lateinit var btnCriarPerfil: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtCodinome = findViewById(R.id.edtCodinome)
        btnCriarPerfil = findViewById(R.id.btnCriarPerfil)
        prefs = getSharedPreferences("meuArquivo", MODE_PRIVATE)

        
        val heroiJson = prefs.getString("ultimoHeroi", null)
        if (heroiJson != null) {
            val gson = Gson()
            val heroi = gson.fromJson(heroiJson, Heroi::class.java)
            edtCodinome.setText(heroi.codinome)
        }

        btnCriarPerfil.setOnClickListener {
            val nome = edtCodinome.text.toString()
            if (nome.isEmpty()) {
                Toast.makeText(this, "Digite um nome", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, Criacaoheroi::class.java)
                intent.putExtra("codinome", nome)
                startActivity(intent)
            }
        }
    }
}
