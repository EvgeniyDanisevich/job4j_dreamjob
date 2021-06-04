package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore {

    private static final MemStore INST = new MemStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private static final AtomicInteger POST_ID = new AtomicInteger(4);

    private static final AtomicInteger CANDIDATE_ID = new AtomicInteger(4);

    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "Vacancies for Junior"));
        posts.put(2, new Post(2, "Middle Java Job", "Vacancies for Middle"));
        posts.put(3, new Post(3, "Senior Java Job", "Vacancies for Senior"));
        candidates.put(1, new Candidate(1, "Junior Java", "1"));
        candidates.put(2, new Candidate(2, "Middle Java", "2"));
        candidates.put(3, new Candidate(3, "Senior Java", "3"));
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }

    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.incrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findByPostId(int id) {
        return posts.get(id);
    }

    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.incrementAndGet());
            candidate.setPhoto(String.valueOf(candidate.getId()));
        }
        candidates.put(candidate.getId(), candidate);
    }

    public void removeCandidateById(int id) {
        candidates.remove(id);
    }

    public Candidate findByCandidateId(int id) {
        return candidates.get(id);
    }
}