package com.javalesson.oop;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.ThemeDefinition;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.table.Table;
import com.googlecode.lanterna.gui2.table.TableCellRenderer;
import com.googlecode.lanterna.gui2.table.TableModel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static int player1;
    private static int player2;
    private static boolean step = true;

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        Panel panel = createPanel();


        Label label = new Label("Крестики-нолики из 70х");
        label.setPosition(new TerminalPosition(20 - label.getPreferredSize().getColumns() / 2, 1));
        label.setSize(label.getPreferredSize());
        panel.addComponent(label);


        Label gamePanel = new Label(String.format("Счет %d : %d", player1, player2));
        createGamePanel(panel, gamePanel);


        Table gameTable = new Table<String>("", "", "");
        gameTable.getTableModel().addRow("*", "*", "*");
        gameTable.getTableModel().addRow("*", "*", "*");
        gameTable.getTableModel().addRow("*", "*", "*");

        createButtonGame(panel, gameTable, gamePanel);


        creatExitButton(panel);
        createWindows(panel, screen);


    }

    private static void createGamePanel(Panel panel, Label gamePanel) {
        gamePanel.setSize(new TerminalSize(12, 4));
        gamePanel.setPosition(new TerminalPosition(0, 0));
        gamePanel.setBackgroundColor(TextColor.ANSI.GREEN);

        gamePanel.setPosition(new TerminalPosition(20, 3));
    }

    private static void createButtonGame(Panel panel, Table gameTable, Label gamePanel) {
        Button button = new Button("Старт");
        button.setPosition(new TerminalPosition(2, 20 - 4));
        button.setSize(new TerminalSize(8, 1));
        button.addListener(new Button.Listener() {
            boolean flag = true;

            @Override
            public void onTriggered(Button button) {
                if (flag) {

                    panel.addComponent(gamePanel);
                    gameTable.setPosition(new TerminalPosition(2, 2));
                    gameTable.setSize(new TerminalSize(28, 28));
                    gameTable.setCellSelection(true);
                    gameTable.setTableCellRenderer(new TableCellRenderer() {


                        @Override
                        public TerminalSize getPreferredSize(Table table, Object o, int i, int i1) {
                            int x = table.getTableModel().getColumnCount();
                            int y = table.getTableModel().getRowCount();
                            return new TerminalSize(3, 3);
                        }

                        @Override
                        public void drawCell(Table table, Object o, int i, int i1, TextGUIGraphics textGUIGraphics) {
                            ThemeDefinition definition = table.getThemeDefinition();
                            if (table.getSelectedRow() == i1 && table.getSelectedColumn() == i) {
                                if (table.isFocused()) {
                                    textGUIGraphics.applyThemeStyle(definition.getActive());
                                } else {
                                    textGUIGraphics.applyThemeStyle(definition.getSelected());

                                }
                            }
                            textGUIGraphics.drawRectangle(new TerminalPosition(0, 0),
                                    new TerminalSize(3, 3), TextCharacter.DEFAULT_CHARACTER);
                            textGUIGraphics.putString(1, 1, o.toString());


                        }
                    });

                    gameTable.setSelectAction(new Runnable() {
                        @Override
                        public void run() {
                            if (step) {
                                gameTable.getTableModel().setCell(gameTable.getSelectedColumn(),
                                        gameTable.getSelectedRow(), "X");
                                step = !step;
                            } else {
                                gameTable.getTableModel().setCell(gameTable.getSelectedColumn(),
                                        gameTable.getSelectedRow(), "O");
                                step = !step;
                            }
                            checkGame(gameTable, gamePanel);
                        }
                    });

                    panel.addComponent(gameTable);

                    button.setSize(new TerminalSize(10, 1));
                    button.setLabel("CANCEL");
                    flag = !flag;
                } else {
                    // panel.removeComponent(label1);
                    // label1.setText("eeee");
                    flag = true;
                    button.setLabel("OK");
                }

            }
        });
        panel.addComponent(button);
    }

    private static void createWindows(Panel panel, Screen screen) {
        BasicWindow window = new BasicWindow();
        window.setComponent(panel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));


        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen,
                new DefaultWindowManager() {
                },
                new EmptySpace(TextColor.ANSI.BLACK));

        gui.addWindowAndWait(window);
    }

    private static void creatExitButton(Panel panel) {
        Button exitButton = new Button("Выйти", () -> System.exit(0));
        exitButton.setPosition(new TerminalPosition(40 - 8, 20 - 4));
        exitButton.setSize(new TerminalSize(7, 1));
        exitButton.addTo(panel);
    }

    private static void checkGame(Table gameTable, Label gamePanel) {
        TableModel<String> model = gameTable.getTableModel();
        if (model.getCell(0, 0) == "X") {
            // insert your code here
        } else if (model.getCell(0, 0) == "O") {

        }

    }


    private static Panel createPanel() {
        Panel panel = new Panel(new AbsoluteLayout());
        panel.setPreferredSize(new TerminalSize(40, 20));
        return panel;
    }


}


