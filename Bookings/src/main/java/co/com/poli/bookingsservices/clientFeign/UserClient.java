package co.com.poli.bookingsservices.clientFeign;

import co.com.poli.bookingsservices.helpers.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "users")
public interface UserClient {
    @GetMapping("api/v1/poli/user/{id}")
    Response findById(@PathVariable Long id);
}
