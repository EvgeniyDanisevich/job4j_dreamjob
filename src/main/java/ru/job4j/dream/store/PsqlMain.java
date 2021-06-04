package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

public class PsqlMain {
    public static void main(String[] args) {
        Store<Post> postStore = new PsqlPostStore();
        Store<Candidate> candidateStore = new PsqlCandidateStore();

        Post post1 = new Post("name1", "description1");
        Post post2 = new Post("name2", "description2");
        Post post3 = new Post("name3", "description3");

        postStore.save(post1);
        postStore.save(post2);
        postStore.save(post3);

        Candidate candidate1 = new Candidate("name1", "photo1");
        Candidate candidate2 = new Candidate("name2", "photo2");
        Candidate candidate3 = new Candidate("name3", "photo3");

        candidateStore.save(candidate1);
        candidateStore.save(candidate2);
        candidateStore.save(candidate3);

        System.out.println("Find all posts:");
        postStore.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Find post by id");
        System.out.println(postStore.findById(post3.getId()));
        System.out.println();

        System.out.println("Update post:");
        post3.setName("name updated");
        post3.setDescription("description updated");
        postStore.save(post3);
        System.out.println(postStore.findById(post3.getId()));
        System.out.println();

        System.out.println("Delete post:");
        postStore.delete(post3.getId());
        postStore.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Find all candidates:");
        candidateStore.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println("Find candidate by id");
        System.out.println(candidateStore.findById(candidate2.getId()));
        System.out.println();

        System.out.println("Update candidate:");
        candidate2.setName("name updated");
        post3.setDescription("photo updated");
        candidateStore.save(candidate2);
        System.out.println(candidateStore.findById(candidate2.getId()));
        System.out.println();

        System.out.println("Delete candidate:");
        candidateStore.delete(candidate2.getId());
        candidateStore.findAll().forEach(System.out::println);
    }
}