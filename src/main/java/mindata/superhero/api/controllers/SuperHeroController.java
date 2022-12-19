package mindata.superhero.api.controllers;

import mindata.superhero.api.models.SuperHero;
import mindata.superhero.api.services.SuperHeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SuperHeroController {

    @Autowired
    private SuperHeroService superHeroService;

    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Object> postSuperHero(@Valid SuperHero superHero) {
        SuperHero superHeroResponse = superHeroService.saveSuperHero(superHero);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Object> getSuperHeroByName(@RequestParam String name) {
        SuperHero superHeroResponse = superHeroService.findSuperHeroByName(name);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @RequestMapping(value = "/superHero",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSuperHeroById(@RequestParam String id) {
        SuperHero superHeroResponse = superHeroService.deleteSuperHeroById(id);
        return ResponseEntity.ok().body(superHeroResponse);
    }

    @RequestMapping(value = "/findAllHeroes",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<Object> getAllSuperHeroes() {
        List<SuperHero> superHeroResponse = superHeroService.findAllSuperHeroes();
        return ResponseEntity.ok().body(superHeroResponse);
    }
}
