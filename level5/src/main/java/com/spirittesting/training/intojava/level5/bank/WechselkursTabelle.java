package com.spirittesting.training.intojava.level5.bank;

import com.spirittesting.training.intojava.level5.currency.Wechselkurs;
import com.spirittesting.training.intojava.level5.currency.WechselkursInterface;
import com.spirittesting.training.intojava.level5.currency.Währung;


import java.util.HashMap;

public class WechselkursTabelle implements WechselkursInterface {

    private final HashMap<String, Double> umrechnungsKurse = new HashMap<String, Double>();

    public void setWechselkurs(Währung w1, Währung w2, double kurs) {

        String key = buildWährungKey(w1,w2);
        umrechnungsKurse.put(key,kurs);

        // rueckrichtung
        double reziprok = 1. / kurs ;
        String reziprokKey = buildWährungKey(w2, w1);
        umrechnungsKurse.put(reziprokKey, reziprok);
    }

    public double getWechselkurs(Währung w1, Währung w2) {
        String key = buildWährungKey(w1,w2);

            double wechselkurs= umrechnungsKurse.get(key);

        return wechselkurs;
    }

    private  String buildWährungKey(Währung w1, Währung w2){
        String result= w1.toString()+"_"+w2.toString();
        // System.out.println("Währungskey: "+result);
        return result;
    }


    @Override
    public double getWechselkursEURUSD() {
        return getWechselkurs(Währung.EUR, Währung.USD);
    }

    @Override
    public void setWechselkursEURUSD(double wechselkursEURUSD) {
        setWechselkurs(Währung.EUR, Währung.USD, wechselkursEURUSD);
    }

    @Override
    public double getWechselkursEURGBP() {
        return getWechselkurs(Währung.EUR, Währung.GBP);
    }

    @Override
    public void setWechselkursEURGBP(double wechselkursEURGBP) {
        setWechselkurs(Währung.EUR, Währung.USD, wechselkursEURGBP);
    }

    @Override
    public double getWechselkursUSDEUR() {
        return getWechselkurs(Währung.USD, Währung.EUR);
    }

    @Override
    public void setWechselkursUSDEUR(double wechselkursUSDEUR) {
        setWechselkurs(Währung.USD, Währung.EUR, wechselkursUSDEUR);
    }

    @Override
    public double getWechselkursUSDGBP() {
        return getWechselkurs(Währung.USD, Währung.GBP);
    }

    @Override
    public void setWechselkursUSDGBP(double wechselkursUSDGBP) {

    }

    @Override
    public double getWechselkursGBPEUR() {
        return getWechselkurs(Währung.GBP, Währung.EUR);
    }

    @Override
    public void setWechselkursGBPEUR(double wechselkursGBPEUR) {
        setWechselkurs(Währung.EUR, Währung.USD, wechselkursGBPEUR);
    }

    @Override
    public double getWechselkursGBPUSD() {
        return getWechselkurs(Währung.GBP, Währung.USD);
    }

    @Override
    public void setWechselkursGBPUSD(double wechselkursGBPUSD) {
        setWechselkurs(Währung.GBP, Währung.USD, wechselkursGBPUSD);
    }
}
