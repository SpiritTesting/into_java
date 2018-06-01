package com.spirittesting.training.intojava.terminal;

import org.jdesktop.swingx.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Terminal {

    private final JXFrame frame;
    private final JXTextArea display;
    private final JXTextField input;
    private final JScrollPane scrollPane;

    private CommandInterpreter interpreter = new EchoInterpreter(this);

    public Terminal(String title) {
        frame = new JXFrame(title, true);
        display = new JXTextArea();
        input = new JXTextField("$ ");
        scrollPane = new JScrollPane(display);

        doLayout();
        input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display.append(String.format("%n$ %s%n", input.getText()));
                interpreter.interpret(input.getText());
                input.setText("");
            }
        });
    }
    
    private void doLayout() {
        display.setFont(new Font("monospaced", Font.PLAIN, 14));
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(input, BorderLayout.SOUTH);
        
        display.setFocusable(false);

        frame.setSize(1024, 768);
        frame.setStartPosition(JXFrame.StartPosition.CenterInScreen);
        frame.setVisible(true);
    }
    
    public void clearDisplay() {
        this.display.setText("");
    }

    public void print(String text) {
        this.display.append(String.format("%s%n", text));
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMaximum());
    }

    public void setCommandInterpreter(CommandInterpreter interpreter) {
        print("Command interpreter changed to " + interpreter.getClass().getSimpleName());
        this.interpreter = interpreter;
    }

    public static void main(String[] args) {
        Terminal terminal = new Terminal("Terminal");
        terminal.display.append("Hallo Welt");
    }
}
