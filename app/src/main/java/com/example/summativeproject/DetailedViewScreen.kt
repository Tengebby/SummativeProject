package com.example.summativeproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailedViewScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_view_screen)

        val txtInfomation = findViewById<TextView>(R.id.txtInfomation)
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)

        val items = intent.getStringArrayListExtra("items")
        val categories = intent.getStringArrayListExtra("categories")
        val quantities = intent.getIntegerArrayListExtra("quantities")
        val notes = intent.getStringArrayListExtra("notes")

        var output = ""

        if (items != null) {

            for (i in items.indices) {

                output +=
                    "Item: ${items[i]}\n" +
                            "Category: ${categories?.get(i)}\n" +
                            "Quantity: ${quantities?.get(i)}\n" +
                            "Notes: ${notes?.get(i)}\n\n"
            }
        }

        txtInfomation.text = output

        btnPrevious.setOnClickListener {
            finish()
        }
    }
}