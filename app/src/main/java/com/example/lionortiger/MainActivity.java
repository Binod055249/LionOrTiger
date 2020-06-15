package com.example.lionortiger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player{
        ONE ,TWO , No

    }
     Player currentPlayer= Player.ONE;
    Player[] playerChoices = new Player[9];

    int[][] winnerRowsColumuns ={{0,1,2},{3,4,5},{6,7,8},{0,4,8},{2,4,6},{0,3,6},{1,4,7},{2,5,8}};
    private  boolean gameOver = false;

    private Button btnReset;
    private GridLayout gridLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          for(int index=0; index<playerChoices.length;index++) {
              playerChoices[index] = Player.No;
          }

        gridLayout= findViewById(R.id.gridLayout);
        btnReset=findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               resetTheGame();
            }
        });
    }

    public void imgViewIsTapped(View view) {
        String winnerOfGame = " ";
        ImageView tappedImageview = (ImageView)view;
        int tiTag=Integer.parseInt(tappedImageview.getTag().toString());//getting the tag of image

        if(playerChoices[tiTag]==Player.No && gameOver == false) {

            tappedImageview.setTranslationX(-2000f);


            playerChoices[tiTag] = currentPlayer;

            if (currentPlayer == Player.ONE) {
                tappedImageview.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;


            } else if (currentPlayer == Player.TWO) {
                tappedImageview.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;

            }
            tappedImageview.animate().translationXBy(2000f).alpha(1).rotation(3600).setDuration(700);

            Toast.makeText(this, tappedImageview.getTag() + "", Toast.LENGTH_SHORT).show();

            for (int[] winnerColumns : winnerRowsColumuns) {

                if (playerChoices[winnerColumns[0]] == playerChoices[winnerColumns[1]] &&
                        playerChoices[winnerColumns[1]] == playerChoices[winnerColumns[2]] &&
                        playerChoices[winnerColumns[0]] != Player.No ) {
                          btnReset.setVisibility(view.VISIBLE);

                    gameOver=true;
                    if (currentPlayer == Player.ONE) {
                        winnerOfGame = "Player Two";
                    } else if (currentPlayer == Player.TWO) {
                        winnerOfGame = "Player One";
                    }
                    Toast.makeText(this, winnerOfGame + " is Winner", Toast.LENGTH_SHORT).show();

                }
            }

        }
        if(gameOver==true ){

            AlertDialog alert =new AlertDialog.Builder(this)
                    .setTitle("Game Over")
                    .setMessage(winnerOfGame+" is winner"+"\nDo You Want to Restart?")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).show();
            alert.setCancelable(false);
        }
    }
    //Reset Game Function
    private void resetTheGame(){
        for (int index =0; index<gridLayout.getChildCount();index++){

            ImageView imageView = (ImageView) gridLayout.getChildAt(index);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);

        }
        currentPlayer= Player.ONE;
         for(int index=0;index<playerChoices.length;index++) {
             playerChoices[index] = Player.No;
         }

       gameOver =false;

       btnReset.setVisibility(View.GONE);
    }
}

