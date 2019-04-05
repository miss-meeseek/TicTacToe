package com.example.tictactoe

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.view.menu.MenuView
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    lateinit var radioGroup: RadioGroup
    lateinit var startButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        radioGroup = this.findViewById(R.id.radioGroup)
        startButton = this.findViewById(R.id.startButton)
        radioButton.setChecked(true)

        startButton.setOnClickListener {
            val id = radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(id)
            Toast.makeText(this, "Selected" + radioButton.text, Toast.LENGTH_LONG).show()

            val intent = Intent(this, PlayGameActivity::class.java)

            if(radioButton.text == "OnePlayer")
                intent.putExtra("NUM_ROBOTS", 1)
            else if(radioButton.text == "TwoPlayers")
                intent.putExtra("NUM_ROBOTS", 0)

            startActivity(intent)
        }
    }
}
