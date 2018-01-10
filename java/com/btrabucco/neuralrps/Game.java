package com.btrabucco.neuralrps;

import android.app.Activity;

import org.tensorflow.Tensor;

/**
 * Created by brand on 1/4/2018.
 */

public class Game {

    public static final int GAME_HISTORY = 5;
    public static final int ACTION_ROCK = 3;
    public static final int ACTION_PAPER = 2;
    public static final int ACTION_SCISSORS = 1;
    public static final int STATUS_WIN = -1;
    public static final int STATUS_TIE = -2;
    public static final int STATUS_LOSE = -3;
    public static int wins = 0;
    public static int losses = 0;
    public static int ties = 0;
    public static final int[] player_state = new int[] {0, 0, 0, 0, 0};
    public static final int[] robot_state = new int[] {0, 0, 0, 0, 0};
    public static TensorFlowEngine tf;

    public static void create(Activity a) {
        tf = new TensorFlowEngine(a, "tf_graph.proto");
        tf.init();
    }

    private static Tensor one_hot(int move) {
        if (move == ACTION_ROCK) {
            return Tensor.create(new float[][] {{1.0f}, {0}, {0}});
        } else if (move == ACTION_PAPER) {
            return Tensor.create(new float[][] {{0}, {1.0f}, {0}});
        } else {
            return Tensor.create(new float[][] {{0}, {0}, {1.0f}});
        }
    }

    private static int lookup(float[] move) {
        int index = 0;
        for (int i = 0; i < move.length; i++) {
            if (move[index] < move[i]) {
                index = i;
            }
        }
        if (index == 0) {
            return ACTION_ROCK;
        } else if (index == 1) {
            return ACTION_PAPER;
        } else {
            return ACTION_SCISSORS;
        }
    }

    private static int robot_move(int player_next) {
        tf.train(
                one_hot(player_state[0]),
                one_hot(wins_by(player_next))
        );
        return lookup(tf.getOutput());
    }

    public static void update_game(int player_move) {
        for (int i = GAME_HISTORY - 1; i > 0; i--) {
            player_state[i] = player_state[i - 1];
            robot_state[i] = robot_state[i - 1];
        }
        robot_state[0] = robot_move(player_move);
        player_state[0] = player_move;
        switch (player_status(player_state[0], robot_state[0])) {
            case STATUS_WIN:
                wins++;
                break;
            case STATUS_TIE:
                ties++;
                break;
            case STATUS_LOSE:
                losses++;
                break;
        }
    }

    public static int get_player(int turn) {
        return  player_state[turn];
    }

    public static int get_robot(int turn) {
        return  robot_state[turn];
    }

    public static float get_win_rate() {
        return ((float)wins) / ((float)(wins + ties + losses)) * 100.0f;
    }

    private static int player_status(int player_move, int robot_move) {
        if (robot_move == loses_by(player_move)) {
            return STATUS_WIN;
        } else if (robot_move == player_move) {
            return STATUS_TIE;
        } else {
            return STATUS_LOSE;
        }
    }

    private static int robot_status(int player_move, int robot_move) {
        if (robot_move == loses_by(player_move)) {
            return STATUS_LOSE;
        } else if (robot_move == player_move) {
            return STATUS_TIE;
        } else {
            return STATUS_WIN;
        }
    }

    public static int player_turn(int turn) {
        return Game.player_status(player_state[turn], robot_state[turn]);
    }

    public static int robot_turn(int turn) {
        return Game.robot_status(player_state[turn], robot_state[turn]);
    }

    public static String get_message(int reference) {
        switch (reference) {
            case ACTION_ROCK:
                return "Rock";
            case ACTION_PAPER:
                return "Paper";
            case ACTION_SCISSORS:
                return "Scissors";
            case STATUS_WIN:
                return "Win :)";
            case STATUS_TIE:
                return "Tie :|";
            case STATUS_LOSE:
                return "Lose :(";
        }
        return "";
    }

    private static int loses_by(int move) {
        if (move == ACTION_ROCK) {
            return ACTION_SCISSORS;
        } else if (move == ACTION_PAPER) {
            return ACTION_ROCK;
        } else {
            return ACTION_PAPER;
        }
    }

    private static int wins_by(int move) {
        if (move == ACTION_ROCK) {
            return ACTION_PAPER;
        } else if (move == ACTION_PAPER) {
            return ACTION_SCISSORS;
        } else {
            return ACTION_ROCK;
        }
    }

}
