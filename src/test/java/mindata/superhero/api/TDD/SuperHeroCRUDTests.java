package mindata.superhero.api.TDD;

import mindata.superhero.api.ApplicationTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import mindata.superhero.api.exceptions.HeroNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = { ApplicationTests.class })
public class SuperHeroCRUDTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void whenGetAllHeroes_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/findAllHeroes")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero[*].id").isNotEmpty());

    }

    @Test
    public void givenNoHeroesExist_WhenGetAllHeroes_ThenNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superheroes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HeroNotFoundException));
    }

    @Test
    public void whenGetHeroByName_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .content("Bat")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero[*].id").isNotEmpty());
    }

    @Test
    public void whenGetHeroByNameAndItDoesntExists_ThenNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .content("Bat")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HeroNotFoundException));
    }

    @Test
    public void whenModifyHero_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/superHero")
                        .content("'name': 'Batman', 'id':'1'")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero[*].id").isNotEmpty());
    }

    @Test
    public void whenDeleteHero_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/superHero")
                        .content("'name':'Batman', 'id':'1'")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.superhero[*].id").isNotEmpty());
    }

}
