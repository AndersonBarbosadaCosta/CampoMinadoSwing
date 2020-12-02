package main.java.br.com.anderson.costa.cm.model;

public class EventResult {

    private final boolean winner;

    public EventResult(boolean winner) {
        this.winner = winner;
    }

    public boolean isWinner() {
        return winner;
    }
}
