package co.com.poli.bookingsservices.helpers;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Response {
    private final int code;
    private final Object data;
}
