package co.com.poli.showtimesservice.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ResponseBuild {

    public Response success(Object data) {
        return new Response(HttpStatus.OK.value(), data);
    }

    public Response successValidation(Boolean result) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("data", result);
        return new Response(HttpStatus.OK.value(), response);
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
