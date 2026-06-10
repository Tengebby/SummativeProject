package com.camspitecommander


import android.content.Intent
import android.os.Bundle
import android.util.Log
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


    private val TAG = "MainActivity"


    // Maximum number of items the packing list supports
    private val MAX_ITEMS = 20


    // Parallel arrays to store gear data
    private val itemNames = Array(MAX_ITEMS) { "" }
    private val itemCategories = Array(MAX_ITEMS) { "" }
    private val itemQuantities = IntArray(MAX_ITEMS)
    private val itemNotes = Array(MAX_ITEMS) { "" }


    // Tracks how many items have been added so far
    private var itemCount = 0


    // Pre-loaded sample data for each category
    private val sampleNames = arrayOf("Tent", "Sleeping Bag", "Camp Stove", "First Aid Kit", "Rope")
    private val sampleCategories = arrayOf("Shelter", "Shelter", "Cooking", "First Aid", "Shelter")
    private val sampleQuantities = intArrayOf(1, 2, 1, 1, 1)
    private val sampleNotes = arrayOf("4-person dome tent", "Rated -5°C", "Gas canister included", "Check expiry dates", "10m paracord")


    // UI references
    private lateinit var etItemName: EditText
    private lateinit var etQuantity: EditText
    private lateinit var etNotes: EditText
    private lateinit var spinnerCategory: Spinner
    private lateinit var tvTotalPacked: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Log.d(TAG, "MainActivity started")


        // Bind UI components
        etItemName = findViewById(R.id.etItemName)
        etQuantity = findViewById(R.id.etQuantity)
        etNotes = findViewById(R.id.etNotes)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        tvTotalPacked = findViewById(R.id.tvTotalPacked)


        val btnAddGear: Button = findViewById(R.id.btnAddGear)
        val btnViewDetails: Button = findViewById(R.id.btnViewDetails)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnExit: Button = findViewById(R.id.btnExit)


        // Set up category spinner with predefined options
        val categories = arrayOf("Shelter", "Cooking", "First Aid", "Clothing", "Navigation", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter


        // Load sample data into arrays on startup
        loadSampleData()
        updateTotalDisplay()


        // Add a new gear item
        btnAddGear.setOnClickListener {
            Log.d(TAG, "Add Gear button clicked")
            addGearItem()
        }


        // Navigate to the detailed checklist view
        btnViewDetails.setOnClickListener {
            Log.d(TAG, "View Details button clicked")
            navigateToDetails()
        }


        // Clear all data and reset the list
        btnClear.setOnClickListener {
            Log.d(TAG, "Clear button clicked")
            clearAllData()
        }


        // Exit the app
        btnExit.setOnClickListener {
            finishAffinity()
        }
    }


    /**
     * Pre-loads sample gear items into the parallel arrays.
     * Gives the user a starting point for their packing list.
     */
    private fun loadSampleData() {
        for (i in sampleNames.indices) {
            itemNames[itemCount] = sampleNames[i]
            itemCategories[itemCount] = sampleCategories[i]
            itemQuantities[itemCount] = sampleQuantities[i]
            itemNotes[itemCount] = sampleNotes[i]
            itemCount++
        }
        Log.d(TAG, "Sample data loaded: $itemCount items")
    }


    /**
     * Reads user input, validates it, and adds a new gear item
     * to all four parallel arrays.
     */
    private fun addGearItem() {
        // Error handling: check if the list is full
        if (itemCount >= MAX_ITEMS) {
            Toast.makeText(this, "Packing list is full! Max $MAX_ITEMS items allowed.", Toast.LENGTH_LONG).show()
            Log.w(TAG, "Item limit reached")
            return
        }


        val name = etItemName.text.toString().trim()
        val qtyStr = etQuantity.text.toString().trim()
        val notes = etNotes.text.toString().trim()
        val category = spinnerCategory.selectedItem.toString()


        // Error handling: item name cannot be empty
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter an item name.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Validation error: empty item name")
            return
        }


        // Error handling: quantity must be a valid positive number
        if (qtyStr.isEmpty()) {
            Toast.makeText(this, "Please enter a quantity.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Validation error: empty quantity")
            return
        }


        val quantity = qtyStr.toIntOrNull()
        if (quantity == null || quantity <= 0) {
            Toast.makeText(this, "Quantity must be a positive whole number.", Toast.LENGTH_SHORT).show()
            Log.e(TAG, "Validation error: invalid quantity '$qtyStr'")
            return
        }


        // Store validated data in parallel arrays
        itemNames[itemCount] = name
        itemCategories[itemCount] = category
        itemQuantities[itemCount] = quantity
        itemNotes[itemCount] = if (notes.isEmpty()) "No notes" else notes
        itemCount++


        Log.d(TAG, "Item added: $name | $category | Qty: $quantity | Notes: $notes")


        // Update total display and clear input fields
        updateTotalDisplay()
        clearInputFields()
        Toast.makeText(this, "'$name' added to your pack!", Toast.LENGTH_SHORT).show()
    }


    /**
     * Uses a loop to count the total number of individual items packed
     * (summing all quantities) and updates the display TextView.
     */
    private fun updateTotalDisplay() {
        var totalItems = 0


        // Loop through all added items and sum their quantities
        for (i in 0 until itemCount) {
            totalItems += itemQuantities[i]
        }


        Log.d(TAG, "Total items packed: $totalItems across $itemCount entries")
        tvTotalPacked.text = "Items in Pack: $itemCount entries | $totalItems total units"
    }


    /**
     * Passes all parallel arrays and the item count to DetailActivity via Intent.
     */
    private fun navigateToDetails() {
        if (itemCount == 0) {
            Toast.makeText(this, "Your pack is empty! Add some gear first.", Toast.LENGTH_LONG).show()
            Log.w(TAG, "Tried to view details with no items")
            return
        }


        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("ITEM_NAMES", itemNames)
            putExtra("ITEM_CATEGORIES", itemCategories)
            putExtra("ITEM_QUANTITIES", itemQuantities)
            putExtra("ITEM_NOTES", itemNotes)
            putExtra("ITEM_COUNT", itemCount)
        }
        startActivity(intent)
    }


    /**
     * Resets all parallel arrays and the item count to zero.
     * Allows the user to start their packing list from scratch.
     */
    private fun clearAllData() {
        for (i in 0 until MAX_ITEMS) {
            itemNames[i] = ""
            itemCategories[i] = ""
            itemQuantities[i] = 0
            itemNotes[i] = ""
        }
        itemCount = 0


        clearInputFields()
        updateTotalDisplay()


        Log.d(TAG, "All gear data cleared")
        Toast.makeText(this, "Pack cleared! Ready for a new list.", Toast.LENGTH_SHORT).show()
    }


    /**
     * Clears only the input fields (not the stored arrays).
     */
    private fun clearInputFields() {
        etItemName.text.clear()
        etQuantity.text.clear()
        etNotes.text.clear()
        spinnerCategory.setSelection(0)
    }
}



