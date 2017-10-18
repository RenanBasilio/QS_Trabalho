package com.g1.disciplina.web.rest;

import com.g1.disciplina.QsTrabalhoDisciplinaApp;

import com.g1.disciplina.domain.Programa;
import com.g1.disciplina.repository.ProgramaRepository;
import com.g1.disciplina.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProgramaResource REST controller.
 *
 * @see ProgramaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsTrabalhoDisciplinaApp.class)
public class ProgramaResourceIntTest {

    private static final String DEFAULT_SIGLA = "AAAAAAAAAA";
    private static final String UPDATED_SIGLA = "BBBBBBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ProgramaRepository programaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProgramaMockMvc;

    private Programa programa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProgramaResource programaResource = new ProgramaResource(programaRepository);
        this.restProgramaMockMvc = MockMvcBuilders.standaloneSetup(programaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Programa createEntity(EntityManager em) {
        Programa programa = new Programa()
            .sigla(DEFAULT_SIGLA)
            .nome(DEFAULT_NOME);
        return programa;
    }

    @Before
    public void initTest() {
        programa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrograma() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().size();

        // Create the Programa
        restProgramaMockMvc.perform(post("/api/programas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programa)))
            .andExpect(status().isCreated());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate + 1);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getSigla()).isEqualTo(DEFAULT_SIGLA);
        assertThat(testPrograma.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createProgramaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = programaRepository.findAll().size();

        // Create the Programa with an existing ID
        programa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProgramaMockMvc.perform(post("/api/programas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProgramas() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get all the programaList
        restProgramaMockMvc.perform(get("/api/programas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(programa.getId().intValue())))
            .andExpect(jsonPath("$.[*].sigla").value(hasItem(DEFAULT_SIGLA.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())));
    }

    @Test
    @Transactional
    public void getPrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);

        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", programa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(programa.getId().intValue()))
            .andExpect(jsonPath("$.sigla").value(DEFAULT_SIGLA.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrograma() throws Exception {
        // Get the programa
        restProgramaMockMvc.perform(get("/api/programas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);
        int databaseSizeBeforeUpdate = programaRepository.findAll().size();

        // Update the programa
        Programa updatedPrograma = programaRepository.findOne(programa.getId());
        updatedPrograma
            .sigla(UPDATED_SIGLA)
            .nome(UPDATED_NOME);

        restProgramaMockMvc.perform(put("/api/programas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrograma)))
            .andExpect(status().isOk());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate);
        Programa testPrograma = programaList.get(programaList.size() - 1);
        assertThat(testPrograma.getSigla()).isEqualTo(UPDATED_SIGLA);
        assertThat(testPrograma.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingPrograma() throws Exception {
        int databaseSizeBeforeUpdate = programaRepository.findAll().size();

        // Create the Programa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProgramaMockMvc.perform(put("/api/programas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(programa)))
            .andExpect(status().isCreated());

        // Validate the Programa in the database
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrograma() throws Exception {
        // Initialize the database
        programaRepository.saveAndFlush(programa);
        int databaseSizeBeforeDelete = programaRepository.findAll().size();

        // Get the programa
        restProgramaMockMvc.perform(delete("/api/programas/{id}", programa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Programa> programaList = programaRepository.findAll();
        assertThat(programaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programa.class);
        Programa programa1 = new Programa();
        programa1.setId(1L);
        Programa programa2 = new Programa();
        programa2.setId(programa1.getId());
        assertThat(programa1).isEqualTo(programa2);
        programa2.setId(2L);
        assertThat(programa1).isNotEqualTo(programa2);
        programa1.setId(null);
        assertThat(programa1).isNotEqualTo(programa2);
    }
}
