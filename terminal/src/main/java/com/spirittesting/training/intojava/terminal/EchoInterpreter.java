package com.spirittesting.training.intojava.terminal;

public class EchoInterpreter implements CommandInterpreter {

    private final Terminal terminal;
    
    public EchoInterpreter(Terminal terminal) {
        this.terminal = terminal;
    }
    
    @Override
    public void interpret(String command) {
        terminal.print("command was: " + command);
    }
}
