package co.com.poli.bookingsservices.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseBuild {

    public Response success() {
        return Response.builder().code(HttpStatus.OK.value()).build();
    }

    public Response success(Object data) {
        return Response.builder().code(HttpStatus.OK.value()).data(data).build();
    }

    public Response successCreated(Object data) {
        return Response.builder().code(HttpStatus.CREATED.value()).data(data).build();
    }

    public Response failed() {
        return Response.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
    }

    public Response failed(String message) {
        Map<String, String> data = new HashMap<>();
        data.put("error", message);
        return Response.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).data(data).build();
    }

    public Response failedNotFound(String message) {
        Map<String, String> data = new HashMap<>();
        data.put("error", message);
        return Response.builder().code(HttpStatus.NOT_FOUND.value()).data(data).build();
    }

    public Response validationFailed(List<Map<String, String>> errors) {
        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);
        return Response.builder().code(HttpStatus.BAD_REQUEST.value()).data(data).build();
    }

    public Response unauthorized(String message) {
        Map<String, String> data = new HashMap<>();
        data.put("error", message);
        return Response.builder().code(HttpStatus.UNAUTHORIZED.value()).data(data).build();
    }
}
