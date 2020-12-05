package main.java.br.com.anderson.costa.cm.model;

import main.java.br.com.anderson.costa.cm.enums.EventField;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Field {

    private int column;
    private int row;
    private boolean checked;
    private boolean open;
    private boolean mine;
    private List<Field> neighborhood = new ArrayList<>();
    private Set<ObserverField> observers = new LinkedHashSet<>();

    Field(int row, int column) {
        this.row = row;
        this.column = column;
    }
    public void registerObserver(ObserverField o) {
        observers.add(o);
    }

    private void notifyObservers(EventField event) {
        this.observers.forEach(o -> o.hasEvent(this, event));
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
        if(open) {
            notifyObservers(EventField.OPEN);
        }
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public List<Field> getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(List<Field> neighborhood) {
        this.neighborhood = neighborhood;
    }

    boolean addNeighborhood(Field field) {

        boolean diffRow = this.row != field.getRow();
        boolean diffColumn = this.column != field.getColumn();
        boolean isDiagonal = diffRow && diffColumn;

        int isColumnNeighborhood = Math.abs(this.column - field.getColumn());
        int isRowNeighborhood = Math.abs(this.row - field.getRow());

        boolean isCandidateNeighborhood = isColumnNeighborhood + isRowNeighborhood == 1;
        boolean isSecondCandidateNeighborhood = isColumnNeighborhood + isRowNeighborhood == 2;
        if (isCandidateNeighborhood && !isDiagonal) {
            neighborhood.add(field);
            return true;
        } else if (isSecondCandidateNeighborhood && isDiagonal) {
            neighborhood.add(field);
            return true;
        } else {
            return false;
        }
    }

    public void updatedCheck() {
        if (!open) {
            checked = !checked;
        }
        if(checked) {
            notifyObservers(EventField.CHECKED);
        } else {
            notifyObservers(EventField.UNCHECKED);
        }
    }

    void mined() {
        mine = true;
    }

    public boolean openField() {
        if (!open && !checked) {
            if (mine) {
             notifyObservers(EventField.EXPLOSION);
             return true;
            }
            setOpen(true);
            if (safetyNeighborhood()) {
                neighborhood.forEach(Field::openField);
            }
            return true;
        } else {
            return false;
        }
    }

   public boolean safetyNeighborhood() {
        return neighborhood.stream().noneMatch(v -> v.mine);
    }

    boolean objectiveFinished() {
        boolean unraveled = !mine && open;
        boolean protect = mine && checked;
        return unraveled || protect;
    }

   public int mineInNeighborhood() {
        return (int)neighborhood.stream().filter(v -> v.mine).count();
    }

    boolean restart() {
        open = false;
        mine = false;
        checked = false;
        return false;
    }

}
