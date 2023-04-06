package com.example.star_wars_project.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You dont have permission for that page!")
public class ForbiddenException extends Http403ForbiddenEntryPoint {

}