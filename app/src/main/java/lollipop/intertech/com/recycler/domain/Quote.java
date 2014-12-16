package lollipop.intertech.com.recycler.domain;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jwhite on 12/7/2014.
 */
public class Quote {

    private long timestamp;
    private double price;
    private String symbol;

    private SimpleDateFormat dateFormat =
            new SimpleDateFormat("dd MMM yyyy HH:mm:ss:S");
    private NumberFormat dollarFormat = NumberFormat.getCurrencyInstance();

    public Quote(long timestamp, double price, String symbol) {
        this.timestamp = timestamp;
        this.price = price;
        this.symbol = symbol;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getFormattedTimestamp() {
        return dateFormat.format(new Date(getTimestamp()));
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getPrice() {
        return price;
    }

    public String getFormattedPrice() {
        return dollarFormat.format(getPrice());
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol + " @ " + getFormattedPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quote){
            return this.getSymbol().equals(((Quote)o).getSymbol());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getSymbol().hashCode();
    }
}
