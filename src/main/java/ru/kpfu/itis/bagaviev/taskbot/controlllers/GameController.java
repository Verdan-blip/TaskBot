package ru.kpfu.itis.bagaviev.taskbot.controlllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ImageView imageViewBasket;

    @FXML
    private Label labelScore;

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public ImageView getImageViewBasket() {
        return imageViewBasket;
    }

    public Label getScoreLabel() {
        return labelScore;
    }

}
