package ru.kpfu.itis.bagaviev.taskbot.game;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import ru.kpfu.itis.bagaviev.taskbot.controlllers.GameController;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EggCatcherGame {

    private static final int CHICKENS_COUNT = 4;
    private static final int MAX_CHICKEN_STATE = 5;
    private static final long EGG_MOVING_DURATION = 1_000;
    private static final long CHICKEN_AWAITNG_DURATION = 7_000;

    public static final int STATE_UP_LEFT = 0;
    public static final int STATE_UP_RIGHT = 2;
    public static final int STATE_DOWN_LEFT = 1;
    public static final int STATE_DOWN_RIGHT = 3;

    private final int[] chickenStates;
    private int wolfState;
    private int score;

    private final GameController controller;
    private final Circle egg;
    private final Random random;

    public EggCatcherGame(GameController controller) {

        this.chickenStates = new int[CHICKENS_COUNT];
        this.wolfState = STATE_UP_LEFT;
        this.score = 0;

        this.controller = controller;
        this.controller.getScoreLabel().setTranslateX(320);
        this.controller.getScoreLabel().setTranslateY(100);

        this.egg = new Circle(10);
        this.egg.setFill(Color.WHITE);
        this.random = new Random();

    }

    public void start() {
        updateBasket();
        TimerTask updateChikenTimerTask = new TimerTask() {
            @Override
            public void run() {
                int chickenIndex = random.nextInt(0, CHICKENS_COUNT);
                Platform.runLater(() -> drawEgg(chickenIndex));
                while (chickenStates[chickenIndex] != MAX_CHICKEN_STATE) {
                    chickenStates[chickenIndex]++;
                    Platform.runLater(() -> updateEgg(chickenIndex));
                    try {
                        Thread.sleep(EGG_MOVING_DURATION);
                    } catch (InterruptedException exception) {
                        throw new RuntimeException(exception);
                    }
                }
                chickenStates[chickenIndex] = 0;
                Platform.runLater(() -> {
                    if (chickenIndex == wolfState) {
                        score++;
                        updateScore();
                    }
                    removeEgg();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(updateChikenTimerTask, 0, CHICKEN_AWAITNG_DURATION);
    }

    public void changeWolfState(int newWolfState) {
        this.wolfState = newWolfState;
        updateBasket();
    }

    public void drawEgg(int activeChicken) {
        double eggPosX = 0; double eggPosY = 0;
        switch (activeChicken) {
            case 0 -> {
                eggPosX = 50; eggPosY = 115;
            }
            case 1 -> {
                eggPosX = 50; eggPosY = 215;
            }
            case 2 -> {
                eggPosX = 575; eggPosY = 115;
            }
            case 3 -> {
                eggPosX = 575; eggPosY = 215;
            }
        }
        egg.setCenterX(eggPosX);
        egg.setCenterY(eggPosY);
        controller.getAnchorPane().getChildren().add(egg);
    }

    public void updateEgg(int activeChicken) {
        double eggDeltaX = 0; double eggDeltaY = 0;
        switch (activeChicken) {
            case 0, 1 -> {
                eggDeltaX = 17.5; eggDeltaY = 7.5;
            }
            case 2, 3 -> {
                eggDeltaX = -17.5; eggDeltaY = 7.5;
            }
        }
        egg.setCenterX(egg.getCenterX() + eggDeltaX);
        egg.setCenterY(egg.getCenterY() + eggDeltaY);
    }

    public void removeEgg() {
        controller.getAnchorPane().getChildren().remove(egg);
    }

    public void updateBasket() {
        double basketPosX = 0; double basketPosY = 0;
        switch (wolfState) {
            case STATE_UP_LEFT -> {
                basketPosX = 50;
                basketPosY = 100;
            }
            case STATE_UP_RIGHT -> {
                basketPosX = 400;
                basketPosY = 100;
            }
            case STATE_DOWN_LEFT -> {
                basketPosX = 50;
                basketPosY = 225;
            }
            case STATE_DOWN_RIGHT -> {
                basketPosX = 400;
                basketPosY = 225;
            }
        }
        this.controller.getImageViewBasket().setTranslateX(basketPosX);
        this.controller.getImageViewBasket().setTranslateY(basketPosY);
    }

    public void updateScore() {
        controller.getScoreLabel().setText(String.format("Score: %d", score));
    }

}
