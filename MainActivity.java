package com.example.basictictactoegame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];

    private boolean player1turn = true;

    private int roundcount;

    private int player1points;

    private int player2points;

    private TextView textViewplayer1;
    private TextView textviewplayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewplayer1 = findViewById(R.id.text_view_p1);
        textviewplayer2 = findViewById(R.id.text_view_p2);

        for  (int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID;
                resID = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonreset = findViewById(R.id.button_reset);
        buttonreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    private void resetGame() {
        player1points = 0;
        player2points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")){
            return;
        }
        if (player1turn){
            ((Button) v).setText("X");
        }
        else{
            ((Button) v).setText("O");
        }

        roundcount++;
        if (checkforwin()){
            if(player1turn){
                player1Wins();
            }
            else{
                player2Wins();
            }
        }
        else if (roundcount == 9){
            draw();
        }
        else{
            //switch turns
            player1turn = !player1turn;
        }
    }

    private void updatePointsText(){
        textViewplayer1.setText("Player 1: " + player1points);
        textviewplayer2.setText("Player 2: " + player2points);
    }

    private void resetBoard(){
        for (int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }

        roundcount = 0;
        player1turn = true;


    }

    private void draw() {
        Toast.makeText(this,"Draw!",Toast.LENGTH_SHORT).show();
        resetBoard();

    }

    private void player1Wins() {
        player1points++;
        Toast.makeText(this,"Player 1 Wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2points++;
        Toast.makeText(this,"Player 2 Wins!",Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private boolean checkforwin(){
        String[][]  field = new String[3][3];
        for (int i=0; i<3;i++){
            for (int j=0; j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }
        //checking for horizontal
        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1])
            && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")){
                return true;
            }
        }
        //checking for vertical
        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }
        //checking the diagonal
        if(field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }
        if(field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }
    //got this method by pressing ctrl+o
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //rotate the screen
        outState.putInt("roundCount",roundcount);
        outState.putInt("player1Points",player1points);
        outState.putInt("player2Points",player2points);
        outState.putBoolean("player1Turn", player1turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundcount = savedInstanceState.getInt("roundCount");
        player1points = savedInstanceState.getInt("player1Points");
        player2points = savedInstanceState.getInt("player2Points");
        player1turn = savedInstanceState.getBoolean("player1Turn");
    }
}
