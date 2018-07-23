package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.*;
import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.BetragException;
import com.spirittesting.training.intojava.level5.currency.Währung;
import com.spirittesting.training.intojava.terminal.CommandInterpreter;
import com.spirittesting.training.intojava.terminal.Terminal;

import java.util.*;
import java.util.stream.Collectors;

public class BankCommands implements CommandInterpreter {

    private Bank bank;
    private Terminal terminal;

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
            case "rate":
                rate(command);
                break;
            case "freigabe":
                lastschriftFreigabe(command);
                break;
            case "lastschrift":
                lastschrift(command);
                break;
            case "dispoAdd":
                dispoAdd(command);
                break;
            case "dispoSub":
                dispoSub(command);
                break;
            case "deposit":
                deposit(command);
                break;
        }
    }

    private void help(Queue<String> params) {
        if (params.isEmpty()) {
            terminal.print("available commands:");
            terminal.print("list [kunde|konto]");
            terminal.print("add [kunde|konto]");
            terminal.print("remove [kunde|konto]");
            terminal.print("transfer kontoVon kontoNach betrag");
            terminal.print("deposit kundennummer konto betrag");
            terminal.print("withdraw kundennummer konto betrag");
            terminal.print("rate <von> <nach> <kurs>");
            terminal.print("freigabe <konto> <gläubiger>");
            terminal.print("lastschrift <gläubiger> <konto> <betrag>");
            terminal.print("dispoAdd <konto> <betrag>");
            terminal.print("dispoSub <konto> <betrag>");
            terminal.print("type 'help topic' to view detailed help for topic");
        }
    }

    private void rate(Queue<String> command) {
        Währung von = Währung.valueOf(command.poll());
        Währung nach = Währung.valueOf(command.poll());
        double kurs = Double.parseDouble(command.poll());
        von.setWechselkurs(nach, kurs);
    }

    private void dispoAdd(Queue<String> command) {
        if (command.isEmpty()) {
            terminal.print("dispoAdd <konto> <betrag>");
            return;
        }
        Konto konto = bank.getKonto(command.poll());
        konto.addToDispo(new Betrag(command.poll()));
    }

    private void dispoSub(Queue<String> command) {
        if (command.isEmpty()) {
            terminal.print("dispoSub <konto> <betrag>");
            return;
        }
        Konto konto = bank.getKonto(command.poll());
        konto.removeFromDispo(new Betrag(command.poll()));
    }

    private void lastschriftFreigabe(Queue<String> command) {
        if (command.isEmpty()) {
            terminal.print("freigabe <konto> <gläubiger>");
            return;
        }
        Konto konto = bank.getKonto(command.poll());
        konto.addLastschriftGläubiger(command.poll());
    }

    private void lastschrift(Queue<String> command) {
        if (command.isEmpty()) {
            terminal.print("lastschrift <gläubiger> <konto> <betrag>");
            return;
        }
        String gläubiger = command.poll();
        String konto = command.poll();
        String betrag = command.poll();
        try {
            bank.lastschriftEinzug(gläubiger, bank.getKonto(konto), new Betrag(betrag));
        } catch (KontostandNichtAusreichendException e) {
            terminal.print("Kontostand ungenügend. Scher dich weg.");
        } catch (KeineBerechtigungFürKontoException e) {
            terminal.print("Pfoten weg! Kein Kontozugriff.");
        }
    }

        private void list (Queue < String > command) {
            try {
                switch (command.poll()) {
                    case "kunde":
                        if (command.isEmpty()) terminal.print("\tlist kunde <kundennummer>");
                        else {
                            String kundennummer = command.poll();
                            Kunde kunde = bank.getKunde(kundennummer);
                            if (kunde == null) {
                                terminal.print("Kundennummer nicht bekannt");
                            } else {
                                terminal.print("Kundennummer: " + kunde.getKundennummer());
                                terminal.print("Name        : " + kunde.getName());
                                terminal.print("--- Finanzstatus ---");
                                for (Währung währung : Währung.values())
                                    terminal.print("\t" + währung + ": " + kunde.getNetWorth(währung));
                                terminal.print("--- Konten ---");
                                for (Konto konto : kunde.getKonten()) {
                                    terminal.print("\tKonto #" + konto.getKontonummer() + ": " + konto.getBetrag().toString());
                                }
                            }
                        }
                        break;
                    case "konto":
                        if (command.isEmpty()) terminal.print("\t list konto <kontonummer>");
                        else {
                            String kontonummer = command.poll();
                            final Konto konto = bank.getKonto(kontonummer);
                            if (konto == null) {
                                terminal.print("Konto nicht bekannt");
                                return;
                            }
                            for (KontoBewegung kontoBewegung : konto.getKontoBewegungen()) {
                                terminal.print("\t" + kontoBewegung);
                            }
                        }
                        break;
                    default:
                        throw new NullPointerException("Falsche Eingabe");

                }
            } catch (NullPointerException e) {
                terminal.print("Befehl unvollständig. Mögliche Formen:");
                terminal.print("\tlist kunde <kundennummer>");
                terminal.print("\tlist konto <kundennummer> <kontonummer>");
            }
        }

        private void add (Queue < String > command) {
            try {
                switch (command.poll()) {
                    case "kunde":
                        if (command.isEmpty()) terminal.print("\tadd kunde <name>");
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

        private void remove (Queue < String > command) {
            try {
                switch (command.poll()) {
                    case "kunde":
                        if (command.isEmpty()) terminal.print("\tremove kunde <kundennummer>");
                        else {
                            String kundennummer = command.stream().collect(Collectors.joining(" "));
                            Kunde kunde = bank.getKunde(kundennummer);
                            if (kunde == null) {
                                terminal.print("Diese Kundennummer ist nicht bekannt.");
                                return;
                            }
                            try {
                                bank.removeKunde(kundennummer);
                            } catch (KontoNichtAusgeglichenException e) {
                                terminal.print("Konten des Kunden nicht ausgeglichen. Kunde wird nicht gelöscht.");
                                return;
                            }
                            terminal.print("Kunde " + kunde.getKundennummer() + " wurde gelöscht.");
                        }
                        break;
                    case "konto":
                        if (command.isEmpty()) terminal.print("\t remove konto <kundennummer> <kontonummmer>");
                        else {
                            try {
                                String kundennummer = command.poll();
                                String kontonummer = command.poll();
                                final Kunde kunde = bank.getKunde(kundennummer);
                                final Konto konto = bank.getKonto(kontonummer);
                                if (kunde == null) {
                                    terminal.print("Diese Kundennummer ist nicht bekannt.");
                                    return;
                                }
                                kunde.removeKonto(konto);
                                terminal.print("Konto " + konto.getKontonummer() + " wurde gelöscht");
                            } catch (NoSuchElementException e) {
                                terminal.print("Diese Kontonummer gehört nicht zum Kunden.");
                            } catch (KontoNichtAusgeglichenException e) {
                                terminal.print("Das Konto ist nicht ausgeglichen.");
                            }
                        }
                        break;
                    default:
                        throw new NullPointerException("Falsche Eingabe");
                }
            } catch (NullPointerException e) {
                terminal.print("Befehl unvollständig. Mögliche Formen:");
                terminal.print("\tremove kunde <kundennummer>");
                terminal.print("\tremove konto <kundennummer> <kontonummer>");
            }
        }

        private void transfer (Queue < String > command) {
            try {
                Konto kontoVon = bank.getKonto(command.poll());
                Konto kontoNach = bank.getKonto(command.poll());
                Betrag betrag = new Betrag(command.poll());
                bank.überweise(kontoVon, kontoNach, betrag);
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

        private void withdraw (Queue < String > command) {
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

        private void deposit (Queue < String > command) {
            try {
                Kunde kunde = bank.getKunde(command.poll());
                Konto konto = bank.getKonto(command.poll());
                Betrag betrag = new Betrag(command.poll());
                bank.zahleEin(kunde, konto, betrag);
            } catch (NullPointerException e) {
                terminal.print("Befehl unvollständig.");
                terminal.print("\tdeposit <kundennummer> <kontonummer> <betrag>");
            } catch (Exception e) {
                terminal.print("Fehler: " + e.getMessage());
            }
        }

    }
