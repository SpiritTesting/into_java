package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.Bank;
import com.spirittesting.training.intojava.terminal.CommandInterpreter;
import com.spirittesting.training.intojava.terminal.Terminal;

import java.awt.datatransfer.StringSelection;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        if (command.isEmpty()) { help(command); return; }
        switch (command.poll()) {
            case "help": default: help(command); break;
            case "list": list(command); break;
            case "add": add(command); break;
            case "remove": remove(command); break;
            case "transfer": transfer(command); break;
            case "withdraw": withdraw(command); break;
            case "deposit": deposit(command); break;
        }
    }

    private void help(Queue<String> params) {
        if (params.isEmpty()) {
            terminal.print("available commands:");
            terminal.print("list [kundennummer]?");
            terminal.print("add [kunde|konto]");
            terminal.print("remove [kunde|konto]");
            terminal.print("transfer");
            terminal.print("deposit");
            terminal.print("withdraw");
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
            terminal.print("<konten pending>");
        }
    }

    private void add(Queue<String> command) {
        terminal.print("<pending>");
    }

    private void remove(Queue<String> command) {
        terminal.print("<pending>");
    }

    private void transfer(Queue<String> command) {
        terminal.print("<pending>");
    }

    private void withdraw(Queue<String> command) {
        terminal.print("<pending>");
    }

    private void deposit(Queue<String> command) {
        terminal.print("<pending>");
    }

}
