package com.spirittesting.training.intojava.level5.currency;

import java.util.Objects;

public class Wechselkurs implements WechselkursInterface {
//    Betrag gebeBetragEUR=new Betrag(0,0,Währung.EUR );
//    Betrag gebeBetragUSD=new Betrag(0,0,Währung.USD );
//    Betrag gebeBetragGBP=new Betrag(0,0,Währung.GBP );
//
//    Betrag bekommeBetragEUR=new Betrag(0,0,Währung.EUR );
//    Betrag bekommeBetragUSD=new Betrag(0,0,Währung.USD );
//    Betrag bekommeBetragGBP=new Betrag(0,0,Währung.GBP );



    private double wechselkursEURUSD;
    private double wechselkursEURGBP;
    private double wechselkursUSDEUR;
    private double wechselkursUSDGBP;
    private double wechselkursGBPEUR;
    private double wechselkursGBPUSD;

    @Override
    public double getWechselkursEURUSD() {
        return wechselkursEURUSD;
    }
    @Override
    public void setWechselkursEURUSD(double wechselkursEURUSD) {
        this.wechselkursEURUSD = wechselkursEURUSD;
    }

    @Override
    public double getWechselkursEURGBP() {
        return wechselkursEURGBP;
    }
    @Override
    public void setWechselkursEURGBP(double wechselkursEURGBP) {
        this.wechselkursEURGBP = wechselkursEURGBP;
    }

    @Override
    public double getWechselkursUSDEUR() {
        return wechselkursUSDEUR;
    }
    @Override
    public void setWechselkursUSDEUR(double wechselkursUSDEUR) {
        this.wechselkursUSDEUR = wechselkursUSDEUR;
    }

    @Override
    public double getWechselkursUSDGBP() {
        return wechselkursUSDGBP;
    }
    @Override
    public void setWechselkursUSDGBP(double wechselkursUSDGBP) {
        this.wechselkursUSDGBP = wechselkursUSDGBP;
    }

    @Override
    public double getWechselkursGBPEUR() {
        return wechselkursGBPEUR;
    }
    @Override
    public void setWechselkursGBPEUR(double wechselkursGBPEUR) {
        this.wechselkursGBPEUR = wechselkursGBPEUR;
    }

    @Override
    public double getWechselkursGBPUSD() {
        return wechselkursGBPUSD;
    }

    @Override
    public void setWechselkursGBPUSD(double wechselkursGBPUSD) {
        this.wechselkursGBPUSD = wechselkursGBPUSD;
    }

    @Override
    public String toString() {
        return "Wechselkurs{" +
                "wechselkursEURUSD=" + wechselkursEURUSD +
                ", wechselkursEURGBP=" + wechselkursEURGBP +
                ", wechselkursUSDEUR=" + wechselkursUSDEUR +
                ", wechselkursUSDGBP=" + wechselkursUSDGBP +
                ", wechselkursGBPEUR=" + wechselkursGBPEUR +
                ", wechselkursGBPUSD=" + wechselkursGBPUSD +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wechselkurs that = (Wechselkurs) o;
        return Double.compare(that.wechselkursEURUSD, wechselkursEURUSD) == 0 &&
                Double.compare(that.wechselkursEURGBP, wechselkursEURGBP) == 0 &&
                Double.compare(that.wechselkursUSDEUR, wechselkursUSDEUR) == 0 &&
                Double.compare(that.wechselkursUSDGBP, wechselkursUSDGBP) == 0 &&
                Double.compare(that.wechselkursGBPEUR, wechselkursGBPEUR) == 0 &&
                Double.compare(that.wechselkursGBPUSD, wechselkursGBPUSD) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(wechselkursEURUSD, wechselkursEURGBP, wechselkursUSDEUR, wechselkursUSDGBP, wechselkursGBPEUR, wechselkursGBPUSD);
    }
    //private double wechselkursEURUSD=Double.parseDouble(bekommeBetragUSD.toString())/Double.parseDouble(gebeBetragEUR.toString());
    //private double wechselkursEURUSD = 1.00/wechselkursUSDEUR;
    //private double wechselkursUSDEUR=1.00/wechselkursEURUSD;
    //private double wechselkursEURGBP=Double.parseDouble(bekommeBetragGBP.toString())/Double.parseDouble(gebeBetragEUR.toString());
    //private double wechselkursUSDEUR=Double.parseDouble(bekommeBetragEUR.toString())/Double.parseDouble(gebeBetragUSD.toString());
    //private double wechselkursUSDGBP =Double.parseDouble(bekommeBetragGBP.toString())/Double.parseDouble(gebeBetragUSD.toString());
    //private double wechselkursGBPEUR =Double.parseDouble(bekommeBetragEUR.toString())/Double.parseDouble(gebeBetragGBP.toString());
    //private double wechselkursGBPUSD =Double.parseDouble(bekommeBetragUSD.toString())/Double.parseDouble(gebeBetragGBP.toString());
}