package com.g1.disciplina.web.rest;

import com.g1.disciplina.QsTrabalhoDisciplinaApp;

import com.g1.disciplina.domain.Local;
import com.g1.disciplina.repository.LocalRepository;
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
 * Test class for the LocalResource REST controller.
 *
 * @see LocalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsTrabalhoDisciplinaApp.class)
public class LocalResourceIntTest {

    private static final String DEFAULT_CENTRO = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO = "BBBBBBBBBB";

    private static final String DEFAULT_BLOCO = "AAAAAAAAAA";
    private static final String UPDATED_BLOCO = "BBBBBBBBBB";

    private static final String DEFAULT_SALA = "AAAAAAAAAA";
    private static final String UPDATED_SALA = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EXTERNO = false;
    private static final Boolean UPDATED_EXTERNO = true;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocalMockMvc;

    private Local local;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocalResource localResource = new LocalResource(localRepository);
        this.restLocalMockMvc = MockMvcBuilders.standaloneSetup(localResource)
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
    public static Local createEntity(EntityManager em) {
        Local local = new Local()
            .centro(DEFAULT_CENTRO)
            .bloco(DEFAULT_BLOCO)
            .sala(DEFAULT_SALA)
            .externo(DEFAULT_EXTERNO);
        return local;
    }

    @Before
    public void initTest() {
        local = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocal() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isCreated());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate + 1);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getCentro()).isEqualTo(DEFAULT_CENTRO);
        assertThat(testLocal.getBloco()).isEqualTo(DEFAULT_BLOCO);
        assertThat(testLocal.getSala()).isEqualTo(DEFAULT_SALA);
        assertThat(testLocal.isExterno()).isEqualTo(DEFAULT_EXTERNO);
    }

    @Test
    @Transactional
    public void createLocalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local with an existing ID
        local.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocalMockMvc.perform(post("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocals() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get all the localList
        restLocalMockMvc.perform(get("/api/locals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(local.getId().intValue())))
            .andExpect(jsonPath("$.[*].centro").value(hasItem(DEFAULT_CENTRO.toString())))
            .andExpect(jsonPath("$.[*].bloco").value(hasItem(DEFAULT_BLOCO.toString())))
            .andExpect(jsonPath("$.[*].sala").value(hasItem(DEFAULT_SALA.toString())))
            .andExpect(jsonPath("$.[*].externo").value(hasItem(DEFAULT_EXTERNO.booleanValue())));
    }

    @Test
    @Transactional
    public void getLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", local.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(local.getId().intValue()))
            .andExpect(jsonPath("$.centro").value(DEFAULT_CENTRO.toString()))
            .andExpect(jsonPath("$.bloco").value(DEFAULT_BLOCO.toString()))
            .andExpect(jsonPath("$.sala").value(DEFAULT_SALA.toString()))
            .andExpect(jsonPath("$.externo").value(DEFAULT_EXTERNO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLocal() throws Exception {
        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);
        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Update the local
        Local updatedLocal = localRepository.findOne(local.getId());
        updatedLocal
            .centro(UPDATED_CENTRO)
            .bloco(UPDATED_BLOCO)
            .sala(UPDATED_SALA)
            .externo(UPDATED_EXTERNO);

        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocal)))
            .andExpect(status().isOk());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate);
        Local testLocal = localList.get(localList.size() - 1);
        assertThat(testLocal.getCentro()).isEqualTo(UPDATED_CENTRO);
        assertThat(testLocal.getBloco()).isEqualTo(UPDATED_BLOCO);
        assertThat(testLocal.getSala()).isEqualTo(UPDATED_SALA);
        assertThat(testLocal.isExterno()).isEqualTo(UPDATED_EXTERNO);
    }

    @Test
    @Transactional
    public void updateNonExistingLocal() throws Exception {
        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Create the Local

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocalMockMvc.perform(put("/api/locals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(local)))
            .andExpect(status().isCreated());

        // Validate the Local in the database
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);
        int databaseSizeBeforeDelete = localRepository.findAll().size();

        // Get the local
        restLocalMockMvc.perform(delete("/api/locals/{id}", local.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Local> localList = localRepository.findAll();
        assertThat(localList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Local.class);
        Local local1 = new Local();
        local1.setId(1L);
        Local local2 = new Local();
        local2.setId(local1.getId());
        assertThat(local1).isEqualTo(local2);
        local2.setId(2L);
        assertThat(local1).isNotEqualTo(local2);
        local1.setId(null);
        assertThat(local1).isNotEqualTo(local2);
    }
}
