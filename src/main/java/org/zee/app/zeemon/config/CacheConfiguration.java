package org.zee.app.zeemon.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, org.zee.app.zeemon.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, org.zee.app.zeemon.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, org.zee.app.zeemon.domain.User.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Authority.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.User.class.getName() + ".authorities");
            createCache(cm, org.zee.app.zeemon.domain.Agent.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Agent.class.getName() + ".tasks");
            createCache(cm, org.zee.app.zeemon.domain.Agent.class.getName() + ".actions");
            createCache(cm, org.zee.app.zeemon.domain.EndpointProperty.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Endpoint.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Endpoint.class.getName() + ".endpointProperties");
            createCache(cm, org.zee.app.zeemon.domain.Endpoint.class.getName() + ".checkScripts");
            createCache(cm, org.zee.app.zeemon.domain.Endpoint.class.getName() + ".actionScripts");
            createCache(cm, org.zee.app.zeemon.domain.FlowExecution.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.FlowExecution.class.getName() + ".taskExecutions");
            createCache(cm, org.zee.app.zeemon.domain.FlowExecution.class.getName() + ".contents");
            createCache(cm, org.zee.app.zeemon.domain.TaskExecution.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.TaskExecution.class.getName() + ".contents");
            createCache(cm, org.zee.app.zeemon.domain.TaskExecution.class.getName() + ".contentValidationErrors");
            createCache(cm, org.zee.app.zeemon.domain.ActionExecution.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.ActionParam.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.ActionScript.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.ActionScript.class.getName() + ".actionParams");
            createCache(cm, org.zee.app.zeemon.domain.ActionScript.class.getName() + ".actions");
            createCache(cm, org.zee.app.zeemon.domain.EventTrigger.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.EventTrigger.class.getName() + ".flows");
            createCache(cm, org.zee.app.zeemon.domain.Flow.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Flow.class.getName() + ".flowExecutions");
            createCache(cm, org.zee.app.zeemon.domain.Flow.class.getName() + ".tasks");
            createCache(cm, org.zee.app.zeemon.domain.Flow.class.getName() + ".contents");
            createCache(cm, org.zee.app.zeemon.domain.Task.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Task.class.getName() + ".taskExecutions");
            createCache(cm, org.zee.app.zeemon.domain.Task.class.getName() + ".contents");
            createCache(cm, org.zee.app.zeemon.domain.Task.class.getName() + ".contentValidationErrors");
            createCache(cm, org.zee.app.zeemon.domain.Action.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Action.class.getName() + ".actionExecutions");
            createCache(cm, org.zee.app.zeemon.domain.ScriptParam.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.CheckScript.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.CheckScript.class.getName() + ".scriptParams");
            createCache(cm, org.zee.app.zeemon.domain.CheckScript.class.getName() + ".tasks");
            createCache(cm, org.zee.app.zeemon.domain.CheckScript.class.getName() + ".contents");
            createCache(cm, org.zee.app.zeemon.domain.ContentMapper.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.ContentMapper.class.getName() + ".checkScripts");
            createCache(cm, org.zee.app.zeemon.domain.ContentMapper.class.getName() + ".fieldMappings");
            createCache(cm, org.zee.app.zeemon.domain.FieldMapping.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.Content.class.getName());
            createCache(cm, org.zee.app.zeemon.domain.ContentValidationError.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache == null) {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

}
