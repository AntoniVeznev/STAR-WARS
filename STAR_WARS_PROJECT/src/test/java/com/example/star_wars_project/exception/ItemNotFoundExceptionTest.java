package com.example.star_wars_project.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ItemNotFoundExceptionTest {
    @Test
    public void testConstructorSetsResponseStatusToNotFound() {
        ItemNotFoundException exception = new ItemNotFoundException();
        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}