package com.example.tictactoe

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_play_game.*
import java.util.*
import kotlin.collections.ArrayList


class PlayGameActivity : AppCompatActivity() {
    val points: Array<Int> = Array(12){0}
    var activePlayer = 1
    var winner = 0
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()
    val emptyCells = ArrayList<Int>()
    var numRobots:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_game)

        numRobots = intent.getIntExtra("NUM_ROBOTS", 0)
        player1.addAll(0..11)
        player2.addAll(0..11)
        emptyCells.addAll(1..25)
    }

    fun buttonClick(view: View) {
        val buttonSelected = view as Button
        var buttonID = 0
        when(buttonSelected.id){
            R.id.button1 -> buttonID = 1
            R.id.button2 -> buttonID = 2
            R.id.button3 -> buttonID = 3
            R.id.button4 -> buttonID = 4
            R.id.button5 -> buttonID = 5
            R.id.button6 -> buttonID = 6
            R.id.button7 -> buttonID = 7
            R.id.button8 -> buttonID = 8
            R.id.button9 -> buttonID = 9
            R.id.button10 -> buttonID = 10
            R.id.button11 -> buttonID = 11
            R.id.button12 -> buttonID = 12
            R.id.button13 -> buttonID = 13
            R.id.button14 -> buttonID = 14
            R.id.button15 -> buttonID = 15
            R.id.button16 -> buttonID = 16
            R.id.button17 -> buttonID = 17
            R.id.button18 -> buttonID = 18
            R.id.button19 -> buttonID = 19
            R.id.button20 -> buttonID = 20
            R.id.button21 -> buttonID = 21
            R.id.button22 -> buttonID = 22
            R.id.button23 -> buttonID = 23
            R.id.button24 -> buttonID = 24
            R.id.button25 -> buttonID = 25
        }
        if(winner == 0)
            playGame(buttonID, buttonSelected)
        else
            buttonSelected.isEnabled = false;
    }


    private fun playGame(buttonID: Int, buttonSelected: Button) {
        var step = 0
        removeCellFromEnemyList(buttonID, activePlayer)
        println(player1)
        println(player2)
        emptyCells.remove(buttonID)

        if(activePlayer == 1) {
            buttonSelected.text = "X"
            buttonSelected.setBackgroundColor(Color.parseColor("#99bbff"))
            step = 1
            activePlayer = 2
            change_score(buttonID, step)

            if (emptyCells.isNotEmpty()) {
                if (numRobots != 0)
                    moveRobot()
            } else
                checkWinner(buttonSelected)
        }
        else if(activePlayer == 2) {
            buttonSelected.text = "O"
            buttonSelected.setBackgroundColor(Color.parseColor("#d9ff66"))
            step = -1
            activePlayer = 1
            change_score(buttonID, step)
            checkWinner(buttonSelected)
        }
        buttonSelected.isEnabled = false;
    }

    private fun change_score(buttonID: Int, step: Int) {
        points[(buttonID - 1) / 5] += step
        points[(buttonID - 1) % 5 + 5] += step
        if((buttonID - 1) % 6 == 0)
            points[10] += step
        if((buttonID != 1) && (buttonID != 25) && ((buttonID - 1) % 4 == 0))
            points[11] += step
    }

    private fun removeCellFromEnemyList(buttonID: Int, activePlayer: Int) {
        val enemyPlayerCellList: Any
        if(activePlayer == 1)
            enemyPlayerCellList = player2
        else
            enemyPlayerCellList = player1

        enemyPlayerCellList.remove((buttonID - 1) / 5)
        enemyPlayerCellList.remove((buttonID - 1) % 5 + 5)
        if ((buttonID - 1) % 6 == 0)
            enemyPlayerCellList.remove(10)
        if ((buttonID != 1) && (buttonID != 25) && ((buttonID - 1) % 4 == 0))
            enemyPlayerCellList.remove(11)
    }

    private fun checkWinner(buttonSelected: Button) {
        var tie = 0
        for( i in 0..11) {
            if(points[i] == 5) {
                winner = 1
                tie++
            }
            if(points[i] == -5) {
                winner = 2
                tie++
            }
        }
        if(tie == 2) {
            buttonSelected.isEnabled = false
            Toast.makeText(this,"Both Players Wins!", Toast.LENGTH_LONG).show()
        }
        else if(winner == 1 || winner == 2 ) {
            buttonSelected.isEnabled = false
            Toast.makeText(this, "Player " + winner + " Wins!", Toast.LENGTH_LONG).show()
        }
        else if(emptyCells.isEmpty()) {
            buttonSelected.isEnabled = false
            Toast.makeText(this,"No One Wins!", Toast.LENGTH_LONG).show()
        }
    }

    private fun moveRobot() {
        var buttonID = -5

        if(player2.isNotEmpty()) {
            val linePossiableWin = player2[0]
            var randIndexInLine = 0

            while(!emptyCells.contains(buttonID)) {
                if(linePossiableWin < 5) {
                    randIndexInLine = (1..5).random()
                    buttonID = linePossiableWin * 5 + randIndexInLine
                    println(buttonID)
                }
                else if(linePossiableWin < 10) {
                    randIndexInLine = (0..4).random()
                    buttonID = randIndexInLine * 5 + linePossiableWin - 4
                }
                else if(linePossiableWin == 10) {
                    buttonID += 6
                }
                else if(linePossiableWin == 11) {
                    if(buttonID < 0) {
                        buttonID = 5
                    }
                    else
                        buttonID += 4
                }
            }
        }
        else if (player1.isNotEmpty()){
            val linePossiableWin = player1[0]
            var randIndexInLine = 0

            while(!emptyCells.contains(buttonID)) {
                if(linePossiableWin < 5) {
                    randIndexInLine = (1..5).random()
                    buttonID = linePossiableWin * 5 + randIndexInLine
                }
                else if(linePossiableWin < 10) {
                    randIndexInLine = (0..4).random()
                    buttonID = randIndexInLine * 5 + linePossiableWin - 4
                }
                else if(linePossiableWin == 10)
                    buttonID += 6
                else if(linePossiableWin == 11) {
                    if(buttonID < 0)
                        buttonID = 5
                    else
                        buttonID += 4
                }
            }
        }
        else {
            if(emptyCells.isNotEmpty()) {
                while(!emptyCells.contains(buttonID))
                    buttonID = (1..25).random()
            }
        }

        var randButton:Button
        when(buttonID) {
            1 -> randButton = button1
            2 -> randButton = button2
            3 -> randButton = button3
            4 -> randButton = button4
            5 -> randButton = button5
            6 -> randButton = button6
            7 -> randButton = button7
            8 -> randButton = button8
            9 -> randButton = button9
            10 -> randButton = button10
            11 -> randButton = button11
            12 -> randButton = button12
            13 -> randButton = button13
            14 -> randButton = button14
            15 -> randButton = button15
            16 -> randButton = button16
            17 -> randButton = button17
            18 -> randButton = button18
            19 -> randButton = button19
            20 -> randButton = button20
            21 -> randButton = button21
            22 -> randButton = button22
            23 -> randButton = button23
            24 -> randButton = button24
            else -> randButton = button25
        }
        playGame(buttonID, randButton)
    }
}

