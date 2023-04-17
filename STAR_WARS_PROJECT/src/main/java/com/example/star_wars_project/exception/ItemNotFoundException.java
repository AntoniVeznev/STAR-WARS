package com.example.star_wars_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {

    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}