package ru.shemich.auth.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shemich.auth.model.App;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/applications")
public class AppRestControllerV1 {
    private List<App> APPLICATIONS = Stream.of(
            new App(1L,"Ivan","IVAN_LOGIN"),
            new App(2L,"Sergey","SERGEY_LOGIN"),
            new App(3L,"Petr","Petr_LOGIN")
    ).collect(Collectors.toList());


    @GetMapping
    public List<App> getAll() {
        return APPLICATIONS;
    }

    @GetMapping("/{id}")
    public App getById(@PathVariable Long id) {
        return APPLICATIONS.stream().filter(app -> app.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
