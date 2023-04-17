package com.example.star_wars_project.model.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsTest {

    @Test
    void testSettersAndGetters() {
        News news = new News();


        news.setTitle("Test news");
        news.setDescription("This is a test news.");
        news.setPostDate(LocalDateTime.now());
        news.setAuthor(new User());
        news.setApproved(true);


        assertEquals("Test news", news.getTitle());
        assertEquals("This is a test news.", news.getDescription());
        assertNotNull(news.getPostDate());
        assertNotNull(news.getAuthor());
        assertTrue(news.getApproved());
    }

    @Test
    void testTitleAndDescriptionCannotBeNull() {
        News news = new News();
        news.setPostDate(LocalDateTime.now());
        news.setAuthor(new User());
        news.setApproved(true);


    }

    @Test
    void testTitleAndDescriptionCannotBeEmpty() {
        News news = new News();
        news.setPostDate(LocalDateTime.now());
        news.setAuthor(new User());
        news.setApproved(true);


    }

    @Test
    void testTitleAndDescriptionMustBeUnique() {
        News news1 = new News();
        News news2 = new News();

        news1.setTitle("Test news");
        news1.setDescription("This is a test news.");
        news1.setPostDate(LocalDateTime.now());
        news1.setAuthor(new User());
        news1.setApproved(true);

        news2.setTitle("Test news");
        news2.setDescription("This is another test news.");
        news2.setPostDate(LocalDateTime.now());
        news2.setAuthor(new User());
        news2.setApproved(true);

        assertThrows(PersistenceException.class, () -> {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(news1);
            em.persist(news2);
            em.getTransaction().commit();
            em.close();
            emf.close();
        });
    }
}
