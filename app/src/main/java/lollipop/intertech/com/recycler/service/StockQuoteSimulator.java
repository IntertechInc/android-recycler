package lollipop.intertech.com.recycler.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lollipop.intertech.com.recycler.domain.Quote;

/**
 * Created by jwhite on 12/7/2014.
 */
public class StockQuoteSimulator {

    private static Random generator = new Random();
    //private String[] mnCompanySymbols = {"MMM", "ATRM", "ALE", "ATK", "AMP", "APOG", "ARCI", "ACAT", "BBY", "TECH", "BWLD", "CHRW", "CPHC", "CPLA", "CSII", "CBK", "CHSCM", "CHSCN", "CHSCO", "CHSCP", "CLFD", "JCS", "CYBE", "DAKP", "DTLK", "VCF", "VFL", "VMM", "DLX", "DGII", "DRIV", "DCI", "ECTE", "ECL", "ELSE", "EEP", "ENTG", "ETRM", "EVLV", "FICO", "DAVE", "FAST", "GK", "GIS", "GGG", "GNI", "GWGH", "FUL", "HWKN", "HMNF", "HRL", "HTCH", "IKNX", "ISNS", "IMN", "ISIG", "LACO", "MDT", "MGCD", "MOCO", "MTSC", "NSYS", "NOG", "NTIC", "JMM", "NVEC", "OTTR", "PDCO", "PJC", "PII", "QUMU", "RGS", "SCSS", "SPDC", "SPSC", "STJ", "SSYS", "SSH", "SVU", "SRDX", "TGT", "TCB", "TNC", "TRV", "TTS", "TTC", "USB", "UNH", "UPI", "VAL", "VASC", "WINA", "WSCI", "XEL"};
    private String[] mnCompanySymbols = {"MMM", "ATRM", "BBY", "TECH", "CHRW", "JCS", "DAKP", "VCF", "ECTE", "FICO"};

    public Quote generateQuote() {
        int stockChoice = generator.nextInt(mnCompanySymbols.length);
        double price = generator.nextDouble() * 2 - 1; // simulate change in price per share
        long time = System.currentTimeMillis();
        return new Quote(time, price, mnCompanySymbols[stockChoice]);
    }

    public List<Quote> initializeQuotes(int numberOfQuotes) {
        List<Quote> quotes = new ArrayList<Quote>();
        Quote q;
        int pos;
        for (int i = 0; i < numberOfQuotes; i++) {
            q = generateQuote();
            pos = quotes.indexOf(q);
            if (pos >= 0) {
                quotes.set(pos, q);
            } else {
                quotes.add(q);
            }
        }
        return quotes;
    }

}
