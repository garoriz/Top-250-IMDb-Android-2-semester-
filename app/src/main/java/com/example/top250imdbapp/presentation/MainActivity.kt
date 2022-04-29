package com.example.top250imdbapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.top250imdbapp.R
import com.example.top250imdbapp.presentation.top250.Top250Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, Top250Fragment())
            .commit()
    }
}
