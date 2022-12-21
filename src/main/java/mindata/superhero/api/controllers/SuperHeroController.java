package mindata.superhero.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import mindata.superhero.api.models.SuperHero;
import mindata.superhero.api.services.SuperHeroService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SuperHeroController {

    private SuperHeroService superHeroService;

    public SuperHeroController(SuperHeroService superHeroService) {
        this.superHeroService = superHeroService;
    }

    @Operation(summary = "Update a superHero",
            security = {@SecurityRequirement(name = "spring_oauth", scopes = {"q2-reprocess-resource-server/octa_scope"})},
            description = "Update a superHero")
    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('SCOPE_q2-reprocess-resource-server/octa_scope')")
    public ResponseEntity<Object> postSuperHero(@RequestBody SuperHero superHero) {
        SuperHero superHeroResponse = superHeroService.saveSuperHero(superHero);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @Operation(summary = "Get a superhero by name",
            security = {@SecurityRequirement(name = "spring_oauth", scopes = {"q2-reprocess-resource-server/octa_scope"})},
            description = "Get a superhero by name")
    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCOPE_q2-reprocess-resource-server/octa_scope')")
    public ResponseEntity<Object> getSuperHeroByName(@RequestParam String name) {
        List<SuperHero> superHeroResponse = superHeroService.findSuperHeroByName(name);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @Operation(summary = "Delete a superhero from database",
            security = {@SecurityRequirement(name = "spring_oauth", scopes = {"q2-reprocess-resource-server/octa_scope"})},
            description = "Delete a superhero from database")
    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('SCOPE_q2-reprocess-resource-server/octa_scope')")
    public ResponseEntity<Object> deleteSuperHeroById(@RequestParam String id) {
        SuperHero superHeroResponse = superHeroService.deleteSuperHeroById(id);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @Operation(summary = "Get all superheroes from database",
            security = {@SecurityRequirement(name = "spring_oauth", scopes = {"q2-reprocess-resource-server/octa_scope"})},
            description = "Get all superheroes from database")
    @RequestMapping(value = "/findAllHeroes",
            produces = { "application/json" },
            method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('SCOPE_q2-reprocess-resource-server/octa_scope')")
    public ResponseEntity<Object> getAllSuperHeroes() {
        List<SuperHero> superHeroResponse = superHeroService.findAllSuperHeroes();
        return ResponseEntity.ok().body(superHeroResponse);
    }
}
