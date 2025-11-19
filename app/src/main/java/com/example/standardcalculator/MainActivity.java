package com.example.standardcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText tvInput;
    private double firstNum = 0;
    private String currentOp = "";
    private double memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);

        // Numeric buttons
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numClick = v -> {
            Button b = (Button) v;
            String text = tvInput.getText().toString();
            if (text.equals("0")) tvInput.setText(b.getText());
            else tvInput.append(b.getText());
        };

        for (int id : numberIds) findViewById(id).setOnClickListener(numClick);

        // Decimal point
        findViewById(R.id.btnDot).setOnClickListener(v -> {
            if (!tvInput.getText().toString().contains(".")) tvInput.append(".");
        });

        // Operators
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperation("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> setOperation("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> setOperation("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperation("/"));

        // Square and square root
        findViewById(R.id.btnSquare).setOnClickListener(v -> {
            double val = getCurrentValue();
            tvInput.setText(String.valueOf(val * val));
        });

        findViewById(R.id.btnSqrt).setOnClickListener(v -> {
            double val = getCurrentValue();
            tvInput.setText(String.valueOf(Math.sqrt(val)));
        });

        // Clear buttons
        findViewById(R.id.btnC).setOnClickListener(v -> tvInput.setText("0")); // Clear all

        findViewById(R.id.btnCE).setOnClickListener(v -> { // Clear last entry
            String text = tvInput.getText().toString();
            if (text.length() > 1) tvInput.setText(text.substring(0, text.length() - 1));
            else tvInput.setText("0");
        });

        // Memory buttons
        findViewById(R.id.btnMC).setOnClickListener(v -> memory = 0);
        findViewById(R.id.btnMR).setOnClickListener(v -> {             // Memory Recall
            tvInput.setText(String.valueOf(memory));
        });
        findViewById(R.id.btnMPlus).setOnClickListener(v -> memory += getCurrentValue());
        findViewById(R.id.btnMMinus).setOnClickListener(v -> memory -= getCurrentValue());

        // Equal button
        findViewById(R.id.btnEqual).setOnClickListener(v -> calculate());
    }

    // Helper: get value from display
    private double getCurrentValue() {
        String text = tvInput.getText().toString();
        if (text.equals("") || text.equals(".")) return 0;
        return Double.parseDouble(text);
    }

    // Set the operation
    private void setOperation(String op) {
        firstNum = getCurrentValue();
        currentOp = op;
        tvInput.setText("0");
    }

    // Perform calculation
    private void calculate() {
        double secondNum = getCurrentValue();
        double result = 0;

        switch (currentOp) {
            case "+": result = firstNum + secondNum; break;
            case "-": result = firstNum - secondNum; break;
            case "*": result = firstNum * secondNum; break;
            case "/":
                if (secondNum != 0) result = firstNum / secondNum;
                else result = 0; // prevent divide by zero
                break;
            default: result = secondNum; // if no operation
        }

        tvInput.setText(String.valueOf(result));
        currentOp = ""; // reset operation
    }
}
