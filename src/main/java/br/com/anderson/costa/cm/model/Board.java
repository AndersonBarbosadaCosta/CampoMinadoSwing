package main.java.br.com.anderson.costa.cm.model;

import main.java.br.com.anderson.costa.cm.enums.EventField;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements ObserverField {

    private final int sizeRows;
    private final int sizeColumns;
    private final int amountMine;

    private List<Field> fields = new ArrayList<>();
    private List<Consumer<EventResult>> observers = new ArrayList<>();

    public Board(int sizeRows, int sizeColumns, int amountMine) {
        this.sizeRows = sizeRows;
        this.sizeColumns = sizeColumns;
        this.amountMine = amountMine;
        generateFields();
        associateNeighborhoods();
        sortedMines();
    }

    public void forEach(Consumer<Field> function){
        fields.forEach(function);
    }

    public void registerObsever(Consumer<EventResult> observer) {
        observers.add(observer);
    }

    private void notifyObservers(boolean result) {
        observers.forEach(o -> o.accept(new EventResult(result)));
    }

    public int getSizeRows() {
        return sizeRows;
    }

    public int getSizeColumns() {
        return sizeColumns;
    }

    public int getAmountMine() {
        return amountMine;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    private void generateFields() {
        for (int row = 0; row < sizeRows; row++) {
            for (int column = 0; column < sizeColumns; column++) {
                Field field = new Field(row, column);
                field.registerObserver(this);
                fields.add(field);
            }
        }
    }

    private void associateNeighborhoods() {
        for (Field field1 : fields) {
            for (Field field2 : fields) {
                field1.addNeighborhood(field2);
            }
        }
    }

    public void sortedMines() {
        long amount;

        Predicate<Field> isMinedField = Field::isMine;
        do {
            int aleatorio = (int) Math.round(Math.random() * fields.size()); // cast tem prioridade sobre multiplicação
            if (aleatorio < fields.size()) {
                fields.get(aleatorio).mined();
            }
            amount = this.fields.stream().filter(isMinedField).count();
        } while (this.amountMine > amount);
    }

    public boolean objectiveFinished() {
        return fields.stream().allMatch(Field::objectiveFinished);
    }

    public void restartGame() {
        fields.forEach(Field::restart);
        sortedMines();
    }

    public void open(int linha, int coluna) {
        fields
                .parallelStream()
                .filter(c -> c.getRow() == linha && c.getColumn() == coluna)
                .findFirst().ifPresent(Field::openField);
    }

    public void update(int linha, int coluna) {
        fields
                .parallelStream()
                .filter(c -> c.getRow() == linha && c.getColumn() == coluna)
                .findFirst().ifPresent(Field::updatedCheck);
    }

    public String toString() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int j = 0; j < sizeColumns; j++) {
            sb.append(" ");
            sb.append(j);
            sb.append(" ");
        }
        sb.append("\n");
        for (int line = 0; line < sizeRows; line++) {
            sb.append(" ");
            sb.append(line);
            for (int columns = 0; columns < sizeColumns; columns++) {
                sb.append(" ");
                sb.append(fields.get(i));
                sb.append(" ");
                i++;
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private void showMines() {
        fields.stream().filter(Field::isMine).filter(c -> !c.isChecked()).forEach(p -> p.setOpen(true));
    }

    @Override
    public void hasEvent(Field field, EventField event) {
        if (event == EventField.EXPLOSION) {
            showMines();
            this.notifyObservers(false);
        } else if (objectiveFinished()) {
            this.notifyObservers(true);
        }
    }
}
