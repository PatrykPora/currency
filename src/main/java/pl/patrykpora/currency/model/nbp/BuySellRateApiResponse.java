package pl.patrykpora.currency.model.nbp;

import lombok.Data;

import java.util.List;

@Data
public class BuySellRateApiResponse {
    private List<BuySellRate> rates;
}
