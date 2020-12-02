package main.java.br.com.anderson.costa.cm.view;

import main.java.br.com.anderson.costa.cm.model.Board;

import javax.swing.*;

public class MineField extends JFrame {

    public MineField() {
        Board board = new Board(16, 30, 50);
        setTitle("MineField");
        setSize(690, 438);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        BoardPanel boardPanel = new BoardPanel(board);
        add(boardPanel);
    }

    public static void main(String[] args) {
        new MineField();
    }
}
