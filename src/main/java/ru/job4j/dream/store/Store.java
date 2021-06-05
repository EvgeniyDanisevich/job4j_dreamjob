package ru.job4j.dream.store;

import java.util.Collection;

public interface Store<T> {
    default Collection<T> findAll() {
        return null;
    }

    default void save(T t) {
    }

    default T findById(int id) {
        return null;
    }

    default void delete(int id) {
    }

    default T findByEmail(String email) {
        return null;
    }
}