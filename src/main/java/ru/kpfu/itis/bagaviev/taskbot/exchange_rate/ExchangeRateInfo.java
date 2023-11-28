package ru.kpfu.itis.bagaviev.taskbot.exchange_rate;

public record ExchangeRateInfo(String currency, double value) {

    @Override
    public String toString() {
        return String.format("Currency (%s) = %f", currency, value);
    }
}
