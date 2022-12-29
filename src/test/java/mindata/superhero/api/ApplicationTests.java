package mindata.superhero.api;

import mindata.superhero.api.exceptions.HeroNotFoundException;
import mindata.superhero.api.models.SuperHero;
import mindata.superhero.api.repositories.SuperHeroRepository;
import mindata.superhero.api.services.SuperHeroService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.*;

import java.util.Optional;


public class ApplicationTests {

	@Mock
	SuperHeroRepository superHeroRepository;
	@InjectMocks
	SuperHeroService superHeroService;

	@Before
	public void setUp(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	public void whenUpdateAnExistingHero_ThenSuccess() {
		var superHero = Optional.of(new SuperHero(1L, "MOCKMAN"));
		Mockito.when(superHeroRepository.findById(ArgumentMatchers.any())).thenReturn(superHero);
		superHeroService.saveSuperHero(superHero.get());
		Mockito.verify(superHeroRepository).save(ArgumentMatchers.any());
	}

	@Test
	public void whenUpdateNonExistingHero_ThenNotFound() {
		var superHero = Optional.of(new SuperHero(1L, "MOCKMAN"));
		Mockito.when(superHeroRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		Assertions.assertThrows(HeroNotFoundException.class, () -> {
			superHeroService.saveSuperHero(superHero.get());
		});
		Mockito.verify(superHeroRepository, Mockito.times(0)).save(ArgumentMatchers.any());
	}

}
