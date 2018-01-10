package com.btrabucco.neuralrps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity {

    private TextView message;
    private TextView win_rate;
    private TextView loss;
    private Button rock_action;
    private Button paper_action;
    private Button scissors_action;
    private TextView player_action_5;
    private TextView player_action_4;
    private TextView player_action_3;
    private TextView player_action_2;
    private TextView player_action_1;
    private TextView robot_action_5;
    private TextView robot_action_4;
    private TextView robot_action_3;
    private TextView robot_action_2;
    private TextView robot_action_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        message = (TextView) findViewById(R.id.message);
        win_rate = (TextView) findViewById(R.id.win_rate);
        loss = (TextView) findViewById(R.id.loss);
        rock_action = (Button) findViewById(R.id.action_rock);
        paper_action = (Button) findViewById(R.id.action_paper);
        scissors_action = (Button) findViewById(R.id.action_scissors);
        player_action_5 = (TextView) findViewById(R.id.player_action_5);
        player_action_4 = (TextView) findViewById(R.id.player_action_4);
        player_action_3 = (TextView) findViewById(R.id.player_action_3);
        player_action_2 = (TextView) findViewById(R.id.player_action_2);
        player_action_1 = (TextView) findViewById(R.id.player_action_1);
        robot_action_5 = (TextView) findViewById(R.id.robot_action_5);
        robot_action_4 = (TextView) findViewById(R.id.robot_action_4);
        robot_action_3 = (TextView) findViewById(R.id.robot_action_3);
        robot_action_2 = (TextView) findViewById(R.id.robot_action_2);
        robot_action_1 = (TextView) findViewById(R.id.robot_action_1);

        rock_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.update_game(Game.ACTION_ROCK);
                updateGameText();
            }
        });
        paper_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.update_game(Game.ACTION_PAPER);
                updateGameText();
            }
        });
        scissors_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game.update_game(Game.ACTION_SCISSORS);
                updateGameText();
            }
        });

        Game.create(this);
    }

    private void setText(TextView view, String text, int status) {
        view.setText(text);
        setTextColor(view, status);
    }

    private void setTextColor(TextView view, int status) {
        if (status == Game.STATUS_WIN) {
            view.setTextColor(getResources().getColor(R.color.colorWin));
        } else if (status == Game.STATUS_TIE) {
            view.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            view.setTextColor(getResources().getColor(R.color.colorLose));
        }
    }

    private void updateGameText() {
        setText(message, Game.get_message(Game.player_turn(0)), Game.player_turn(0));
        setText(win_rate, String.format("%.2f", Game.get_win_rate()), Game.player_turn(0));
        setText(loss, String.format("%.10f", Game.tf.getLoss()), Game.player_turn(0));
        setText(player_action_1, Game.get_message(Game.get_player(0)), Game.player_turn(0));
        setText(robot_action_1, Game.get_message(Game.get_robot(0)), Game.robot_turn(0));
        setText(player_action_2, Game.get_message(Game.get_player(1)), Game.player_turn(1));
        setText(robot_action_2, Game.get_message(Game.get_robot(1)), Game.robot_turn(1));
        setText(player_action_3, Game.get_message(Game.get_player(2)), Game.player_turn(2));
        setText(robot_action_3, Game.get_message(Game.get_robot(2)), Game.robot_turn(2));
        setText(player_action_4, Game.get_message(Game.get_player(3)), Game.player_turn(3));
        setText(robot_action_4, Game.get_message(Game.get_robot(3)), Game.robot_turn(3));
        setText(player_action_5, Game.get_message(Game.get_player(4)), Game.player_turn(4));
        setText(robot_action_5, Game.get_message(Game.get_robot(4)), Game.robot_turn(4));
    }

}
