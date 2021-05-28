package ru.job4j.models;

import java.util.Objects;

public class HR {
    private int id;
    private String name;
    private String surname;

    public HR(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HR hr = (HR) o;
        return id == hr.id && Objects.equals(name, hr.name) && Objects.equals(surname, hr.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname);
    }

    @Override
    public String toString() {
        return "HR{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + '}';
    }
}
