package com.example.tictactoe;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button[] button= new Button[9];
    int[] board={0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] win={{0,3,6},{1,4,7},{2,5,8},
            {0,1,2},{3,4,5},{6,7,8},
            {0,4,8},{2,4,6}};
    int[] moves={0,1,2,3,4,5,6,7,8};
    boolean gameActive=true;
    static int moves_played;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        button[0] = findViewById(R.id.button1);
        button[1] = findViewById(R.id.button2);
        button[2] = findViewById(R.id.button3);
        button[3] = findViewById(R.id.button4);
        button[4] = findViewById(R.id.button5);
        button[5] = findViewById(R.id.button6);
        button[6] = findViewById(R.id.button7);
        button[7] = findViewById(R.id.button8);
        button[8] = findViewById(R.id.button9);
        Button reset = findViewById(R.id.reset_button);

        button[0].setOnClickListener(this);
        button[1].setOnClickListener(this);
        button[2].setOnClickListener(this);
        button[3].setOnClickListener(this);
        button[4].setOnClickListener(this);
        button[5].setOnClickListener(this);
        button[6].setOnClickListener(this);
        button[7].setOnClickListener(this);
        button[8].setOnClickListener(this);
        reset.setOnClickListener(this);
        moves_played=0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button1:
                move(0,-1, findViewById(R.id.button1));
                game();
                break;
            case R.id.button2:
                move(1,-1, findViewById(R.id.button2));
                game();
                break;
            case R.id.button3:
                move(2,-1, findViewById(R.id.button3));
                game();
                break;
            case R.id.button4:
                move(3,-1, findViewById(R.id.button4));
                game();
                break;
            case R.id.button5:
                move(4,-1, findViewById(R.id.button5));
                game();
                break;
            case R.id.button6:
                move(5,-1, findViewById(R.id.button6));
                game();
                break;
            case R.id.button7:
                move(6,-1, findViewById(R.id.button7));
                game();
                break;
            case R.id.button8:
                move(7,-1, findViewById(R.id.button8));
                game();
                break;
            case R.id.button9:
                move(8,-1, findViewById(R.id.button9));
                game();
                break;
            case R.id.reset_button:
                reset();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void game() {
        for(int i[]:win) {
            if (board[i[0]] + board[i[1]] + board[i[2]] == 2) {
                int[] index = sorted(i,0);
                int w=index[0];
                move(w,1,button[w]);
                return;
            }
        }
        for(int i[]:win) {
            if (board[i[0]] + board[i[1]] + board[i[2]] == -2) {
                int[] index = sorted(i,0);
                int w=index[0];
                move(w,1,button[w]);
                return;
            }
        }
        counter();
    }

    private int[] sorted(int[] array, int value) {
        List<Integer> index = new ArrayList<>();
        int j=0;
        for(int i:array){
            if(board[i]==value){
                index.add(i);
            }
        }
        int[] out=new int[index.size()];
        for (int i =0;i<index.size();i++){
            out[i] = index.get(i);
        }
        return out;
    }

    private void check() {
        for (int[] i :win){
            if(board[i[0]]+board[i[1]]+board[i[2]]==3){
                Toast.makeText(this,"You Lost!",Toast.LENGTH_SHORT).show();
                gameActive=false;
            }
            else if(board[i[0]]+board[i[1]]+board[i[2]]==-3){
                Toast.makeText(this,"You win!",Toast.LENGTH_SHORT).show();
                gameActive=false;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void counter() {
        if(gameActive){
            int[] moves_left = sorted(moves,0);
            int[] corners={0,2,6,8};
            int[] sides = {1, 3, 5, 7};
            Random rand = new Random();
            switch (moves_played){
                case 1:
                    if(board[4]==0){move(4,1,button[4]);
                        return;}
                    else{
                        int m=rand.nextInt(4);
                        move(corners[m],1,button[corners[m]]);
                        return;
                    }
                case 3:
                    if(board[0]+board[8]==-2||board[2]+board[6]==-2) {
                    int m = rand.nextInt(4);
                    move(sides[m], 1, button[sides[m]]);
                    }
                    else if(sorted(corners,-1).length!=0){
                        int[] m= sorted(corners,0);
                        move(m[0],1,button[m[0]]);
                    }
                    else if (board[1]+board[5]==-2||board[5]+board[7]==-2||board[7]+board[3]==-2||board[3]+board[1]==-2){
                        int m=0;
                        int[] places = new int[6];
                        int k=0;
                        for(int i[]:win) {
                            if (board[i[0]] + board[i[1]] + board[i[2]] == -1) {
                                places[k]=i[0];
                                places[k+1]=i[1];
                                places[k+2]=i[2];
                                k=3;
                            }
                        }
                        for(int i:places) {
                            int f=0;
                            for(int j:places) {
                                if(i==j){
                                    f++;
                                    if(f==2){
                                        m=i;
                                        break;
                                    }
                                }
                            }
                        }
                        move(m,1,button[m]);
                    }
                    else{
                        randomly(moves_left);
                    }
                    break;
                default:
                    randomly(moves_left);
                    break;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void randomly(int[] moves_left) {
        for(int i[]:win) {

            if ((board[i[0]] + board[i[1]] + board[i[2]] == 1)&&(board[i[0]]!=-1)&&(board[i[1]]!=-1)&&board[i[2]]!=-1) //Wtf (All are &&?)
            {
                int[] index = sorted(i,0);
                int w=index[0];
                move(w,1,button[w]);
                return;
            }
            else if ((board[i[0]] + board[i[1]] + board[i[2]] == 0)) {
                int[] index = sorted(i,0);
                int w=index[0];
                move(w,1,button[w]);
                return;
            }
        }
    }


    private void reset() {
        gameActive=true;
        for( int i = 0; i < 9;i++){
            move(i,0,button[i]);
        }
        moves_played=0;
    }

    private void move(int i1, int i2,Button button) {
        if(gameActive){
            moves_played++;
            if(i2==-1){
                button.setText("X");
                button.setClickable(false);
                board[i1]=-1;
                check();
            }
            else if(i2==1){
                button.setText("O");
                button.setClickable(false);
                board[i1]=1;
                check();
            }
            else{
                button.setText("");
                board[i1]=0;
                button.setClickable(true);
            }
        }
    }
}
