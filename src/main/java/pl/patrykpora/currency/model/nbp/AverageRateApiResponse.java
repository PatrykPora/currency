package pl.patrykpora.currency.model.nbp;

import lombok.Data;

import java.util.ArrayList;

@Data
public class AverageRateApiResponse {
    private ArrayList<AverageRate> rates;
}
