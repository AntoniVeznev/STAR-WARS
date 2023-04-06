package com.example.star_wars_project.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Movie was not found!")
public class ItemNotFoundException extends RuntimeException{

}
