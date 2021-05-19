package com.movies.controller;

import com.movies.controller.dto.ErrorMessage;
import com.movies.exception.PageLessThanOneException;
import com.movies.exception.NoRecordFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PageLessThanOneException.class)
    public ErrorMessage handlePageLessThanOneException(PageLessThanOneException exc) {
        return new ErrorMessage( "الرجاء ادخال رقم صفحة اكبر من 0", "Page number should be greater than 0",
                "تأكد من رقم الصفحة", "Check Page number", exc);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(NoRecordFoundException.class)
    public ErrorMessage handleNoRecordFoundException(NoRecordFoundException exc) {
        return new ErrorMessage( "لاتوجد نتائج", "No records found",
                "لاتوجد نتائج", "No records found", exc);
    }
}
