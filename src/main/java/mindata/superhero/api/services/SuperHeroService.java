package mindata.superhero.api.services;

import mindata.superhero.api.models.SuperHero;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperHeroService {
    public SuperHero saveSuperHero(SuperHero superHero) {
        throw new NotImplementedException();
    }

    public SuperHero findSuperHeroByName(String name) {
        throw new NotImplementedException();
    }

    public SuperHero deleteSuperHeroById(String id) {
        throw new NotImplementedException();
    }

    public List<SuperHero> findAllSuperHeroes() {
        throw new NotImplementedException();
    }
}
