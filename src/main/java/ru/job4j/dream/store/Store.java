package ru.job4j.dream.store;

import java.util.Collection;

public interface Store<T> {
    Collection<T> findAll();

    void save(T t);

    T findById(int id);

    void delete(int id);
}