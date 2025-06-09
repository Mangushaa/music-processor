package org.example.test;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Test {

    @Value("${clients.url.service.song}")
    private String value;

    @PostConstruct
    public void test() {
        System.out.println(value);
    }
}
