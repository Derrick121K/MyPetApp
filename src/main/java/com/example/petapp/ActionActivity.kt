package com.example.petapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActionActivity : AppCompatActivity() {
    // Button variables
    private lateinit var feedButton: Button
    private lateinit var cleanButton: Button
    private lateinit var playButton: Button

    // TextViews for displaying status messages
    private lateinit var statusTextView: TextView
    private lateinit var hungerStatusTextView: TextView
    private lateinit var cleanlinessStatusTextView: TextView

    // Variable declaration for petStatus
    private lateinit var petStatus: PetStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action)

        // initialise the find view on the buttons
        feedButton = findViewById(R.id.feedButton)
        cleanButton = findViewById(R.id.cleanButton)
        playButton = findViewById(R.id.playButton)

        // initialise the find view on the TextViews
        statusTextView = findViewById(R.id.statusTextView)
        hungerStatusTextView = findViewById(R.id.hungerStatusTextView)
        cleanlinessStatusTextView = findViewById(R.id.cleanlinessStatusTextView)

        // Initialize petStatus
        petStatus = PetStatus()

        // Set initial status text
        val (statusText, message) = updateStatusText()
        statusTextView.text = statusText
        hungerStatusTextView.text = message
        cleanlinessStatusTextView.text = message

        // Set click listeners for buttons
        feedButton.setOnClickListener {
            updatePetStatus("feed")
            updatePetImage("feed")
            val (statusText, message) = updateStatusText()
            statusTextView.text = statusText
            hungerStatusTextView.text = message
        }

        cleanButton.setOnClickListener {
            updatePetStatus("clean")
            updatePetImage("clean")
            val (statusText, message) = updateStatusText()
            statusTextView.text = statusText
            cleanlinessStatusTextView.text = message
        }

        playButton.setOnClickListener {
            updatePetStatus("play")
            updatePetImage("play")
            val (statusText, message) = updateStatusText()
            statusTextView.text = statusText
            hungerStatusTextView.text = message
            cleanlinessStatusTextView.text = message
        }
    }

    private fun updatePetStatus(action: String) {
        // Update pet's status values based on the action performed
        // You can implement this logic based on your requirements
        // For demonstration purposes, I'll just increment/decrement values
        when (action) {
            "feed" -> petStatus.hunger += 5
            "clean" -> petStatus.cleanliness += 10
            "play" -> {
                petStatus.hunger -= 10
                petStatus.cleanliness -= 5
            }
        }
        // Ensure values are within bounds
        petStatus.hunger = petStatus.hunger.coerceIn(0, 100)
        petStatus.cleanliness = petStatus.cleanliness.coerceIn(0, 100)
    }

    private fun updatePetImage(action: String) {
        // Update pet's image based on the action performed
        // You can set different images for each action
        val imageResource = when (action) {
            "feed" -> R.drawable.feeding
            "clean" -> R.drawable.cleaning
            "play" -> R.drawable.playing
            else -> R.drawable.pet_image
        }
        val petImageView = findViewById<ImageView>(R.id.petImageView)
        petImageView.setImageResource(imageResource)
    }

    private fun updateStatusText(): Pair<String, String> {
        // Update the statusTextView to display current pet status
        val cleanlinessMessage = when {
            petStatus.cleanliness >= 80 -> "Your pet is clean"
            petStatus.cleanliness <= 25 -> "Your pet needs a bath"
            else -> ""
        }

        val hungerMessage = when {
            petStatus.hunger <= 0 -> "Your pet is hungry"
            petStatus.hunger >= 100 -> "Your pet is full"
            else -> ""
        }

        val statusText = "Hunger: ${petStatus.hunger}%\nCleanliness: ${petStatus.cleanliness}%"

        return Pair(statusText, "$cleanlinessMessage\n$hungerMessage")
    }
}

// Define PetStatus data class
data class PetStatus(var hunger: Int = 50, var cleanliness: Int = 50)