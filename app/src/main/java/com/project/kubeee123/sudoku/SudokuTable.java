package com.project.kubeee123.sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class SudokuTable extends AppCompatActivity {
    private class Cell {
        int value;
        boolean fixed;
        Button bt;
        LinearLayout view;

        public Cell(int intValue, Context THIS) {

            value = intValue;
            if (value != 0) fixed = true;
            else fixed = false;
            bt = new Button(THIS);
            view=new LinearLayout(THIS);
            view.addView(bt);
            if (fixed) bt.setText(String.valueOf(value));
            else bt.setTextColor(Color.BLUE);
            bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fixed) return;
                    value++;
                    if (value > 9) value = 1;
                    bt.setText(String.valueOf(value));
                    if (correct()) {
                        tv.setText("");
                    } else {
                        tv.setTextColor(Color.RED);
                        tv.setText("There is a repeated digit!!!");
                    }

                    if (completed() && correct()) {
                        tv.setTextColor(Color.GREEN);
                        tv.setText(" YOU WON");
                        table[mistakes[0]][mistakes[1]].bt.setTextColor(Color.BLACK);
                    } else if (completed() && !correct()) {
                        tv.setTextColor(Color.RED);
                        tv.setText("There is a repeated digit!!!");


                    }
                }
            });
        }
    }



    Cell[][] table;
    int[] mistakes = new int[14];
    String input;
    TableLayout tl;
    TextView tv;
    LinearLayout mainLayout, buttonsLayout;
    Button restart,menu;


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_sudoku);
        Intent intent = getIntent();
        String level = intent.getStringExtra("level");

        if (level.equals("easy")){
            this.setTitle("EASY SUDOKU");

            input = "? ? ? ? 8 ? ? ? 9 " +
                    "? ? ? 4 2 6 1 3 ? " +
                    "? ? ? 9 ? 1 5 ? 6 " +
                    "2 ? ? 8 3 ? 9 7 4 " +
                    "3 ? 9 ? 6 ? ? 8 ? " +
                    "? ? ? 2 9 4 ? ? ? " +
                    "? 5 6 3 1 ? ? ? ? " +
                    "? ? ? ? ? ? 8 ? 7 " +
                    "? 8 4 ? 5 2 ? 1 ? ";

          /*      completed = "6 1 2 5 8 3 7 4 9 " +
                              "5 9 7 4 2 6 1 3 8 " +
                              "4 3 8 9 7 1 5 2 6 " +
                              "2 6 1 8 3 5 9 7 4 " +
                              "3 4 9 1 6 7 2 8 5 " +
                              "8 7 5 2 9 4 3 6 1 " +
                              "7 5 6 3 1 8 4 9 2 " +
                              "1 2 3 6 4 9 8 5 7 " +
                              "9 8 4 7 5 2 6 1 3 ";*/

        }else {
            this.setTitle("HARD SUDOKU");
            input = "2 ? ? ? ? ? ? 5 1 "+
                    "? ? ? ? ? ? ? ? 7 "+
                    "8 7 6 ? ? 5 2 ? ? "+
                    "3 ? 4 ? 2 8 ? ? ? "+
                    "? ? ? ? ? ? 5 ? 9 "+
                    "? ? 5 ? ? 1 3 ? 4 "+
                    "? 2 ? ? 8 4 ? 1 ? "+
                    "? ? ? ? 1 ? ? 9 8 "+
                    "? ? ? ? ? ? ? ? ? ";


            /*  completed  2 4 3 8 7 6 9 5 1
                           9 5 1 2 4 3 8 6 7
                           8 7 6 1 9 5 2 4 3
                           3 9 4 5 2 8 1 7 6
                           1 6 2 4 3 7 5 8 9
                           7 8 5 9 6 1 3 2 4
                           6 2 9 3 8 4 7 1 5
                           5 3 7 6 1 2 4 9 8
                           4 1 8 7 5 9 6 3 2*/
        }





        String[] split = input.split(" ");
        table = new Cell[9][9];
        init();
        for (int i = 0; i < 9; i++) {
            TableRow tr = new TableRow(this);
            if(i==2||i==5){
                tr.setPadding(0,0,0,30);
            }
            for (int j = 0; j < 9; j++) {
                String s = split[i * 9 + j];
                Character c = s.charAt(0);
                table[i][j] = new Cell(c == '?' ? 0 : c - '0', this);
                if(j==2||j==5){
                    table[i][j].view.setPadding(0,0,30,0);
                }
                tr.addView(table[i][j].view);
            }

            tl.addView(tr);

        }

       viewSetting();
        setContentView(mainLayout);



    }

    public void init(){
        tl = new TableLayout(this);
        tv = new TextView(this);
        restart=new MaterialButton(this);
        menu=new MaterialButton(this);
        mainLayout = new LinearLayout(this);
        buttonsLayout =new LinearLayout(this);
    }
    public void viewSetting(){
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonsParams.setMargins(10,10,10,10);

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(10,50,10,10);

        tv.setLayoutParams(textViewParams);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,17);


        restart.setText("Restart");
        restart.setLayoutParams(buttonsParams);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                overridePendingTransition(0, 0);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                overridePendingTransition(0, 0);
                startActivity(intent);

            }
        });

        menu.setText("menu");
        menu.setLayoutParams(buttonsParams);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setPadding(50,50,50,50);
        buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        buttonsLayout.setLayoutParams(textViewParams);
        buttonsLayout.addView(menu);
        buttonsLayout.addView(restart);

        mainLayout.addView(tl);
        mainLayout.addView(tv);
        mainLayout.addView(buttonsLayout);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(50,50,50,50);

        tl.setShrinkAllColumns(true);
        tl.setStretchAllColumns(true);

    }

    boolean completed() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (table[i][j].value == 0)
                    return false;
        return true;
    }


    boolean correct(int i1, int j1, int i2, int j2) {
        boolean[] seen = new boolean[10];
        for (int i = 1; i < 9; i++) seen[i] = false;
        for (int i = i1; i < i2; i++) {
            for (int j = j1; j < j2; j++) {
                int value = table[i][j].value;
                if (value != 0) {
                    if (seen[value]) {
                        return false;
                    }
                    seen[value] = true;
                }
            }
        }

        return true;
    }


    boolean correct() {
        for (int i = 0; i < 9; i++)
            if (!correct(i, 0, i + 1, 9)) {
                return false;
            }
        for (int j = 0; j < 9; j++)
            if (!correct(0, j, 9, j + 1)) {
                return false;
            }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!correct(3 * i, 3 * j, 3 * i + 3, 3 * j + 3)) {
                    return false;
                }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}