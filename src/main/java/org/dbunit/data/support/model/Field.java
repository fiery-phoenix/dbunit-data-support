package org.dbunit.data.support.model;

public class Field {

    private final String name;

    private final Object value;

    public Field(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
