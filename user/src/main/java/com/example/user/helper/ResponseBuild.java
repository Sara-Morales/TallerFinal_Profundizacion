package com.example.user.helper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class ResponseBuild {

    
    public Response success(Object data) {
        return new Response(HttpStatus.OK.value(), data);
    }

    public Response failed(Object data) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), data);
    }

    public Response failedNotFound(Object data) {
        return new Response(HttpStatus.NOT_FOUND.value(), data);
    }

    public Response failedBadRequest(List<Map<String, String>> errors) {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
    }
}

