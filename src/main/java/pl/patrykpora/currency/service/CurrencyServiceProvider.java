package pl.patrykpora.currency.service;

import pl.patrykpora.currency.model.nbp.AverageRate;
import pl.patrykpora.currency.model.nbp.MaxAndMinAverageRateForCurrency;

import java.time.LocalDate;

public interface CurrencyServiceProvider {

    AverageRate getAverageExchangeRate(String currencyCode, LocalDate date);
    MaxAndMinAverageRateForCurrency getMinAndMaxAverageRate(String currencyCode, Integer numberOfQuotations);
    Double getMajorDifferenceBuyAskRate(String currencyCode, Integer numberOfQuotations);

}
