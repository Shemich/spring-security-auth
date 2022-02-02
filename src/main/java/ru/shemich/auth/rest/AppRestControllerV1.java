package ru.shemich.auth.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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
    @PreAuthorize("hasAuthority('app:read')")
    public App getById(@PathVariable Long id) {
        return APPLICATIONS.stream().filter(app -> app.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('app:write')")
    public App create(@RequestBody App app) {
        this.APPLICATIONS.add(app);
        return app;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('app:write')")
    public void deleteById(@PathVariable Long id) {
        this.APPLICATIONS.removeIf(app -> app.getId().equals(id));
    }
}
