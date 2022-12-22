package mindata.superhero.api.TDD;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import mindata.superhero.api.Application;
import mindata.superhero.api.models.SuperHero;
import mindata.superhero.api.repositories.SuperHeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import mindata.superhero.api.exceptions.HeroNotFoundException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@Transactional
@SpringBootConfiguration
@WithMockUser(authorities = {"SCOPE_q2-reprocess-resource-server/octa_scope"})
public class SuperHeroCRUDTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SuperHeroRepository repository;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void whenGetAllHeroes_ThenSuccess() throws Exception {
        List<SuperHero> superHeroes = repository.findAll();

        ResultActions getAllHeroesResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/findAllHeroes")
                .accept(MediaType.APPLICATION_JSON));

        for (int i = 0; i < superHeroes.size(); i++) {
            String heroName = superHeroes.get(i).getName();
            Long heroId = superHeroes.get(i).getId();
            getAllHeroesResult
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].id").value(heroId))
                    .andExpect(MockMvcResultMatchers.jsonPath("$["+i+"].name").value(heroName));
        }
    }

    @Test
    public void givenNoHeroesExist_WhenGetAllHeroes_ThenNotFound() throws Exception {
        repository.deleteAll();
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/findAllHeroes")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HeroNotFoundException));
    }

    @Test
    public void whenGetHeroByName_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .param("name", "SPI")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("SPIDERMAN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value("6"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("SPIDERGIRL"));
    }

    @Test
    public void whenGetHeroByNameAndItDoesntExists_ThenNotFound() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .param("name", "SUPERMAN")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof HeroNotFoundException));
    }

    @Test
    public void whenModifyHero_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .param("name", "SPIDERMAN")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("SPIDERMAN"));

        String heroJson = createRequestJson(new SuperHero(1L,"BATMAN"));

        this.mockMvc.perform(post("/superHero")
                        .content(heroJson)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("BATMAN"));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHero")
                        .param("name", "Batman")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("BATMAN"));
    }

    @Test
    public void whenDeleteHero_ThenSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/superHero")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/superHeroById")
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private String createRequestJson(SuperHero superHero) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(superHero);
    }
}
