package com.movies.exception;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class PageLessThanOneException extends Exception {

    public PageLessThanOneException() {
        log.info("Page should be greater than 0");
    }

    public PageLessThanOneException(String message) {
        super(message);
        log.info("Page should be greater than 0");
    }
}
