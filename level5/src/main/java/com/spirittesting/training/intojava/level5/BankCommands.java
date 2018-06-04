package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.Bank;
import com.spirittesting.training.intojava.level5.bank.Konto;
import com.spirittesting.training.intojava.level5.bank.KontostandNichtAusreichendException;
import com.spirittesting.training.intojava.level5.bank.Kunde;
import com.spirittesting.training.intojava.level5.currency.Betrag;
import com.spirittesting.training.intojava.level5.currency.BetragException;
import com.spirittesting.training.intojava.level5.currency.Währung;
import com.spirittesting.training.intojava.terminal.CommandInterpreter;
import com.spirittesting.training.intojava.terminal.Terminal;

import java.awt.datatransfer.StringSelection;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
            case "deposit":
                deposit(command);
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

    private void remove(Queue<String> command) {
        terminal.print("<pending>");
    }

    private void transfer(Queue<String> command) {
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
            terminal.print("\twithdraw <kundennummer> <kontonummer> <betrag>");
        } catch (Exception e) {
            terminal.print("Fehler: " + e.getMessage());
        }
    }

}
