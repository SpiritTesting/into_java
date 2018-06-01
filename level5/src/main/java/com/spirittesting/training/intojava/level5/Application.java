package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.level5.bank.Bank;
import com.spirittesting.training.intojava.terminal.Terminal;

public class Application {

    public static void main(String[] args) {
        Bank bank = new Bank();
        Terminal terminal = new Terminal("Spirit Banking");
        BankCommands commands = new BankCommands(bank, terminal);

        bank.addKunde("Hannes Kabeltod");
        bank.addKunde("Willi Winzig");
        bank.addKunde("Balduin Brot");
    }

}
