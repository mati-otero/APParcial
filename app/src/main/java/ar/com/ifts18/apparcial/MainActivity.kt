package ar.com.ifts18.apparcial

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonTC = findViewById<Button>(R.id.btnIniciar)
        val caja = findViewById<CheckBox>(R.id.caja)
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)

        // evento, al hacer click abre actvity TyC
        caja.setOnClickListener{
            mostrarToast("Ingresaste a terminos y condiciones")
            irATerminosYCondiciones()
        }

        // Llamo a SharedPreferences y extraigo "terminosAceptados"
        val misPreferencias = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val terminosAceptados = misPreferencias.getBoolean("terminosAceptados", false)
        // Dependiendo el boton presionado se cambia el estado del CheckBox
        caja.isChecked = terminosAceptados

        // en este archivo guaddate la key estaLogeado en false y ademas guardala en yaSeLogueo
        val yaSeLogueo = misPreferencias.getBoolean("estaLogeado", false)

        if(yaSeLogueo){
            // Si ya se logeo hizo test inversor => HOME activity
            mostrarToast("Ya estas logeado")
            irAHome()
        }



        // logica de validacion
        botonTC.setOnClickListener{
            // casteo datos
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val correo = etCorreo.text.toString()

            // Verifico que haya llenado todos los campos
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty()) {

                mostrarToast("Por favor, completa todos los campos.")

            } else if (!caja.isChecked) {
                // Verifico que el checkbox de términos y condiciones está marcado
                mostrarToast("Debes aceptar los términos y condiciones.")
            } else {

                // si paso todas las validaciones
                // 1) guardo info de usuario en shared preference
                // si las credenciales son correctas => almaceno info en sharedPreferences
                misPreferencias.edit().apply {
                    // actualizo estado de logeo y guardo solo credencial username
                    putString("username", nombre)
                    putString("userlastname", apellido)
                    putString("useremail", correo)
                    apply()
                }
                // 2) envio a test inversor
                irATestInversor()
            }

        }

    }
    private fun irATerminosYCondiciones(){
        val intent = Intent(this, TyCActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }

    private fun irATestInversor(){
        val intent = Intent(this, TestInversorActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }

    private fun irAHome(){
        val intent = Intent(this, HomeActivity::class.java).apply {  }
        startActivity(intent)
        finish()
    }
    
    private fun mostrarToast(mensajeToast: String){
        Toast.makeText(this, mensajeToast, Toast.LENGTH_LONG).show()
    }
}