package fr.esnl.projetjerroro.Domain;

import java.io.Serializable;

public class Role implements Serializable {
    private int ID;
    private String Name;

    public Role() {}

    public Role(int ID, String Name) {
        this.ID = ID;
        this.Name = Name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "ID=" + ID +
                ", Name='" + Name + '\'' +
                '}';
    }
}
