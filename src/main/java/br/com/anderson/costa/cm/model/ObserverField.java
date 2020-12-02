package main.java.br.com.anderson.costa.cm.model;

import main.java.br.com.anderson.costa.cm.enums.EventField;

@FunctionalInterface
public interface ObserverField {
    void hasEvent(Field field, EventField event);
}
