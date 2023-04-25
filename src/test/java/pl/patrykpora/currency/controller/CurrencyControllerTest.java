package pl.patrykpora.currency.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.patrykpora.currency.CurrencyApplication;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CurrencyApplication.class)
@AutoConfigureMockMvc
class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnAverageExchangeRateWhenGetAverageExchangeRate() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/averageExchangeRate?currencyCode=eur&date=2023-04-25")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4.598")))
                .andDo(print());
    }

    @Test
    void shouldReturnMinAndMaxAverageRateForGivenQuotations() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/maxAndMinAverageRate?currencyCode=eur&numberOfQuotations=50")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("4.7358")))
                .andExpect(content().string(containsString("4.5756")))
                .andDo(print());
    }

    @Test
    void shouldReturnMajorDifferenceForBuyAskRateOfGivenQuotations() throws Exception {
            this.mvc.perform(MockMvcRequestBuilders.get("/majorDifferenceBetweenBuyAskRate?currencyCode=eur&numberOfQuotations=100")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("0.0966")))
                    .andDo(print());
    }

}