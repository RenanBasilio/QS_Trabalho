package com.g1.disciplina.web.rest;

import com.g1.disciplina.QsTrabalhoDisciplinaApp;

import com.g1.disciplina.domain.Disciplina;
import com.g1.disciplina.repository.DisciplinaRepository;
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

import com.g1.disciplina.domain.enumeration.EstadoDisciplina;
import com.g1.disciplina.domain.enumeration.TipoDisciplina;
/**
 * Test class for the DisciplinaResource REST controller.
 *
 * @see DisciplinaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QsTrabalhoDisciplinaApp.class)
public class DisciplinaResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBB";

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final EstadoDisciplina DEFAULT_ESTADO = EstadoDisciplina.INSCRICAO;
    private static final EstadoDisciplina UPDATED_ESTADO = EstadoDisciplina.TRANCAMENTO;

    private static final Integer DEFAULT_TRIMESTRE = 1;
    private static final Integer UPDATED_TRIMESTRE = 2;

    private static final Integer DEFAULT_CARGA_HORARIA = 1;
    private static final Integer UPDATED_CARGA_HORARIA = 2;

    private static final Integer DEFAULT_CREDITOS = 1;
    private static final Integer UPDATED_CREDITOS = 2;

    private static final TipoDisciplina DEFAULT_TIPO = TipoDisciplina.CURSO;
    private static final TipoDisciplina UPDATED_TIPO = TipoDisciplina.PESQUISA;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDisciplinaMockMvc;

    private Disciplina disciplina;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisciplinaResource disciplinaResource = new DisciplinaResource(disciplinaRepository);
        this.restDisciplinaMockMvc = MockMvcBuilders.standaloneSetup(disciplinaResource)
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
    public static Disciplina createEntity(EntityManager em) {
        Disciplina disciplina = new Disciplina()
            .codigo(DEFAULT_CODIGO)
            .nome(DEFAULT_NOME)
            .estado(DEFAULT_ESTADO)
            .trimestre(DEFAULT_TRIMESTRE)
            .cargaHoraria(DEFAULT_CARGA_HORARIA)
            .creditos(DEFAULT_CREDITOS)
            .tipo(DEFAULT_TIPO);
        return disciplina;
    }

    @Before
    public void initTest() {
        disciplina = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisciplina() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate + 1);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testDisciplina.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDisciplina.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testDisciplina.getTrimestre()).isEqualTo(DEFAULT_TRIMESTRE);
        assertThat(testDisciplina.getCargaHoraria()).isEqualTo(DEFAULT_CARGA_HORARIA);
        assertThat(testDisciplina.getCreditos()).isEqualTo(DEFAULT_CREDITOS);
        assertThat(testDisciplina.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void createDisciplinaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplinaRepository.findAll().size();

        // Create the Disciplina with an existing ID
        disciplina.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = disciplinaRepository.findAll().size();
        // set the field null
        disciplina.setCodigo(null);

        // Create the Disciplina, which fails.

        restDisciplinaMockMvc.perform(post("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isBadRequest());

        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisciplinas() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get all the disciplinaList
        restDisciplinaMockMvc.perform(get("/api/disciplinas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disciplina.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].trimestre").value(hasItem(DEFAULT_TRIMESTRE)))
            .andExpect(jsonPath("$.[*].cargaHoraria").value(hasItem(DEFAULT_CARGA_HORARIA)))
            .andExpect(jsonPath("$.[*].creditos").value(hasItem(DEFAULT_CREDITOS)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);

        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", disciplina.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disciplina.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.trimestre").value(DEFAULT_TRIMESTRE))
            .andExpect(jsonPath("$.cargaHoraria").value(DEFAULT_CARGA_HORARIA))
            .andExpect(jsonPath("$.creditos").value(DEFAULT_CREDITOS))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDisciplina() throws Exception {
        // Get the disciplina
        restDisciplinaMockMvc.perform(get("/api/disciplinas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Update the disciplina
        Disciplina updatedDisciplina = disciplinaRepository.findOne(disciplina.getId());
        updatedDisciplina
            .codigo(UPDATED_CODIGO)
            .nome(UPDATED_NOME)
            .estado(UPDATED_ESTADO)
            .trimestre(UPDATED_TRIMESTRE)
            .cargaHoraria(UPDATED_CARGA_HORARIA)
            .creditos(UPDATED_CREDITOS)
            .tipo(UPDATED_TIPO);

        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisciplina)))
            .andExpect(status().isOk());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate);
        Disciplina testDisciplina = disciplinaList.get(disciplinaList.size() - 1);
        assertThat(testDisciplina.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testDisciplina.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDisciplina.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testDisciplina.getTrimestre()).isEqualTo(UPDATED_TRIMESTRE);
        assertThat(testDisciplina.getCargaHoraria()).isEqualTo(UPDATED_CARGA_HORARIA);
        assertThat(testDisciplina.getCreditos()).isEqualTo(UPDATED_CREDITOS);
        assertThat(testDisciplina.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void updateNonExistingDisciplina() throws Exception {
        int databaseSizeBeforeUpdate = disciplinaRepository.findAll().size();

        // Create the Disciplina

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDisciplinaMockMvc.perform(put("/api/disciplinas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disciplina)))
            .andExpect(status().isCreated());

        // Validate the Disciplina in the database
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDisciplina() throws Exception {
        // Initialize the database
        disciplinaRepository.saveAndFlush(disciplina);
        int databaseSizeBeforeDelete = disciplinaRepository.findAll().size();

        // Get the disciplina
        restDisciplinaMockMvc.perform(delete("/api/disciplinas/{id}", disciplina.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Disciplina> disciplinaList = disciplinaRepository.findAll();
        assertThat(disciplinaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disciplina.class);
        Disciplina disciplina1 = new Disciplina();
        disciplina1.setId(1L);
        Disciplina disciplina2 = new Disciplina();
        disciplina2.setId(disciplina1.getId());
        assertThat(disciplina1).isEqualTo(disciplina2);
        disciplina2.setId(2L);
        assertThat(disciplina1).isNotEqualTo(disciplina2);
        disciplina1.setId(null);
        assertThat(disciplina1).isNotEqualTo(disciplina2);
    }
}
