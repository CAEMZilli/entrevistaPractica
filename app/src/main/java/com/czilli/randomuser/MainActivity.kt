package com.czilli.randomuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.czilli.randomuser.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Realiza la petición inicial
        getRandomUser()

        // Configura el botón para actualizar el usuario
        binding.buttonUpdate.setOnClickListener {
            getRandomUser()
        }
    }

    private fun getRandomUser() {
        // Muestra el loader
        binding.progressBar.visibility = View.VISIBLE

        // Realiza la petición a la API
        CoroutineScope(Dispatchers.IO).launch {
            val response = ApiClient.apiService.getRandomUser()

            // Oculta el loader y actualiza la UI
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val user = response.body()?.results?.firstOrNull()
                    updateUserUI(user)
                } else {
                    Toast.makeText(this@MainActivity, "Error: ${response.errorBody()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUserUI(user: User?) {
        user?.let {
            binding.textName.text = "${it.name.title} ${it.name.first} ${it.name.last}"
            binding.textEmail.text = it.email
            binding.textDob.text = it.dob.date
            binding.textAddress.text = "${it.location.street.number} ${it.location.street.name}, ${it.location.city}, ${it.location.state}, ${it.location.country}, ${it.location.postcode}"
            binding.textPhone.text = it.phone
            binding.textPassword.text = it.login.password

            // Carga la imagen con Glide
            Glide.with(this)
                .load(it.picture.large)
                .into(binding.imageProfile)
        }
    }
}

