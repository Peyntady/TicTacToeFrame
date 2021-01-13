package ru.job4j.tictactoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final Logic logic = new Logic();
    private final List<Button> buttons = new ArrayList<>();
    private SwitchMaterial switchSide, switchOpponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutForButtons = findViewById(R.id.LayoutForButtons);
        for (int i = 0; i < layoutForButtons.getChildCount(); i++) {
            LinearLayout layout = findViewById(layoutForButtons.getChildAt(i).getId());
            for (int j = 0; j < layout.getChildCount(); j++) {
                Button button = findViewById(layout.getChildAt(j).getId());
                buttons.add(button);
            }
        }

        switchSide = findViewById(R.id.switchSide);
        switchSide.setOnClickListener(this::clickSwitchSide);
        switchOpponent = findViewById(R.id.switchOpponent);

        Button restart = findViewById(R.id.restart);
        restart.setOnClickListener(this::clickOnRestart);
        restart.setText("Restart");
    }

    public boolean isWin() {
        Toast toast = Toast.makeText(
                getApplicationContext(), "",
                Toast.LENGTH_SHORT
        );
        toast.setGravity(Gravity.CENTER, 0, 0);
        if (this.logic.checkWin("X")) {
            toast.setText("Победили Крестики! Начните новую Игру!");
            toast.show();
            return true;
        } else if (this.logic.checkWin("O")) {
            toast.setText("Победили Нолики! Начните новую Игру!");
            toast.show();
            return true;
        } else if (this.logic.isFilled()) {
            toast.setText("Ничья! Начните новую Игру!");
            toast.show();
            return true;
        }
        return false;
    }

    public void clickOnButton(View view) {
        Button clicked = findViewById(view.getId());
        if (clicked.getText() != Logic.firstMark && clicked.getText() != Logic.secondMark && !isWin()) {
            clicked.setText(logic.getValue());
            logic.clickOnButton((String) clicked.getTag());
            if (!switchOpponent.isChecked() && !logic.isFilled() && !logic.checkWin(Logic.firstMark) && logic.getTurn() % 2 == 1) {
                Button button = buttons.get(logic.clickOnButtonWithAI());
                button.setText(logic.getValue());
                logic.setTurn(logic.getTurn() + 1);
            }
            isWin();
        }
    }

    public void clickOnRestart(View view) {
        for (Button v : buttons) {
            v.setText("");
        }
        logic.clearMatrix();
        if (switchSide.isChecked()) {
            logic.changeSide("O");
        } else logic.changeSide("X");
    }

    public void clickSwitchSide(View view) {
        logic.changeSide();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        for (Button v : buttons) {
            outState.putString("buttons" + v.getId(), (String) v.getText());
        }
        for (int i = 0; i < logic.getMatrix().length; i++) {
            for (int j = 0; j < logic.getMatrix().length; j++) {
                outState.putString("matrix" + ((i * logic.SIZE) + j), logic.getMatrix()[i][j]);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        for (Button v : buttons) {
            v.setText(savedInstanceState.getString("buttons" + v.getId()));
        }
        for (int i = 0; i < logic.getMatrix().length; i++) {
            for (int j = 0; j < logic.getMatrix().length; j++) {
                logic.getMatrix()[i][j] = savedInstanceState.getString("matrix" + ((i * logic.SIZE) + j));
            }
        }
    }
}