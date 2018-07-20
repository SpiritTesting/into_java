package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.*;
import com.spirittesting.training.intojava.level5.currency.*;
import com.spirittesting.training.intojava.terminal.CommandInterpreter;
import com.spirittesting.training.intojava.terminal.Terminal;
import sun.security.provider.certpath.SunCertPathBuilderResult;
//import java.util.terminal;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BankCommands implements CommandInterpreter {

    private Bank bank;
    private Terminal terminal;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public BankCommands(Bank bank, Terminal terminal) {
        this.bank = bank;
        this.terminal = terminal;
        terminal.setCommandInterpreter(this);
    }

    @Override
    public void interpret(String commandString) {
        Queue<String> command = new LinkedList<>(Arrays.asList(commandString.trim().split("\\s+")));
        if (command.isEmpty()) {
            help(command);
            return;
        }
        switch (command.poll()) {
            case "help":
            default:
                help(command);
                break;
            case "list":
                list(command);
                break;
            case "add":
                add(command);
                break;
            case "remove":
                remove(command);
                break;
            case "transfer":
                transfer(command);
                break;
            case "withdraw":
                withdraw(command);
                break;
            case "deposit":
                deposit(command);
                break;
            case "show":
                show(command);
                break;
            case "moves":
                moves (command);
                break;
            case "spar":
                spar (command);
                break;
            case "changes":
                changes(command);
                break;
            case "setchanges":
                setchanges(command);
                break;
        }
    }

    private void help(Queue<String> params) {
        if (params.isEmpty()) {
            terminal.print("available commands:");
            terminal.print("list [kundennummer]?");
            terminal.print("add [kunde|konto]");
            terminal.print("remove [kunde|konto]");
            terminal.print("transfer kontoVon kontoNach betrag");
            terminal.print("deposit kundennummer konto betrag");
            terminal.print("withdraw kundennummer konto betrag");
            terminal.print("show kundennummer");
            terminal.print("moves kontonummer");
            terminal.print("spar kundennummer");
            terminal.print("changes ausgangswährung");
            terminal.print("setchanges ausgangswährung fremdwährung wechselkurs");
            terminal.print("type 'help topic' to view detailed help for topic");
        }
    }

    private void list(Queue<String> command) {
        if (command.isEmpty()) {
            terminal.print("Kundennummern:");
            for (String kundennummer : bank.listKunden()) {
                terminal.print("\t" + kundennummer);
            }
        } else {
            String kundennummer = command.poll();
            Kunde kunde = bank.getKunde(kundennummer);
            if (kunde == null) {
                terminal.print("Kundennummer nicht bekannt");
            } else {
                terminal.print("Kundennummer: " + kunde.getKundennummer());
                terminal.print("Name        : " + kunde.getName());
                for (Konto konto : kunde.getKonten()) {
                    terminal.print("\tKonto #" + konto.getKontonummer() + ": " + konto.getBetrag().toString());
                }
            }
        }
    }

    private void add(Queue<String> command) {
        try {
            switch (command.poll()) {
                case "kunde":
                    if (command.isEmpty()) terminal.print("\t add kunde <name>");
                    else {
                        String name = command.stream().collect(Collectors.joining(" "));
                        Kunde kunde = bank.addKunde(name);
                        terminal.print("Kundennummer: " + kunde.getKundennummer());
                    }
                    break;
                case "konto":
                    if (command.isEmpty()) terminal.print("\t add konto <kundennummer> <EUR|USD|GBP>");
                    else {
                        String kundennummer = command.poll();
                        String währung = command.poll();
                        Kunde kunde = bank.getKunde(kundennummer);
                        Konto konto = new Konto(Währung.valueOf(währung));
                        kunde.getKonten().add(konto);
                        terminal.print("Kontonummer: " + konto.getKontonummer());
                    }
                    break;
                default:
                    throw new NullPointerException("Falsche Eingabe");
            }
        } catch (NullPointerException e) {
            terminal.print("Befehl unvollständig. Mögliche Formen:");
            terminal.print("\tadd kunde <name>");
            terminal.print("\tadd konto <kundennummer> <EUR|USD|GBP>");
        }
    }

    private void remove(Queue<String> command) //throws KontoNichtAusgeglichenException {
    {
        try {
            switch (command.poll()) {
                case "kunde":
                    if (command.isEmpty()) terminal.print("\t remove kunde <kundennummer>");
                    else {
                        String name = command.poll();
                        //String name = command.stream().collect(Collectors.joining(" "));
                        //String kundennummer = command.stream().collect(Collectors.joining(" "));
                        Kunde kunde = bank.getKunde(name);
                        String kundennummer = kunde.getKundennummer();
                        //String kdnr = kunde.getKundennummer();
                        bank.removeKunde(kundennummer);
                        //kunde.getKonten().remove(konto);
                        terminal.print("Kundennummer: " + kunde.getKundennummer() + " wurde gelöscht");
                    }
                    break;
                case "konto":
                    if (command.isEmpty()) terminal.print("\t remove konto <kundennummer> <EUR|USD|GBP> <kontonummer>");
                    else {
                        //String kundennummer = command.stream().collect(Collectors.joining(" "));
                        String kundennummer = command.poll();
                        String währung = command.poll();
                        String kontonummer = command.poll();
                        Kunde kunde = bank.getKunde(kundennummer);
                        Konto konto = new Konto(Währung.valueOf(währung));
                        //Konto kontok = new Konto().getKontonummer().
                        bank.getKonten(kundennummer);
                        kunde.getKonten().remove(konto);
                        bank.removeKunde(kundennummer);
                        terminal.print("Kontonummer: " + konto.getKontonummer() + " und der Kunde: " + kundennummer + " wurde gelöscht");
                    }
                    break;
                default:
                    throw new NullPointerException("Falsche Eingabe");
            }
        } catch (NullPointerException e) {
            terminal.print("Befehl unvollständig. Mögliche Formen:");
            terminal.print("\tremove kunde <name>");
            terminal.print("\tremove konto <kundennummer> <EUR|USD|GBP>");
        } catch (KontoNichtAusgeglichenException k) {
            terminal.print("Konto nicht ausgeglichen");


        }

    }

    private void transfer(Queue<String> command) {
        try {
            Konto kontoVon = bank.getKonto(command.poll());
            Konto kontoNach = bank.getKonto(command.poll());
            Betrag betrag = new Betrag(command.poll());
            bank.überweise(kontoVon, kontoNach, betrag);
            if (betrag.equals(0)) {terminal.print("Hier");
            }
        } catch (NullPointerException e) {
            terminal.print("Befehl unvollständig.");
            terminal.print("\ttransfer <kontoVon> <kontoNach> <betrag>");
            terminal.print("\ttransfer 00000001 00000002 EUR10.00");
        } catch (KontostandNichtAusreichendException e) {
            terminal.print("Unzureichende Deckung");
        } catch (BetragException e) {
            terminal.print("Quell- und Zielkonto müssen in derselben Währung geführt werden");
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }

    private void withdraw(Queue<String> command) {
        try {
            Kunde kunde = bank.getKunde(command.poll());
            Konto konto = bank.getKonto(command.poll());
            Betrag betrag = new Betrag(command.poll());
            bank.zahleAus(kunde, konto, betrag);
        } catch (NullPointerException e) {
            terminal.print("Befehl unvollständig.");
            terminal.print("\twithdraw <kundennummer> <kontonummer> <betrag>");
        } catch (KontostandNichtAusreichendException e) {
            terminal.print("Unzureichende Deckung");
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }

    private void deposit(Queue<String> command) {
        try {
            Kunde kunde = bank.getKunde(command.poll());
            Konto konto = bank.getKonto(command.poll());
            Betrag betrag = new Betrag(command.poll());
            bank.zahleEin(kunde, konto, betrag);
        } catch (NullPointerException e) {
            terminal.print("Befehl unvollständig.");
            terminal.print("\t deposit <kundennummer> <kontonummer> <betrag>");
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }

    private void show(Queue<String> command) {
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {
            if (command.isEmpty()) terminal.print("\t show <kundennummer>");
            else {
                String kundennummer = command.poll();
                Kunde kunde = bank.getKunde(kundennummer);
                for (Konto konto : kunde.getKonten()) {
                    terminal.print(kunde.getName());
                    terminal.print("Konto: " + konto.getKontonummer());
                    terminal.print("Betrag: " + konto.getBetrag().toString());
                    /*if (konto.getBetrag().getWährung()==Währung.EUR) {
                        if (konto.getBetrag().getWährung()==Währung.EUR) {
                            if (konto.getBetrag().getWährung()==Währung.EUR) {
                                String ersparnisEUR =
                            }
                        }
                    }*/
                    terminal.print("Betrag: " + konto.getBetrag().getWährung().toString());
                }
                    if (bank.getEinzahlungen().containsKey(kundennummer) ){
                        terminal.print("Einzahlung: "+bank.getEinzahlungen().toString());
                    }
                    else terminal.print("Keine Einzahlungen des Kunden vorhanden");

                    if (bank.getAuszahlungen().containsKey(kundennummer)){
                        terminal.print("Auszahlung: "+bank.getAuszahlungen().toString());
                    }
                    else terminal.print("Keine Auszahlungen des Kunden vorhanden");
            }
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }

private void spar(Queue<String> command) {
    try {
        if (command.isEmpty()) terminal.print("\t spar <kundennummer>");
        else {
            String kundennummer = command.poll();
            Kunde kunde = bank.getKunde(kundennummer);
            Betrag summeEUR = new Betrag(0, 0, Währung.EUR);
            Betrag summeUSD = new Betrag(0, 0, Währung.USD);
            Betrag summeGBP = new Betrag(0, 0, Währung.GBP);
            for (Konto konto : kunde.getKonten()) {
                Betrag currentBetrag = konto.getBetrag();
                if (currentBetrag.getWährung() == Währung.EUR) {
                    // summeEUR.addiere(currentBetrag);
                    summeEUR = summeEUR.addiere(currentBetrag);
                }
                if (currentBetrag.getWährung() == Währung.USD) {
                    summeUSD = summeUSD.addiere(currentBetrag);
                }
                if (currentBetrag.getWährung() == Währung.GBP) {
                    summeGBP = summeGBP.addiere(currentBetrag);
                }
               /* } else if (konto.getBetrag().getWährung() == Währung.USD) {
                    String ersparnisUSD = konto.getBetrag().toString();
                    Double.parseDouble(ersparnisUSD);
                    ersparnisUSD += ersparnisUSD;
                    String ersparnisUSDBetrag = ersparnisUSD + "USD";
                    terminal.print(ersparnisUSDBetrag);
                } else if (konto.getBetrag().getWährung() == Währung.GBP) {
                    String ersparnisGBP = konto.getBetrag().toString();
                    Double.parseDouble(ersparnisGBP);
                    ersparnisGBP += ersparnisGBP;
                    String ersparnisGBPBetrag = ersparnisGBP + "GBP";
                    terminal.print(ersparnisGBPBetrag);
                } else {
                    terminal.print("Keine Ersparnisse");
                }
            }*/
                // Ausgabe
                terminal.print("Der Kunde " + kunde.getName() + "hat Ersparnisse in Höhe von: " + summeEUR.toString());
                terminal.print("Der Kunde " + kunde.getName() + "hat Ersparnisse in Höhe von: " + summeUSD.toString());
                terminal.print("Der Kunde " + kunde.getName() + "hat Ersparnisse in Höhe von: " + summeGBP.toString());
            }
        }
        }catch (Exception e) {
        terminal.print("Fehler: " + e.getMessage());
    }
}

    private void moves (Queue<String> command) {
            try {
                if (command.isEmpty()) terminal.print("\t moves <kontonummer>");
                else {
                    String kontonummer = command.poll();
                    //Konto konto = bank.getKonto(kontonummer);
                    if (bank.getEinzahlKonto().containsKey(kontonummer)) {
                        terminal.print("Einzahlungen bei Kontonr:" + bank.getEinzahlKonto()+bank.getEinzahlungen().toString());
                    }
                    else terminal.print("Keine Einzahlungen des Kunden vorhanden");

                    if (bank.getAuszahlKonto().containsKey(kontonummer)) {
                        terminal.print("Auszahlungen bei Kontonr:" + bank.getAuszahlKonto()+bank.getAuszahlungen().toString());
                    }
                    else terminal.print("Keine Auszahlungen des Kunden vorhanden");
                }
            } catch (Exception e) {
                terminal.print("Fehler: " + e.getMessage());
            }
        }
    private void changes (Queue<String> command) {
        try {
            if (command.isEmpty()) terminal.print("\t changes <ausgangswährung>");
            else {
                String ausgangsWährung = command.poll();
                WechselkursInterface wechselkurs = bank.getWechselkurs();
                if (ausgangsWährung.equals("EUR")){
                    terminal.print("Der Wechselkurs Euro zu USD beträgt: "+wechselkurs.getWechselkursEURUSD());
                    terminal.print("Der Wechselkurs Euro zu GBP beträgt: "+wechselkurs.getWechselkursEURGBP());
                } else if (ausgangsWährung.equals("USD")) {
                    terminal.print("Der Wechselkurs USD zu EUR beträgt: "+wechselkurs.getWechselkursUSDEUR());
                    terminal.print("Der Wechselkurs USD zu GBP beträgt: "+wechselkurs.getWechselkursUSDGBP());
                } else if (ausgangsWährung.equals("GBP")) {
                    terminal.print("Der Wechselkurs GBP zu EUR beträgt: "+wechselkurs.getWechselkursGBPEUR());
                    terminal.print("Der Wechselkurs GBP zu USD beträgt: "+wechselkurs.getWechselkursGBPUSD());
                }
                else terminal.print("Damit handeln wir nicht");

            }
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }
    private void setchanges (Queue<String> command) {
        try {
            if (command.isEmpty()) terminal.print("\t setchanges <Ausgangswährung> <Fremdwährung> <Wechselkurs>");
            else {
                    String ausgang = command.poll();
                    String fremd = command.poll();
                    String setzeWechselkurs= command.poll();
                    WechselkursInterface wechselkurs = bank.getWechselkurs();
                    terminal.print(setzeWechselkurs);
                    if(ausgang.equals("EUR")&& fremd.equals("USD")){
                        double wechselkursEURUSD=(Double.parseDouble(setzeWechselkurs));
                        double wechselkursUSDEUR=1.00/wechselkursEURUSD;
                        //double wechselkursUSDEUR2=Math.floor(100*1.00/wechselkursEURUSD)/100;
                        //terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar = "+wechselkursUSDEUR2+" Euro");
                        wechselkurs.setWechselkursEURUSD(wechselkursEURUSD);
                        wechselkurs.setWechselkursUSDEUR(wechselkursUSDEUR);
                        terminal.print("Folgender Wechselkurs gesetzt: 1 Euro = "+wechselkurs.getWechselkursEURUSD()+" US Dollar");
                        terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar = "+wechselkurs.getWechselkursUSDEUR()+" Euro");
                        //double wechselkursUSDEUR=1.00/wechselkurs.getWechselkursEURUSD();
                        //terminal.print("Folgender Wechselkurs gesetzt: 1 Euro = "+wechselkurs.getWechselkursEURUSD()+"US Dollar");
                        //terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar = "+wechselkursUSDEUR+" US Dollar");
                    }else if (ausgang.equals("EUR")&&fremd.equals("GBP")) {
                        double wechselkursEURGBP = Double.parseDouble(setzeWechselkurs);
                        double wechselkursGBPEUR = 1.00 / wechselkursEURGBP;
                        wechselkurs.setWechselkursEURGBP(wechselkursEURGBP);
                        wechselkurs.setWechselkursEURGBP(wechselkursEURGBP);
                        terminal.print("Folgender Wechselkurs gesetzt: 1 Euro = " + wechselkurs.getWechselkursEURGBP() + " GB Pfund");
                        terminal.print("Folgender Wechselkurs gesetzt: 1 GB Pfund  = " + wechselkurs.getWechselkursGBPEUR() + " Euro");
                    }else if (ausgang.equals("USD")&&fremd.equals("GBP")) {
                        double wechselkursUSDGBP=Double.parseDouble(setzeWechselkurs);
                        double wechselkursGBPUSD=1.00/wechselkursUSDGBP;
                        wechselkurs.setWechselkursEURUSD(wechselkursUSDGBP);
                        wechselkurs.setWechselkursUSDEUR(wechselkursGBPUSD);
                        terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar = "+wechselkurs.getWechselkursUSDGBP()+ " GB Pfund");
                        terminal.print("Folgender Wechselkurs gesetzt: 1 GB Pfund  = "+wechselkurs.getWechselkursGBPUSD()+" US Dollar");
                    } else if (ausgang.equals("USD")&&fremd.equals("EUR")) {
                        double wechselkursUSDEUR = Double.parseDouble(setzeWechselkurs);
                        double wechselkursEURUSD = 1.00 / wechselkursUSDEUR;
                        wechselkurs.setWechselkursEURUSD(wechselkursEURUSD);
                        wechselkurs.setWechselkursUSDEUR(wechselkursUSDEUR);
                        terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar = " + wechselkurs.getWechselkursUSDEUR() + " Euro");
                        terminal.print("Folgender Wechselkurs gesetzt: 1 Euro  = " + wechselkurs.getWechselkursGBPUSD() + " US Dollar");
                    }else if (ausgang.equals("GBP")&&fremd.equals("EUR")) {
                        double wechselkursGBPEUR = Double.parseDouble(setzeWechselkurs);
                        double wechselkursEURGBP = 1.00 / wechselkursGBPEUR;
                        wechselkurs.setWechselkursGBPEUR(wechselkursGBPEUR);
                        wechselkurs.setWechselkursEURGBP(wechselkursEURGBP);
                        terminal.print("Folgender Wechselkurs gesetzt: 1 GB Pfund = " + wechselkurs.getWechselkursGBPEUR() + " Euro");
                        terminal.print("Folgender Wechselkurs gesetzt: 1 Euro = " + wechselkurs.getWechselkursEURGBP() + " GB Pfund");
                    }else if (ausgang.equals("GBP")&&fremd.equals("USD")) {
                            double wechselkursGBPUSD=Double.parseDouble(setzeWechselkurs);
                            double wechselkursUSDGBP=1.00/wechselkursGBPUSD;
                            wechselkurs.setWechselkursGBPUSD(wechselkursGBPUSD);
                            wechselkurs.setWechselkursUSDGBP(wechselkursUSDGBP);
                            terminal.print("Folgender Wechselkurs gesetzt: 1 GB Pfund = "+wechselkurs.getWechselkursGBPUSD()+ " US Dollar");
                            terminal.print("Folgender Wechselkurs gesetzt: 1 US Dollar  = "+wechselkurs.getWechselkursUSDGBP()+" GB Pfund");
                    } else {
                        terminal.print("So geht das nicht");
                    }
                }
            } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }
}



