package org.vaadin.referencearchitecture.helloworld.service;

import org.springframework.stereotype.Service;

@Service
public class Greeter {

    public String sayHello(String name) {
        return "Hello, %s!".formatted(name);
    }
}
