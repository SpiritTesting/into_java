package com.spirittesting.training.intojava.level5.currency;

import java.util.HashMap;
import java.util.Map;

public enum Währung {

    EUR("Euro", new double[]{1.0, 0.9, 1.15}),
    GBP("Britische Pfund", new double[]{1.1, 1.0, 1.3}),
    USD("US-Dollar", new double[]{0.85, 0.75, 1.0});

    private String name;
    private Map<String, Double> wechselkurse = new HashMap<>();

    Währung(String name, double[] kurse) {
        this.name = name;
        wechselkurse.put("EUR", kurse[0]);
        wechselkurse.put("GBP", kurse[1]);
        wechselkurse.put("USD", kurse[2]);
    }

    public double to(Währung währung) {
        return wechselkurse.get(währung.name());
    }

    public void setWechselkurs(Währung währung, double kurs) {
        wechselkurse.put(währung.name(), kurs);
    }

}
