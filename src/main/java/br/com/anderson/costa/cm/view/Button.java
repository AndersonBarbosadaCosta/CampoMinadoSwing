package main.java.br.com.anderson.costa.cm.view;

import main.java.br.com.anderson.costa.cm.enums.EventField;
import main.java.br.com.anderson.costa.cm.model.Field;
import main.java.br.com.anderson.costa.cm.model.ObserverField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Button extends JButton implements ObserverField, MouseListener {

    private Field field;
    private final Color BG_DEFAULT = new Color(184,184,184);
    private final Color BG_OPEN = new Color(8,179,247);
    private final Color BG_EXPLOSE = new Color(189,66,68);
    private final Color GREEN_TEXT = new Color(0,100,0);

    public Button(Field field) {
        this.field = field;
        setBorder(BorderFactory.createBevelBorder(0));
        setBackground(BG_DEFAULT);
        field.registerObserver(this);
        addMouseListener(this);
    }

    @Override
    public void hasEvent(Field field, EventField event) {

        switch (event) {
            case OPEN:
                doStyleOpen();
                break;
            case EXPLOSION:
                doStyleExplosion();
                break;
            default:
                doStyleDefault();
                break;
        }
    }

    private void doStyleDefault() {
    }

    private void doStyleExplosion() {
    }

    private void doStyleOpen() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch (field.mineInNeighborhood()) {
            case 1:
                setForeground(GREEN_TEXT);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = !field.safetyNeighborhood() ? field.mineInNeighborhood() + "" : "";
        setText(valor);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1) {
            field.openField();
        } else {
            field.updatedCheck();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
