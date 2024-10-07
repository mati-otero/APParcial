package ar.com.ifts18.apparcial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class HomeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Para validar datos ingresados
        val etMonto = findViewById<EditText>(R.id.et1)
        val etPlazoEnDias = findViewById<EditText>(R.id.et2)
       // val TipoDeInversion = findViewById<EditText>(R.id.et4)
        val monto:Int? = etMonto.text.toString().toIntOrNull()
        val PlazoEndias:Int? = etPlazoEnDias.text.toString().toIntOrNull()
       // val tipoDeInversion = TipoDeInversion.text.toString()

        val tvNumero2 = findViewById<TextView>(R.id.tvNumero2)

        val misPreferencias = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val hNombre = misPreferencias.getString("username", null)
        val hUsername = misPreferencias.getString("userlastname", null)

        tvNumero2.text = "¡bienvenido " + hNombre + "!"


       //para el spinner
       /* val bancos = arrayOf("Ingrese un banco", "BNA", "STR", "ICBC")
        val tasasInteres = mapOf("BNA" to 43.0, "STR" to 42.0, "ICBC" to 41.0)
        val bancoSpinner: Spinner = findViewById(R.id.banco_spinner)

        // adaptador para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, bancos)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bancoSpinner.adapter = adapter */

        val botonHistorial = findViewById<Button>(R.id.btVerHistorial)
        val botonSalir = findViewById<Button>(R.id.btSalir)
        val botonCalcularRendimiento = findViewById<Button>(R.id.btCalcularRendimiento)

        val hTasaInteres = misPreferencias.getString("tasaInteres", "NO")
       // val fullinfo = "$hNombre $hUsername $hTasaInteres"

        val spinnerBanco = findViewById<Spinner>(R.id.banco_spinner)
        val spinnerInversion = findViewById<Spinner>(R.id.inversion_spinner)

        //CREAMOS EL ARRAY DEL ADAPTER del BANCO
        ArrayAdapter.createFromResource(
            this,
            R.array.entradaBanco,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerBanco.adapter = adapter
        }

        //CREAMOS EL ARRAY DEL ADAPTER de la INVERSION
        ArrayAdapter.createFromResource(
            this,
            R.array.entradaInversion,
            android.R.layout.simple_spinner_item
        ).also { adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            spinnerInversion.adapter = adapter
        }

        botonCalcularRendimiento.setOnClickListener{

            mostrarToast("$monto")
            val entradaDeBanco = spinnerBanco.selectedItem.toString()
            val entradaDeInversion = spinnerInversion.selectedItem.toString()
            if(monto==0){
                mostrarToast("El valor no puede ser cero")
            }else if(PlazoEndias==0){
                mostrarToast("El plazo no puede ser cero")
            }else {

                mostrarToast("$monto para $PlazoEndias de $entradaDeBanco para $entradaDeInversion")
                //irARendimiento()
                // mostrarToast(fullinfo.toString())
            }
        }



        //Guardar en preferencias (para pasar a Jose)
        val misPreferenciasARendimiento = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        misPreferenciasARendimiento.edit().apply {

            putString("montoInvertir", "0")
            putString("plazoDias", "0")
            putString("banco", "banco")
            putString("tipoInversion", "inversion")
            putString("tasaInteres", "tasa")
            apply()

        }
        botonHistorial.setOnClickListener{ irAHistorial() }
        botonSalir.setOnClickListener{ irAInicio() }


        // aqui va el codigo
    }
    fun irAHistorial(){
        val intent = Intent(this, HistorialActivity::class.java)
        startActivity(intent) }

    fun irAInicio(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent) }

    fun irARendimiento(){
        val intent = Intent(this, RendimientoActivity::class.java)
        startActivity(intent)
    }
    private fun mostrarToast(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}