package edu.harrisburgu.cisc349.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import android.view.MotionEvent;
import android.widget.Toast;

public class Calculator extends AppCompatActivity {

    boolean isButtonPressed = false;
// Buttons for operations and view
    private TextView AnswerView;
    private int firstvalue;
    private int secondvalue;
    private char currentSymbol;

    private static final char Addition = '+';
    private static final char Subtraction = '-';


// Buttons for numbers
    private MaterialButton m9Button, m8Button, m7Button, mplusButton;
    private MaterialButton m6Button, m5Button, m4Button, mminusButton;

    private MaterialButton m3Button, m2Button, m1Button, mequalButton;
    private MaterialButton m0Button;

// Long press button
    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

// Answer ID
        AnswerView = findViewById(R.id.answer_view);

// Long press ID
        gestureDetector = new GestureDetector(this, new GestureListener());

// Button IDs
        m0Button = findViewById(R.id.button_0);
        m1Button = findViewById(R.id.button_1);
        m2Button = findViewById(R.id.button_2);
        m3Button = findViewById(R.id.button_3);
        m4Button = findViewById(R.id.button_4);
        m5Button = findViewById(R.id.button_5);
        m6Button = findViewById(R.id.button_6);
        m7Button = findViewById(R.id.button_7);
        m8Button = findViewById(R.id.button_8);
        m9Button = findViewById(R.id.button_9);

// Operation IDs
        mplusButton = findViewById(R.id.plus_operation);
        mminusButton = findViewById(R.id.subtraction_operation);
        mequalButton = findViewById(R.id.equal_operation);


// Click listeners for number buttons
        m0Button.setOnClickListener(onNumberClickListener("0"));
        m1Button.setOnClickListener(onNumberClickListener("1"));
        m2Button.setOnClickListener(onNumberClickListener("2"));
        m3Button.setOnClickListener(onNumberClickListener("3"));
        m4Button.setOnClickListener(onNumberClickListener("4"));
        m5Button.setOnClickListener(onNumberClickListener("5"));
        m6Button.setOnClickListener(onNumberClickListener("6"));
        m7Button.setOnClickListener(onNumberClickListener("7"));
        m8Button.setOnClickListener(onNumberClickListener("8"));
        m9Button.setOnClickListener(onNumberClickListener("9"));


// Actions when the equal button is pressed
        mequalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculations();
                resetPlusButtonColor();
            }
        });

// Set long click listener for equal button
        mequalButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AnswerView.setText("");
                Toast.makeText(Calculator.this, "Cleared", Toast.LENGTH_SHORT).show();
                return true;
            }
        });


// Actions when the plus button is pressed
        mplusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculations();
                currentSymbol = Addition;
                AnswerView.append("+");
                mplusButton.setBackgroundColor(getResources().getColor(R.color.black));
                isButtonPressed = true;
            }
        });


// Actions when the minus button is pressed
        mminusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculations();
                currentSymbol = Subtraction;
                AnswerView.append("-");
                mminusButton.setBackgroundColor(getResources().getColor(R.color.black));
                isButtonPressed = true;
            }
        });
    }


// OnClickListener for number buttons
    private View.OnClickListener onNumberClickListener(final String number) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPlusButtonColor();
                AnswerView.append(number);
            }
        };
    }



// Calculates both addition and subtraction
    private void calculations(){
        String expression = AnswerView.getText().toString();
        if (!expression.isEmpty()) {
            String[] parts = expression.split("[-+]");
            if (parts.length == 2) {
                firstvalue = Integer.parseInt(parts[0]);
                secondvalue = Integer.parseInt(parts[1]);
                if (currentSymbol == Addition)
                    firstvalue = firstvalue + secondvalue;
                else if (currentSymbol == Subtraction)
                    firstvalue = firstvalue - secondvalue;
                AnswerView.setText(String.valueOf(firstvalue));
            }
        }
    }


// Changes the color of the buttons
    private void resetPlusButtonColor() {
        mplusButton.setBackgroundColor(getResources().getColor(R.color.blue));
        isButtonPressed = false;
    }

    // GestureListener for long press
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            AnswerView.setText("");
            Toast.makeText(Calculator.this, "Cleared", Toast.LENGTH_SHORT).show();
        }
    }
}