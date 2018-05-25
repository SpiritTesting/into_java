package com.spirittesting.training.intojava.level5.bank;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Kunde {

    private static final AtomicInteger kundennummerGenerator = new AtomicInteger();
    private final String kundennummer;
    private final Set<Konto> konten = new HashSet<>();
    private String name;

    public Kunde(String name) {
        this.kundennummer = "KDNR" + kundennummerGenerator.incrementAndGet();
        this.name = name;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Konto> getKonten() {
        return konten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Kunde that = (Kunde) o;

        return Objects.equals(this.konten, that.konten) &&
                Objects.equals(this.kundennummer, that.kundennummer) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(konten, kundennummer, name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("kundennummer = " + kundennummer)
                .add("name = " + name)
                .add("konten = " + konten)
                .toString();
    }
}
