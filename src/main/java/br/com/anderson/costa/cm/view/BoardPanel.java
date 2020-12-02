package main.java.br.com.anderson.costa.cm.view;

import main.java.br.com.anderson.costa.cm.model.Board;
import main.java.br.com.anderson.costa.cm.model.Field;

import javax.swing.*;
import java.awt.*;

public class BoardPanel extends JPanel {

    public BoardPanel(Board board) {

        setLayout(new GridLayout(board.getSizeRows(),board.getSizeColumns()));
        board.forEach(c -> add(new Button(c)));

        board.registerObsever(e -> {
            // implementar
        });
    }
}
