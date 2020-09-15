package com.thoughtworks.gtb.csc.basicquiz.model.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessBasicException {

    public UserNotFoundException(int userId) {
        super(HttpStatus.NOT_FOUND, "ID为<" + userId + ">的用户未找到");
    }
}
