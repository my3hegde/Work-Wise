package com.allforone.oneforall.workwise;

/**
 * Created by Ricky on 2017-05-06.
 */

public class ListItem {

    private int priority;
    private int length;
    private int type;
    private String name;
    private int id;

    @Override
    public String toString() {
        return "ListItem{" +
                "priority='" + priority + '\'' +
                ", length='" + length + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ListItem(int priority, int length, int type, String name, int id) {
        this.priority = priority;
        this.length = length;
        this.type = type;
        this.name = name;
        this.id = id;
    }
}
