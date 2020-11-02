package com.example.tiktaktoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][3];
    Button buttonReset;
    private boolean player1Turn = true;
    private int roundCount = 0;
    private int player1Points = 0;
    private int player2Points = 0;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = (TextView) findViewById(R.id.textViewP1);
        textViewPlayer2 = (TextView) findViewById(R.id.textViewP2);

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                String buttonID = "btn" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        buttonReset = (Button) findViewById(R.id.resetBtn);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        /*View.OnClickListener onButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                switch (v.getId()) {
                    case R.id.btn1:
                        btn1.setText("X");
                        break;
                    case R.id.btn2:
                        btn2.setText("X");
                        break;
                    case R.id.btn3:
                        btn3.setText("X");
                        break;
                }
            }
        };

        btn1.setOnClickListener(onButtonClickListener);
        btn2.setOnClickListener(onButtonClickListener);
        btn3.setOnClickListener(onButtonClickListener);*/
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")) {
            return;
        }

        if(player1Turn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }

        roundCount += 1;

        if(checkForWin()) {
            if(player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if(roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i = 0; i < 3; i++) {
            //horizontal
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
                return true;
            //vertikal
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                return true;
        }

        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
            return true;
        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
            return true;

        return false;
    }

    private void player1Wins() {
        player1Points += 1;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points += 1;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player1: " + player1Points);
        textViewPlayer2.setText("Player2: " + player2Points);
    }

    private void resetBoard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;

        updatePointsText();

        resetBoard();
    }

    //wird aufgerufen, wenn Handy gedreht wird
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    //Variablen werden wiederhergestellt
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}