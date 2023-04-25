package pl.patrykpora.currency.model.nbp;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MaxAndMinAverageRateForCurrency {
    private double max;
    private double min;
}
