module ru.kpfu.itis.bagaviev.taskbot {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.kpfu.itis.bagaviev.taskbot to javafx.fxml;
    //exports ru.kpfu.itis.bagaviev.taskbot;
    exports ru.kpfu.itis.bagaviev.taskbot.weather;
    opens ru.kpfu.itis.bagaviev.taskbot.weather to javafx.fxml;
    exports ru.kpfu.itis.bagaviev.taskbot.app;
    opens ru.kpfu.itis.bagaviev.taskbot.app to javafx.fxml;
    exports ru.kpfu.itis.bagaviev.taskbot.controlllers;
    opens ru.kpfu.itis.bagaviev.taskbot.controlllers to javafx.fxml;
}