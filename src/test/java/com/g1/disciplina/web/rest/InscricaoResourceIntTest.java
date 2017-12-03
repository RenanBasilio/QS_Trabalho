package com.g1.disciplina.web.rest;

import com.g1.disciplina.QsTrabalhoDisciplinaApp;

import com.g1.disciplina.domain.Inscricao;
import com.g1.disciplina.repository.InscricaoRepository;
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

import com.g1.disciplina.domain.enumeration.EstadoInscricao;
import com.g1.disciplina.domain.enumeration.Nota;
/**
 * Test class for the InscricaoResource REST controller.
 *
 * @see InscricaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsTrabalhoDisciplinaApp.class)
public class InscricaoResourceIntTest {

    private static final EstadoInscricao DEFAULT_STATUS = EstadoInscricao.NORMAL;
    private static final EstadoInscricao UPDATED_STATUS = EstadoInscricao.PENDENTE;

    private static final Nota DEFAULT_NOTA = Nota.A;
    private static final Nota UPDATED_NOTA = Nota.B;

    private static final Boolean DEFAULT_NOTIFICADO = false;
    private static final Boolean UPDATED_NOTIFICADO = true;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInscricaoMockMvc;

    private Inscricao inscricao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InscricaoResource inscricaoResource = new InscricaoResource(inscricaoRepository);
        this.restInscricaoMockMvc = MockMvcBuilders.standaloneSetup(inscricaoResource)
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
    public static Inscricao createEntity(EntityManager em) {
        Inscricao inscricao = new Inscricao()
            .status(DEFAULT_STATUS)
            .nota(DEFAULT_NOTA)
            .notificado(DEFAULT_NOTIFICADO);
        return inscricao;
    }

    @Before
    public void initTest() {
        inscricao = createEntity(em);
    }

    @Test
    @Transactional
    public void createInscricao() throws Exception {
        int databaseSizeBeforeCreate = inscricaoRepository.findAll().size();

        // Create the Inscricao
        restInscricaoMockMvc.perform(post("/api/inscricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscricao)))
            .andExpect(status().isCreated());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaoList = inscricaoRepository.findAll();
        assertThat(inscricaoList).hasSize(databaseSizeBeforeCreate + 1);
        Inscricao testInscricao = inscricaoList.get(inscricaoList.size() - 1);
        assertThat(testInscricao.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testInscricao.getNota()).isEqualTo(DEFAULT_NOTA);
        assertThat(testInscricao.isNotificado()).isEqualTo(DEFAULT_NOTIFICADO);
    }

    @Test
    @Transactional
    public void createInscricaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inscricaoRepository.findAll().size();

        // Create the Inscricao with an existing ID
        inscricao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInscricaoMockMvc.perform(post("/api/inscricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscricao)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Inscricao> inscricaoList = inscricaoRepository.findAll();
        assertThat(inscricaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInscricaos() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get all the inscricaoList
        restInscricaoMockMvc.perform(get("/api/inscricaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inscricao.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA.toString())))
            .andExpect(jsonPath("$.[*].notificado").value(hasItem(DEFAULT_NOTIFICADO.booleanValue())));
    }

    @Test
    @Transactional
    public void getInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);

        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", inscricao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inscricao.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA.toString()))
            .andExpect(jsonPath("$.notificado").value(DEFAULT_NOTIFICADO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInscricao() throws Exception {
        // Get the inscricao
        restInscricaoMockMvc.perform(get("/api/inscricaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeUpdate = inscricaoRepository.findAll().size();

        // Update the inscricao
        Inscricao updatedInscricao = inscricaoRepository.findOne(inscricao.getId());
        updatedInscricao
            .status(UPDATED_STATUS)
            .nota(UPDATED_NOTA)
            .notificado(UPDATED_NOTIFICADO);

        restInscricaoMockMvc.perform(put("/api/inscricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInscricao)))
            .andExpect(status().isOk());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaoList = inscricaoRepository.findAll();
        assertThat(inscricaoList).hasSize(databaseSizeBeforeUpdate);
        Inscricao testInscricao = inscricaoList.get(inscricaoList.size() - 1);
        assertThat(testInscricao.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testInscricao.getNota()).isEqualTo(UPDATED_NOTA);
        assertThat(testInscricao.isNotificado()).isEqualTo(UPDATED_NOTIFICADO);
    }

    @Test
    @Transactional
    public void updateNonExistingInscricao() throws Exception {
        int databaseSizeBeforeUpdate = inscricaoRepository.findAll().size();

        // Create the Inscricao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInscricaoMockMvc.perform(put("/api/inscricaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inscricao)))
            .andExpect(status().isCreated());

        // Validate the Inscricao in the database
        List<Inscricao> inscricaoList = inscricaoRepository.findAll();
        assertThat(inscricaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInscricao() throws Exception {
        // Initialize the database
        inscricaoRepository.saveAndFlush(inscricao);
        int databaseSizeBeforeDelete = inscricaoRepository.findAll().size();

        // Get the inscricao
        restInscricaoMockMvc.perform(delete("/api/inscricaos/{id}", inscricao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inscricao> inscricaoList = inscricaoRepository.findAll();
        assertThat(inscricaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inscricao.class);
        Inscricao inscricao1 = new Inscricao();
        inscricao1.setId(1L);
        Inscricao inscricao2 = new Inscricao();
        inscricao2.setId(inscricao1.getId());
        assertThat(inscricao1).isEqualTo(inscricao2);
        inscricao2.setId(2L);
        assertThat(inscricao1).isNotEqualTo(inscricao2);
        inscricao1.setId(null);
        assertThat(inscricao1).isNotEqualTo(inscricao2);
    }
}
