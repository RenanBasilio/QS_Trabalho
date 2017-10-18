package com.g1.disciplina.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.g1.disciplina.domain.enumeration.EstadoInscricao;

import com.g1.disciplina.domain.enumeration.Nota;

/**
 * A Inscricao.
 */
@Entity
@Table(name = "inscricao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inscricao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EstadoInscricao status;

    @Enumerated(EnumType.STRING)
    @Column(name = "nota")
    private Nota nota;

    @OneToOne
    @JoinColumn(unique = true)
    private Aluno aluno;

    @OneToOne
    @JoinColumn(unique = true)
    private Turma turma;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoInscricao getStatus() {
        return status;
    }

    public Inscricao status(EstadoInscricao status) {
        this.status = status;
        return this;
    }

    public void setStatus(EstadoInscricao status) {
        this.status = status;
    }

    public Nota getNota() {
        return nota;
    }

    public Inscricao nota(Nota nota) {
        this.nota = nota;
        return this;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Inscricao aluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Turma getTurma() {
        return turma;
    }

    public Inscricao turma(Turma turma) {
        this.turma = turma;
        return this;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
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
        Inscricao inscricao = (Inscricao) o;
        if (inscricao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inscricao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inscricao{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", nota='" + getNota() + "'" +
            "}";
    }
}
