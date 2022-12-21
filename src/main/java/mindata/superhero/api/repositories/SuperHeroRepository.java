package mindata.superhero.api.repositories;

import mindata.superhero.api.models.SuperHero;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero,Long> {
    @Cacheable("superHeroesByName")
    @Query("SELECT s FROM SuperHero s WHERE lower(s.name) LIKE lower(concat('%', :sequence,'%'))")
    List<SuperHero> findSuperHeroContainingCharSequence(String sequence);

    @Cacheable("superHeroesById")
    @Override
    Optional<SuperHero> findById(Long id);
}
