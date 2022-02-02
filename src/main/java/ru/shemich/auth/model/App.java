package ru.shemich.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
public class App {

    private Long id;
    private String name;
    private String login;

}
