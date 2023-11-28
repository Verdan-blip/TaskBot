package ru.kpfu.itis.bagaviev.taskbot.exchange_rate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExchangeRateService {

    private static final String API_KEY = "841023613a068a799b6e6080dfbe22dd";
    private static final String REQUEST_FORMAT = "https://currate.ru/api/?get=rates&pairs=%s,%s&key=%s";

    private String readRowExchangeRate(String currency1, String currency2) {
        String request = String.format(REQUEST_FORMAT, currency1, currency2, API_KEY);
        try {
            URL url = new URI(request).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try (InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream())) {
                try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    connection.disconnect();
                    return stringBuilder.toString();
                }
            }
        } catch (URISyntaxException | IOException exception) {
            return null;
        }
    }

    public ExchangeRateInfo getExchangeRate(String currency1, String currency2) {
        Pattern pattern = Pattern.compile(":\"([0-9.]+)\"");
        String rowCurrencyData = readRowExchangeRate(currency1, currency2);
        if (rowCurrencyData == null) return null;
        Matcher matcher = pattern.matcher(rowCurrencyData);
        if (matcher.find()) {
            return new ExchangeRateInfo(currency2, Double.parseDouble(matcher.group(1)));
        }
        return null;
    }

}
