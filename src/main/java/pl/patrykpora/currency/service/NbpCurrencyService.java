package pl.patrykpora.currency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.patrykpora.currency.model.nbp.*;

import java.time.LocalDate;
import java.util.List;

@Service
public class NbpCurrencyService implements CurrencyServiceProvider{

    private static final Logger logger = LoggerFactory.getLogger(NbpCurrencyService.class);
    @Value("${nbpCurrencyService.baseUrlAverageRate}")
    public String baseUrlAverageRate;

    @Value("${nbpCurrencyService.baseUrlForRatesBetweenDates}")
    public String baseUrlForRatesBetweenDates;
    @Override
    public AverageRate getAverageExchangeRate(String currencyCode, LocalDate date) {
        String url = baseUrlAverageRate.replace("{table}", "A")
                             .replace("{currencyCode}", currencyCode)
                             .replace("{date}", date.toString());
        logger.info("created url to query nbp service {}", url);
        RestTemplate restTemplate = new RestTemplate();
        AverageRateApiResponse apiResponse = restTemplate.getForObject(url, AverageRateApiResponse.class);
        return apiResponse.getRates().stream().findFirst().get();
    }

    @Override
    public MaxAndMinAverageRateForCurrency getMinAndMaxAverageRate(String currencyCode, Integer numberOfQuotations) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(367);
        String url = baseUrlForRatesBetweenDates.replace("{table}", "A")
                             .replace("{currencyCode}", currencyCode)
                             .replace("{startDate}", startDate.toString())
                             .replace("{endDate}", today.toString());
        logger.info("created url to query nbp service {}", url);
        RestTemplate restTemplate = new RestTemplate();
        AverageRateApiResponse apiResponse = restTemplate.getForObject(url, AverageRateApiResponse.class);
        List<AverageRate> ratesList = apiResponse.getRates();

        if (ratesList.size() > numberOfQuotations) {
            ratesList.subList(numberOfQuotations, ratesList.size()).clear();
        }
        Double max = ratesList.stream().map(AverageRate::getMid).max(Double::compare).get();
        Double min = ratesList.stream().map(AverageRate::getMid).min(Double::compare).get();
        return new MaxAndMinAverageRateForCurrency(max, min);
    }

    @Override
    public Double getMajorDifferenceBuyAskRate(String currencyCode, Integer numberOfQuotations) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(367);
        String url = baseUrlForRatesBetweenDates.replace("{table}", "C")
                             .replace("{currencyCode}", currencyCode)
                             .replace("{startDate}", startDate.toString())
                             .replace("{endDate}", today.toString());
        logger.info("created url to query nbp service {}", url);
        RestTemplate restTemplate = new RestTemplate();
        BuySellRateApiResponse apiResponse = restTemplate.getForObject(url, BuySellRateApiResponse.class);
        List<BuySellRate> ratesList = apiResponse.getRates();

        if (ratesList.size() > numberOfQuotations) {
            ratesList.subList(numberOfQuotations, ratesList.size()).clear();
        }
        double majorDifference = ratesList.stream().map(rate -> rate.getAsk() - rate.getBid()).max(Double::compare).get();
        return Math.round(majorDifference * 10000)/10000.0;
    }
}
