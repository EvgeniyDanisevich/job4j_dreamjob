package ru.job4j.dream.store;

import ru.job4j.dream.model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MemPostStore implements Store<Post> {

    private final List<Post> posts = new ArrayList<>();

    private static final class Lazy {
        private static final Store<Post> INST = new MemPostStore();
    }

    public static Store<Post> instOf() {
        return MemPostStore.Lazy.INST;
    }

    @Override
    public Collection<Post> findAll() {
        return new ArrayList<>(posts);
    }

    @Override
    public void save(Post post) {
        posts.add(post);
    }

    @Override
    public Post findById(int id) {
        return posts.get(id);
    }

    @Override
    public void delete(int id) {
        posts.remove(id);
    }
}
