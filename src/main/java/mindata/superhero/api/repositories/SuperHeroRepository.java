package mindata.superhero.api.repositories;

import mindata.superhero.api.models.SuperHero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperHeroRepository extends JpaRepository<SuperHero,String> {
}
