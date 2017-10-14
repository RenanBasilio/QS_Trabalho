package com.g1.disciplina.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Local.
 */
@Entity
@Table(name = "local")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Local implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "centro")
    private String centro;

    @Column(name = "bloco")
    private String bloco;

    @Column(name = "sala")
    private String sala;

    @Column(name = "externo")
    private Boolean externo;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCentro() {
        return centro;
    }

    public Local centro(String centro) {
        this.centro = centro;
        return this;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getBloco() {
        return bloco;
    }

    public Local bloco(String bloco) {
        this.bloco = bloco;
        return this;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }

    public String getSala() {
        return sala;
    }

    public Local sala(String sala) {
        this.sala = sala;
        return this;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public Boolean isExterno() {
        return externo;
    }

    public Local externo(Boolean externo) {
        this.externo = externo;
        return this;
    }

    public void setExterno(Boolean externo) {
        this.externo = externo;
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
        Local local = (Local) o;
        if (local.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), local.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Local{" +
            "id=" + getId() +
            ", centro='" + getCentro() + "'" +
            ", bloco='" + getBloco() + "'" +
            ", sala='" + getSala() + "'" +
            ", externo='" + isExterno() + "'" +
            "}";
    }
}
