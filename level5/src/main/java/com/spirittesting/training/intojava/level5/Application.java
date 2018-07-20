package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.Bank;
import com.spirittesting.training.intojava.level5.bank.Konto;
import com.spirittesting.training.intojava.level5.bank.Kunde;
import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.Währung;
import com.spirittesting.training.intojava.terminal.Terminal;

import java.util.HashSet;
import java.util.Set;

public class Application {

    public static void main(String[] args) {
        Set<Kunde> kunden = new HashSet<Kunde>();
        Kunde kunde = new Kunde("Dagobert Duck");
        Konto konto = new Konto(Währung.USD);
        konto.setBetrag(new Betrag(120, 51, Währung.USD));
        kunde.getKonten().add(konto);
        konto = new Konto(Währung.EUR);
        konto.setBetrag(new Betrag(57182, 12, Währung.EUR));
        kunde.getKonten().add(konto);
        kunden.add(kunde);

        kunde = new Kunde("Gustav Gans");
        konto = new Konto(Währung.EUR);
        konto.setBetrag(new Betrag(2, 81, Währung.EUR));
        kunde.getKonten().add(konto);
        kunden.add(kunde);

        kunde = new Kunde("Donald Duck");
        konto = new Konto(Währung.USD);
        konto.setBetrag(new Betrag(227275123, 99, Währung.USD));
        kunde.getKonten().add(konto);
        kunden.add(kunde);

        Bank bank = new Bank(kunden);
        Terminal terminal = new Terminal("Spirit Banking");
        BankCommands commands = new BankCommands(bank, terminal);

    }

}
