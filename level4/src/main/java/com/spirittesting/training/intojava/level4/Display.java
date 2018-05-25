package com.spirittesting.training.intojava.level4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Display {

    private static final String format = "\t%-20s - %s%n";

    private final JFrame frame = new JFrame();
    private final JTextArea textArea = new JTextArea();
    private final JTextField textField = new JTextField();
    private Kontaktbuch kontaktbuch;

    Display(Kontaktbuch kontaktbuch) {
        this.kontaktbuch = kontaktbuch;
        frame.setAutoRequestFocus(true);
        frame.setSize(1024, 768);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea.setFont(new Font("monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton button = new JButton("OK");
        button.setText("OK");
        button.setDefaultCapable(true);

        button.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Command command = new Command(textField.getText());
                textField.setText("");

                List<Ausgabe> ausgaben = new ArrayList<>();
                try {
                    switch (command.command) {
                        case "list":
                            ausgaben.addAll(kontaktbuch.listAllContacts());
                            break;
                        case "who":
                            ausgaben.add(kontaktbuch.showSelectedContact());
                            break;
                        case "add":
                            if (command.parameters.length != 1) {
                                ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                                break;
                            }
                            ausgaben.add(kontaktbuch.addKontakt(command.parameters[0]));
                            break;
                        case "rem":
                            if (command.parameters.length != 1) {
                                ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                                break;
                            }
                            ausgaben.add(kontaktbuch.removeKontakt(command.parameters[0]));
                            break;
                        case "sel":
                            if (command.parameters.length != 1) {
                                ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie den Namen des Kontakts ein"));
                                break;
                            }
                            ausgaben.add(kontaktbuch.selectKontakt(command.parameters[0]));
                            break;
                        case "newadd":
                            if (command.parameters.length != 4) {
                                ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie Straße, Hausnummer, Postleitzahl und Ort getrennt durch je ein Komma ein"));
                                break;
                            }
                            ausgaben.add(kontaktbuch.addAdresse(command.parameters[0], command.parameters[1], command.parameters[2], command.parameters[3]));
                            break;
                        case "remadd":
                            if (command.parameters.length != 1) {
                                ausgaben.add(new Ausgabe("Fehler", "Bitte geben Sie die ID der zu löschenden Adresse ein. Denken Sie an das HashTag!"));
                                break;
                            }
                            ausgaben.add(kontaktbuch.removeAdresse(command.parameters[0]));
                            break;
                        case "quit":
                            System.exit(0);
                        case "help":
                        default:
                            ausgaben.addAll(kontaktbuch.printHelp());
                    }
                } catch (IllegalArgumentException e) {
                    ausgaben.add(new Ausgabe("Eingabe nicht verstanden", ""));
                }
                for (Ausgabe ausgabe : ausgaben) {
                    textArea.append("\n");
                    textArea.append(String.format(format, ausgabe.getTitel(), ausgabe.getInhalt()));
                }
            }
        });

        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new java.awt.BorderLayout());
        commandPanel.add(textField, BorderLayout.CENTER);
        commandPanel.add(button, BorderLayout.EAST);

        frame.getRootPane().setDefaultButton(button);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(commandPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Display display = new Display(new Kontaktbuch());
        for (Ausgabe ausgabe : display.kontaktbuch.printHelp()) {
            display.textArea.append("\n");
            display.textArea.append(String.format(format, ausgabe.getTitel(), ausgabe.getTitel()));
        }
    }

    static class Command {
        final String command;
        final String[] parameters;

        Command(String input) {
            if (input.isEmpty()) throw new IllegalArgumentException("Eingabe erwartet");

            int indexOf = input.indexOf(" ");
            if (indexOf == -1) {
                command = input;
                parameters = new String[0];
            } else {
                command = input.substring(0, indexOf).trim();
                String parameterString = input.substring(indexOf);
                parameters = parameterString.split(",");
            }
        }
    }

}
