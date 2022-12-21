package mindata.superhero.api.repositories;

import mindata.superhero.api.models.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuperHeroRepository extends JpaRepository<SuperHero,Long> {
    @Query("SELECT s FROM SuperHero s WHERE lower(s.name) LIKE lower(concat('%', :sequence,'%'))")
    List<SuperHero> findSuperHeroContainingCharSequence(String sequence);
}
