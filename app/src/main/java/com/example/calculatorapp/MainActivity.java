package com.example.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import org.mariuszgromada.math.mxparser.*; //math imports

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //instance variable
    private EditText calcDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcDisplay = findViewById(R.id.DisplayEditText); //initializing variable

        calcDisplay.setShowSoftInputOnFocus(false); //disables OEM keyboard from popping up

        //Set On click listener to check for user input
        calcDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if user clicks the display, the default message will be cleared
                if (getString(R.string.display).equals(calcDisplay.getText().toString())) {
                    calcDisplay.setText("");
                }

            }
        });

    }//onCreate

    private void update(String addStr) {
        /*Update method will be used to update the display
          @param: String s
        */

        String LastStr = calcDisplay.getText().toString(); //gets text from old display + converts
        int pos = calcDisplay.getSelectionStart();         //position of cursor
        String LStr = LastStr.substring(0, pos);           //stores left half of cursor
        String RStr = LastStr.substring(pos);              //stores right half of cursor

        //If-statement used to remove repeated display clicks in order to use
        if (getString(R.string.display).equals(calcDisplay.getText().toString())) {
            calcDisplay.setText(addStr);        //adds the first character user wants
            calcDisplay.setSelection(pos + 1);    //moves cursor pos to right
        } else {
            /*else-statement that is run if the display already has characters
            formats 3 strings, takes left half of cursor first
            Then adds the new string and finally attaches the right half back
            */
            calcDisplay.setText(String.format("%s%s%s", LStr, addStr, RStr));
            calcDisplay.setSelection(pos + 1); //moves cursor pos to right
        }//if-else


    }//update

    //Other
    public void clearBttn(View view) {
        calcDisplay.setText("");
    }//clearBttn

    public void expBttn(View view) {
        update("^");
    }//expBttn

    public void decBttn(View view) {
        update(".");
    }//decBttn

    public void signBttn(View view) {
        update("-");
    }//signBttn

    public void parentBttn(View view) {
        int pos = calcDisplay.getSelectionStart(); //cursos position
        int open = 0;
        int closed = 0;
        int length = calcDisplay.getText().length();

        //loop dependant on pos since we need to see amount of parents to left/right of pos
        //if-else checks to see if par is open/close and if so increases value of par by 1
        for (int i = 0; i < pos; i++) {
            if (calcDisplay.getText().toString().substring(i, i + 1).equals("(")) {
                open += 1;
            }
            if (calcDisplay.getText().toString().substring(i, i + 1).equals(")")) {
                closed += 1;
            }
        }//for-loop

        //if-else that determines whether open or closed parents need to be used
        if (open == closed || calcDisplay.getText().toString().substring(length - 1, length).equals("(")) {
            update("(");
            calcDisplay.setSelection(pos + 1);
        } else if (closed < open && !calcDisplay.getText().toString().substring(length - 1, length).equals("(")) {
            update(")");
            calcDisplay.setSelection(pos + 1);
        }

    }//parentBttn

    public void deleteBttn(View view) {
        int pos = calcDisplay.getSelectionStart(); //position of cursor
        int length = calcDisplay.getText().length(); //length of text

        //if-statement ran long as positon AND length is not zero
        if (pos != 0 && length != 0) {
            //SSB allows us to replace diff chars
            SpannableStringBuilder select = (SpannableStringBuilder) calcDisplay.getText();
            select.replace(pos - 1, pos, ""); //replace char with empty string
            calcDisplay.setText(select);
            calcDisplay.setSelection(pos - 1); //sets pos back to original pos
        }
    }//deleteBttn

    //Operators
    public void divideBttn(View view) {
        update("÷");
    }//divideBttn

    public void multiplyBttn(View view) {
        update("×");
    }//multiplyBttn

    public void subtractBttn(View view) {
        update("-");
    }//subtractBttn

    public void addBttn(View view) {
        update("+");
    }//addBttn

    public void equalsBttn(View view) {
        String express = calcDisplay.getText().toString(); //grab the expression

        //replace alt-codes with operators to use with Mxparser (math calculations) import
        express = express.replaceAll("÷", "/");
        express = express.replaceAll("×", "*");

        //calculate the expression
        Expression exn = new Expression(express);
        String result = String.valueOf(exn.calculate());
        calcDisplay.setText(result); // update display text
        calcDisplay.setSelection(result.length()); //sets pos to end of result

    }//equalsBttn

    //Numericals
    public void zeroBttn(View view) {
        update("0");
    }//zeroBttn

    public void oneBttn(View view) {
        update("1");
    }//oneBttn

    public void twoBttn(View view) {
        update("2");
    }//twoBttn

    public void threeBttn(View view) {
        update("3");
    }//threeBttn

    public void fourBttn(View view) {
        update("4");
    }//fourBttn

    public void fiveBttn(View view) {
        update("5");
    }//fiveBttn

    public void sixBttn(View view) {
        update("6");
    }//sixBttn

    public void sevenBttn(View view) {
        update("7");
    }//sevenBttn

    public void eightBttn(View view) {
        update("8");
    }//eightBttn

    public void nineBttn(View view) {
        update("9");
    }//nineBttn

}//MainActivity