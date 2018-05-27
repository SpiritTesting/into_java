package com.spirittesting.training.intojava.level5;

import com.spirittesting.training.intojava.terminal.CommandInterpreter;

import java.awt.datatransfer.StringSelection;
import java.util.List;

public class BankCommands implements CommandInterpreter {

    @Override
    public void interpret(String command) {

    }

    private class ParameterizedCommand {

        private final String command;
        private final String[] args;

        public ParameterizedCommand(String command) {
            int index = command.indexOf("\\s");
            this.command = index == -1 ? command : command.substring(0, index).trim();
            String args = index == -1 ? "" : command.substring(index).trim();
            String[] argsplit = args.split(",");
            this.args = index == -1 ? new String[0] : new String[argsplit.length];
            for (int i = 0; i < argsplit.length; i++) {
                this.args[i] = argsplit[i].trim();
            }
        }

    }
}
