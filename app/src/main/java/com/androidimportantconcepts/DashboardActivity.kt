package com.androidimportantconcepts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.androidimportantconcepts.databinding.ActivityDashboardBinding
import com.androidimportantconcepts.utils.SealedBaseResponse
import com.androidimportantconcepts.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlin.system.measureTimeMillis

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private val postViewModel: PostViewModel by viewModels()

    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        postViewModel.getPost()
        initObservers()

    }

    private fun initObservers() {
        postViewModel.responseLD.observe(this) {
            Log.d("TAG", "initObservers: ${it[0].body}")
        }

        lifecycleScope.launchWhenStarted {
            checkConnect().collect {
                val check = when (it) {
                    true -> "connected with internet"
                    false -> "No Internet"
                }
                binding.networkTV.text = check
            }
        }
    }
}