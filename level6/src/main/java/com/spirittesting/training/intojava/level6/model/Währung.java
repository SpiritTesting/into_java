package com.spirittesting.training.intojava.level6.model;

public enum Währung {

    EUR("Euro"),
    GBP("Britische Pfund"),
    USD("US-Dollar");

    private String name;

    Währung(String name) {
        this.name = name;
    }

}
