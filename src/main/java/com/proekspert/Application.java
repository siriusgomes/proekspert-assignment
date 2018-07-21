package com.proekspert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application {

    public static List<String> listHosts = new ArrayList<String>();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        listHosts = Arrays.stream(args).collect(Collectors.toList());
    }

}