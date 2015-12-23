package services;

import java.text.DecimalFormat;

/**
 * Class for money formatting
 */
public class MoneyFormat {

    private static final String CURRENCY = "€";
    private static final String MASK = "###,###,##0.00";

    /**
     * Formats double number unto currency according to default mask
     *
     * @param amount Amount to format
     * @return String
     */
    public static String setMoney(Double amount) {
        DecimalFormat formatter = new DecimalFormat(MASK);

        return formatter.format(amount) + CURRENCY;
    }
}
