package main.java.br.com.anderson.costa.cm.view;

import main.java.br.com.anderson.costa.cm.enums.EventField;
import main.java.br.com.anderson.costa.cm.model.Field;
import main.java.br.com.anderson.costa.cm.model.ObserverField;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton implements ObserverField {

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
    }
}
