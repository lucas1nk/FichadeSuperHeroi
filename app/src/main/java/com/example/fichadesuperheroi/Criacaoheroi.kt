package com.example.fichadesuperheroi

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson

class Criacaoheroi : AppCompatActivity() {

    private lateinit var txtBoasVindas: TextView
    private lateinit var rgAlinhamento: RadioGroup
    private lateinit var cbVoo: CheckBox
    private lateinit var cbForca: CheckBox
    private lateinit var cbTelepatia: CheckBox
    private lateinit var cbRajadas: CheckBox
    private lateinit var cbVelocidade: CheckBox
    private lateinit var imgAvatar: ImageView
    private lateinit var btnGerarFicha: Button

    private val avatares = arrayOf(
        R.drawable.superman,
        R.drawable.flash,
        R.drawable.aquaman,
        R.drawable.coringa,
        R.drawable.batman,
        R.drawable.harleyqueen
    )
    private var avatarIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_criacaoheroi)

        txtBoasVindas = findViewById(R.id.txtBoasVindas)
        rgAlinhamento = findViewById(R.id.rgAlinhamento)
        cbVoo = findViewById(R.id.cbVoo)
        cbForca = findViewById(R.id.cbForca)
        cbTelepatia = findViewById(R.id.cbTelepatia)
        cbRajadas = findViewById(R.id.cbRajadas)
        cbVelocidade = findViewById(R.id.cbVelocidade)
        imgAvatar = findViewById(R.id.imgAvatar)
        btnGerarFicha = findViewById(R.id.btnGerarFicha)

        val prefs = getSharedPreferences("meuArquivo", MODE_PRIVATE)
        val gson = Gson()

        
        val heroiJson = prefs.getString("ultimoHeroi", null)
        val heroi = if (heroiJson != null) gson.fromJson(heroiJson, Heroi::class.java) else null
        
        val codinome = intent.getStringExtra("codinome") ?: heroi?.codinome
        txtBoasVindas.text = "Personalize o perfil de: $codinome"

        
        val alinhamentoSalvo = heroi?.alinhamento
        alinhamentoSalvo?.let {
            when(it) {
                "Herói" -> rgAlinhamento.check(R.id.rbHeroi)
                "Vilão" -> rgAlinhamento.check(R.id.rbVilao)
                "Anti-herói" -> rgAlinhamento.check(R.id.rbAntiHeroi)
            }
        }

        val poderesSalvos = heroi?.poderes ?: emptyList()
        cbVoo.isChecked = poderesSalvos.contains("Voo")
        cbForca.isChecked = poderesSalvos.contains("Super-força")
        cbTelepatia.isChecked = poderesSalvos.contains("Telepatia")
        cbRajadas.isChecked = poderesSalvos.contains("Rajadas de Energia")
        cbVelocidade.isChecked = poderesSalvos.contains("Super-velocidade")

       
        avatarIndex = if (heroi != null) avatares.indexOf(heroi.avatarResId) else 0
        if (avatarIndex == -1) avatarIndex = 0
        imgAvatar.setImageResource(avatares[avatarIndex])

        imgAvatar.setOnClickListener {
            avatarIndex = (avatarIndex + 1) % avatares.size
            imgAvatar.setImageResource(avatares[avatarIndex])
        }

        btnGerarFicha.setOnClickListener {
            val selecionadoId = rgAlinhamento.checkedRadioButtonId
            val alinhamentoEscolhido = if (selecionadoId != -1)
                findViewById<RadioButton>(selecionadoId).text.toString()
            else
                "Não definido"

            val listaPoderes = mutableListOf<String>()
            if (cbVoo.isChecked) listaPoderes.add("Voo")
            if (cbForca.isChecked) listaPoderes.add("Super-força")
            if (cbTelepatia.isChecked) listaPoderes.add("Telepatia")
            if (cbRajadas.isChecked) listaPoderes.add("Rajadas de Energia")
            if (cbVelocidade.isChecked) listaPoderes.add("Super-velocidade")

            val avatarAtual = avatares[avatarIndex]

            val novoHeroi = Heroi(codinome ?: "Não definido", alinhamentoEscolhido, listaPoderes, avatarAtual)
            prefs.edit().putString("ultimoHeroi", gson.toJson(novoHeroi)).apply()

            val intent = Intent(this, FichaHeroiActivity::class.java)
            intent.putExtra("codinome", codinome)
            intent.putExtra("alinhamento", alinhamentoEscolhido)
            intent.putStringArrayListExtra("poderes", ArrayList(listaPoderes))
            intent.putExtra("avatar", avatarAtual)
            startActivity(intent)
        }
    }
}
