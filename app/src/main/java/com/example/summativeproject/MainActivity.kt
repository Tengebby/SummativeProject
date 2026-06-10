package com.example.summativeproject


import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


/**
 * MainActivity - The main screen of Campsite Commander.
 * Allows users to add camping gear items with category, quantity and notes.
 * Uses parallel arrays to store all item data.
 * Calculates total items packed using a loop.
 * Navigates to DetailActivity for the full gear checklist.
 */
class MainActivity : AppCompatActivity() {

    private val gearItems = arrayListOf(
        "Tent",
        "Sleeping Bag",
        "Stove",
        "First Aid Kit"
    )

    private val categories = arrayListOf(
        "Shelter",
        "Shelter",
        "Cooking",
        "First Aid"
    )

    private val quantities = arrayListOf(
        1,
        2,
        1,
        1
    )

    private val notes = arrayListOf(
        "4 Person Tent",
        "Winter Rated",
        "Gas Stove",
        "Emergency Use"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtInfomation = findViewById<TextView>(R.id.txtInfomation)
        val edtItem = findViewById<EditText>(R.id.edtItem)
        val edtCategory = findViewById<EditText>(R.id.edtCategory)
        val edtQuantity = findViewById<EditText>(R.id.edtAmount)
        val edtNotes = findViewById<EditText>(R.id.edtNotes)
        val btnAddGear = findViewById<Button>(R.id.btnAddGear)

        updateTotal(txtInfomation)

        btnAddGear.setOnClickListener {

            if (
                edtItem.text.isEmpty() ||
                edtCategory.text.isEmpty() ||
                edtQuantity.text.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Please complete all required fields",
                    Toast.LENGTH_LONG
                ).show()

            } else {

                gearItems.add(edtItem.text.toString())
                categories.add(edtCategory.text.toString())
                quantities.add(edtQuantity.text.toString().toInt())
                notes.add(edtNotes.text.toString())

                updateTotal(txtInfomation)

                Toast.makeText(
                    this,
                    "Gear Added Successfully",
                    Toast.LENGTH_SHORT
                ).show()

                // Clear input fields
                edtItem.text.clear()
                edtCategory.text.clear()
                edtQuantity.text.clear()
                edtNotes.text.clear()

                // Move to DetailActivity
                val intent = Intent(this, MainActivity::class.java)

                intent.putStringArrayListExtra(
                    "items",
                    ArrayList(gearItems)
                )

                intent.putStringArrayListExtra(
                    "categories",
                    ArrayList(categories)
                )

                intent.putIntegerArrayListExtra(
                    "quantities",
                    ArrayList(quantities)
                )

                intent.putStringArrayListExtra(
                    "notes",
                    ArrayList(notes)
                )

                startActivity(intent)
            }
        }
    }

    private fun updateTotal(textView: TextView) {

        var total = 0

        for (qty in quantities) {
            total += qty
        }

        textView.text = "Total Items Packed: $total"
    }
}