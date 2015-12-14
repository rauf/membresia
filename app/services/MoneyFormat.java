package services;


import java.text.DecimalFormat;

public class MoneyFormat {

    private static final String CURRENCY = "€";
    private static final String MASK = "###,###,##0.00";

    public static String setMoney(Double amount) {
        DecimalFormat formatter = new DecimalFormat(MASK);
        return formatter.format(amount) + CURRENCY;
    }
}
