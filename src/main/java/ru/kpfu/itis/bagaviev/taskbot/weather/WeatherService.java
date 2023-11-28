package ru.kpfu.itis.bagaviev.taskbot.weather;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeatherService {

    private static final String API_KEY = "00a613d23af323d8eb8e2522020e9fdc";
    private static final String REQUEST_FORMAT = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    private String readRowWeather(String city) {
        String request = String.format(REQUEST_FORMAT, city, API_KEY);
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

    public WeatherInfo getCurrentWeather(String city) {
        Pattern pattern = Pattern.compile("\"description\":\"(.*?)\".*\"temp\":(.*?),.*\"humidity\":(.*?),.*\"country\":\"(.*?)\"");
        String rowWeatherInfo = readRowWeather(city);
        if (rowWeatherInfo == null) return null;
        Matcher matcher = pattern.matcher(rowWeatherInfo);
        if (matcher.find()) {
            return new WeatherInfo(matcher.group(1), Double.parseDouble(matcher.group(2)), Double.parseDouble(matcher.group(3)));
        }
        return null;
    }

}
