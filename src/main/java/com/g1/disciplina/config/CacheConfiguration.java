package com.g1.disciplina.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.g1.disciplina.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Disciplina.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Disciplina.class.getName() + ".requisitos", jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.TimeSlot.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Programa.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Pessoa.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Professor.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Aluno.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Local.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Turma.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Turma.class.getName() + ".horarios", jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Inscricao.class.getName(), jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Disciplina.class.getName() + ".disciplinas", jcacheConfiguration);
            cm.createCache(com.g1.disciplina.domain.Disciplina.class.getName() + ".dependentes", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
