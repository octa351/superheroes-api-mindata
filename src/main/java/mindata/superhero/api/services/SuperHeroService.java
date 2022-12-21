package mindata.superhero.api.services;

import mindata.superhero.api.exceptions.HeroNotFoundException;
import mindata.superhero.api.models.SuperHero;
import mindata.superhero.api.repositories.SuperHeroRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SuperHeroService {

    private final SuperHeroRepository superHeroRepository;

    public SuperHeroService(SuperHeroRepository superHeroRepository) {
        this.superHeroRepository = superHeroRepository;
    }

    public SuperHero saveSuperHero(SuperHero superHero) {
        Optional<SuperHero> superHeroOptional = superHeroRepository.findById(superHero.getId());
        if (superHeroOptional.isPresent()){
            superHeroRepository.save(superHero);
            return superHero;
        }
        else{
            StringBuilder errorStringBuilder = new StringBuilder();
            errorStringBuilder.append("Hero with id: ");
            errorStringBuilder.append(superHero.getId());
            errorStringBuilder.append(" Not found");
            throw new HeroNotFoundException(errorStringBuilder.toString());
        }
    }

    public List<SuperHero> findSuperHeroByName(String name) {
        List<SuperHero> superHeroes = superHeroRepository.findSuperHeroContainingCharSequence(name);
        return superHeroes;
    }

    public void deleteSuperHeroById(String id) {
        Long idToDelete = Long.parseLong(id);
        try{
            superHeroRepository.deleteById(idToDelete);
        }
        catch(EmptyResultDataAccessException e){
            throw new HeroNotFoundException("No hero with the provided id was found to delete");
        }
    }

    public List<SuperHero> findAllSuperHeroes() {
        return superHeroRepository.findAll();
    }

    public Optional<SuperHero> findSuperHeroById(String id) {
        Long idToFind = Long.parseLong(id);
        return superHeroRepository.findById(idToFind);
    }
}
