package com.g1.disciplina.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.g1.disciplina.domain.enumeration.EstadoDisciplina;

import com.g1.disciplina.domain.enumeration.TipoDisciplina;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 6)
    @Column(name = "codigo", length = 6, nullable = false)
    private String codigo;

    @Column(name = "nome")
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoDisciplina estado;

    @Column(name = "trimestre")
    private Integer trimestre;

    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    @Column(name = "creditos")
    private Integer creditos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoDisciplina tipo;

    @OneToOne
    @JoinColumn(unique = true)
    private Professor professor;

    @OneToOne
    @JoinColumn(unique = true)
    private Programa programa;

    @ManyToOne
    private Disciplina disciplina;

    @OneToMany(mappedBy = "disciplina")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Disciplina> requisitos = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Disciplina codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public Disciplina nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoDisciplina getEstado() {
        return estado;
    }

    public Disciplina estado(EstadoDisciplina estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoDisciplina estado) {
        this.estado = estado;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public Disciplina trimestre(Integer trimestre) {
        this.trimestre = trimestre;
        return this;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public Disciplina cargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
        return this;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public Disciplina creditos(Integer creditos) {
        this.creditos = creditos;
        return this;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }

    public TipoDisciplina getTipo() {
        return tipo;
    }

    public Disciplina tipo(TipoDisciplina tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(TipoDisciplina tipo) {
        this.tipo = tipo;
    }

    public Professor getProfessor() {
        return professor;
    }

    public Disciplina professor(Professor professor) {
        this.professor = professor;
        return this;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Programa getPrograma() {
        return programa;
    }

    public Disciplina programa(Programa programa) {
        this.programa = programa;
        return this;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public Disciplina disciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
        return this;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public Set<Disciplina> getRequisitos() {
        return requisitos;
    }

    public Disciplina requisitos(Set<Disciplina> disciplinas) {
        this.requisitos = disciplinas;
        return this;
    }

    public Disciplina addRequisito(Disciplina disciplina) {
        this.requisitos.add(disciplina);
        disciplina.setDisciplina(this);
        return this;
    }

    public Disciplina removeRequisito(Disciplina disciplina) {
        this.requisitos.remove(disciplina);
        disciplina.setDisciplina(null);
        return this;
    }

    public void setRequisitos(Set<Disciplina> disciplinas) {
        this.requisitos = disciplinas;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disciplina disciplina = (Disciplina) o;
        if (disciplina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disciplina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", nome='" + getNome() + "'" +
            ", estado='" + getEstado() + "'" +
            ", trimestre='" + getTrimestre() + "'" +
            ", cargaHoraria='" + getCargaHoraria() + "'" +
            ", creditos='" + getCreditos() + "'" +
            ", tipo='" + getTipo() + "'" +
            "}";
    }
}
