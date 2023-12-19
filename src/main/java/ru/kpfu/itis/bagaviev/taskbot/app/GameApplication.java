package ru.kpfu.itis.bagaviev.taskbot.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.kpfu.itis.bagaviev.taskbot.controlllers.GameController;
import ru.kpfu.itis.bagaviev.taskbot.game.EggCatcherGame;


public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/game.fxml"));
        Parent parent = loader.load();
        GameController gameController = loader.getController();

        Scene scene = new Scene(parent);

        EggCatcherGame eggCatcherGame = new EggCatcherGame(gameController);
        eggCatcherGame.start();

        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case Q -> eggCatcherGame.changeWolfState(EggCatcherGame.STATE_UP_LEFT);
                case W -> eggCatcherGame.changeWolfState(EggCatcherGame.STATE_UP_RIGHT);
                case A -> eggCatcherGame.changeWolfState(EggCatcherGame.STATE_DOWN_LEFT);
                case S -> eggCatcherGame.changeWolfState(EggCatcherGame.STATE_DOWN_RIGHT);
            }
        });

        primaryStage.setTitle("Ну погоди");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
