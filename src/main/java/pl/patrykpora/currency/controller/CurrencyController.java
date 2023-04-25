package pl.patrykpora.currency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.patrykpora.currency.model.nbp.AverageRate;
import pl.patrykpora.currency.model.nbp.MaxAndMinAverageRateForCurrency;
import pl.patrykpora.currency.service.CurrencyServiceProvider;

import java.time.LocalDate;


@RestController
@Tag(name = "Currency Data")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private final CurrencyServiceProvider currencyServiceProvider;

    public CurrencyController(@Qualifier("nbpCurrencyService") final CurrencyServiceProvider currencyServiceProvider) {
        this.currencyServiceProvider = currencyServiceProvider;
    }

    @GetMapping("/averageExchangeRate")
    @Operation(summary = "get average exchange rate for currency and date")
    public AverageRate getAverageExchangeRate(@RequestParam("currencyCode") String currencyCode,
                                              @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        logger.info("received request for average exchange rate for {} and date {}", currencyCode, date.toString());
        return currencyServiceProvider.getAverageExchangeRate(currencyCode, date);
    }


    @GetMapping("/maxAndMinAverageRate")
    @Operation(summary = "get the max and min average value of given quotations")
    public MaxAndMinAverageRateForCurrency getMinAndMaxAverageRate(@RequestParam("currencyCode") String currencyCode,
                                                                   @RequestParam("numberOfQuotations") Integer numberOfQuotations) {
        logger.info("received request for max and min average rate for {} and number of quotations {}",
                currencyCode, numberOfQuotations);

        return currencyServiceProvider.getMinAndMaxAverageRate(currencyCode, numberOfQuotations);
    }


    @GetMapping("/majorDifferenceBetweenBuyAskRate")
    @Operation(summary = "get the major difference between the buy and ask rate of given quotations")
    public Double getMajorDifferenceBuyAskRate(@RequestParam("currencyCode") String currencyCode,
                                               @RequestParam("numberOfQuotations") Integer numberOfQuotations) {
        logger.info("received request for major difference between buy and ask rate for {} and last {} quotations",
                currencyCode, numberOfQuotations);

        return currencyServiceProvider.getMajorDifferenceBuyAskRate(currencyCode, numberOfQuotations);
    }


}
