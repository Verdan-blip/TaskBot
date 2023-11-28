package ru.kpfu.itis.bagaviev.taskbot.controlllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.kpfu.itis.bagaviev.taskbot.app.ChatApplication;
import ru.kpfu.itis.bagaviev.taskbot.chat.views.UserConfigView;
import ru.kpfu.itis.bagaviev.taskbot.exchange_rate.ExchangeRateInfo;
import ru.kpfu.itis.bagaviev.taskbot.exchange_rate.ExchangeRateService;
import ru.kpfu.itis.bagaviev.taskbot.weather.WeatherInfo;
import ru.kpfu.itis.bagaviev.taskbot.weather.WeatherService;

public class Controller {

    @FXML
    private TextField tfCommand;

    @FXML
    private TextArea taChat;

    @FXML
    public void onRunButtonClick() {
        String text = tfCommand.getText();
        if (text.startsWith("/weather")) {
            String city = text.split("\\s+")[1];
            WeatherService service = new WeatherService();
            WeatherInfo weatherInfo = service.getCurrentWeather(city);
            if (weatherInfo == null) {
                taChat.appendText("Ошибка!\n");
            } else {
                taChat.appendText(weatherInfo + "\n");
            }
        } else if (text.startsWith("/currency")) {
            String[] split = text.split("\\s+");
            String currency1 = split[1];
            String currency2 = split[2];
            ExchangeRateService service = new ExchangeRateService();
            ExchangeRateInfo exchangeRateInfo = service.getExchangeRate(currency1, currency2);
            if (exchangeRateInfo == null) {
                taChat.appendText("Ошибка!\n");
            } else {
                taChat.appendText(exchangeRateInfo + "\n");
            }
        } else if (text.startsWith("/help")) {
            taChat.appendText("/weather {city} - выводит текущую погоду в данном городе\n");
            taChat.appendText("/currency {currency_1} {currency_2} - выводит текущий курс валюты\n");
            taChat.appendText("/chat - создает чат");
            taChat.appendText("/help - выводит информацию о всех командах\n");
        } else if (text.startsWith("/chat")) {
            ChatApplication chatApplication = new ChatApplication();
            chatApplication.startChat();
        }
        taChat.appendText("<---------------------------->\n");
    }

}