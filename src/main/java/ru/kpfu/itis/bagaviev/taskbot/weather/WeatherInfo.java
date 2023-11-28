package ru.kpfu.itis.bagaviev.taskbot.weather;

public record WeatherInfo(String description, double currentTemperature, double humidity) {

    @Override
    public String toString() {
        return String.format("Description = %s\n" +
                "CurrentTemperature = %f\n" +
                "Humidity = %f", description, currentTemperature, humidity);
    }

}
