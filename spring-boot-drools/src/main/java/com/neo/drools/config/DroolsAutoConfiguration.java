package com.neo.drools.config;

import com.neo.drools.enums.DroolsEnum;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DroolsAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public Map<String, KieSession> getMapKieSession() throws IOException {
        Map<String, KieSession> kieSessionMap = new HashMap<String, KieSession>();
        for (DroolsEnum path : DroolsEnum.values()) {
            KieServices kieServices = KieServices.Factory.get();
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            Resource[] resource = resourcePatternResolver.getResources("classpath*:" + path.getRulesPath() + "**/*.*");
            for (Resource file : resource) {
                kieFileSystem.write(ResourceFactory.newClassPathResource(path.getRulesPath() + file.getFilename(), "UTF-8"));
            }
            final KieRepository kieRepository = kieServices.getRepository();
            kieRepository.addKieModule(new KieModule() {
                public ReleaseId getReleaseId() {
                    return kieRepository.getDefaultReleaseId();
                }
            });
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            KieContainer kieContainer = kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
            KieSession kieSession = kieContainer.newKieSession();
            kieSessionMap.put(path.getRulesName(), kieSession);
        }
        return kieSessionMap;
    }


}
