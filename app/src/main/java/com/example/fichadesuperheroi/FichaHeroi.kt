package com.example.fichadesuperheroi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FichaHeroiActivity : AppCompatActivity() {

    private lateinit var imgAvatarFinal: ImageView
    private lateinit var txtCodinome: TextView
    private lateinit var txtAlinhamento: TextView
    private lateinit var txtPoderes: TextView
    private lateinit var btnEditar: Button
    private lateinit var rootLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ficha_heroi)

        imgAvatarFinal = findViewById(R.id.imgAvatarFinal)
        txtCodinome = findViewById(R.id.txtCodinome)
        txtAlinhamento = findViewById(R.id.txtAlinhamento)
        txtPoderes = findViewById(R.id.txtPoderes)
        btnEditar = findViewById(R.id.btnEditar)
        rootLayout = findViewById(R.id.rootLayoutFicha) 

        val codinome = intent.getStringExtra("codinome") ?: "Não definido"
        val alinhamento = intent.getStringExtra("alinhamento") ?: "Não definido"
        val poderes = intent.getStringArrayListExtra("poderes") ?: arrayListOf()
        val avatarResId = intent.getIntExtra("avatar", R.drawable.superman)

        txtCodinome.text = "Codinome: $codinome"
        txtAlinhamento.text = "Alinhamento: $alinhamento"
        txtPoderes.text = "Poderes: ${if (poderes.isNotEmpty()) poderes.joinToString(", ") else "Nenhum"}"
        imgAvatarFinal.setImageResource(avatarResId)

        
        when (alinhamento) {
            "Herói" -> rootLayout.setBackgroundColor(getColor(android.R.color.holo_blue_light))
            "Vilão" -> rootLayout.setBackgroundColor(getColor(android.R.color.holo_red_light))
            "Anti-herói" -> rootLayout.setBackgroundColor(getColor(android.R.color.darker_gray))
            else -> rootLayout.setBackgroundColor(getColor(android.R.color.white))
        }

        
        btnEditar.setOnClickListener {
            val intent = Intent(this, Criacaoheroi::class.java)

            intent.putExtra("codinome", codinome)
            intent.putExtra("alinhamento", alinhamento)
            intent.putStringArrayListExtra("poderes", ArrayList(poderes))
            intent.putExtra("avatar", avatarResId)

            startActivity(intent)
            finish()
        }
    }
}
