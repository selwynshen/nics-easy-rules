/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.support;

import lombok.extern.slf4j.Slf4j;
import nics.easy.rules.spring.definition.GroupDefinition;
import nics.easy.rules.spring.definition.GroupsDefinition;
import nics.easy.rules.spring.reader.RulesReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * 规则读取工厂对象（用于读取yml中规则配置）
 * @author Selwyn
 * @version $Id: EasyRulesFactory.java, v 0.1 11/26/2018 9:58 AM Selwyn Exp $
 */
@Slf4j
public class RulesDefinitionFactory implements InitializingBean{
    /**
     * 规则yml文件所在位置（正则）
     */
    private String rulesLocations;

    /**
     * 是否加载过配置
     */
    private boolean rulesLoaded;

    private RulesReader rulesReader;

    /**
     * key: groupRules name
     * value: GroupDefinition
     */
    private Map<String, GroupDefinition> groupsDefinitionMap;

    private void init()
    {
        this.rulesReader = new RulesReader();
        this.groupsDefinitionMap = new HashMap<>();
    }

    public RulesDefinitionFactory()
    {
        this.init();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.loadRules();
        log.info("rules definitions has been loaded");
    }

    private void loadRules() throws Exception
    {
        if (!this.rulesLoaded) {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(this.rulesLocations);
            Reader tempReader;
            for (Resource resource : resources)
            {
                //bug fix：修复spring boot jar包无法读取规则配置文件的bug 20190128
                tempReader = new InputStreamReader(resource.getInputStream(), "utf-8");
                //tempReader = new FileReader(resource.getFile());
                GroupsDefinition groupsDefinition =
                        this.rulesReader.read(tempReader);
                if (groupsDefinition != null && groupsDefinition.getGroup() != null) {
                    GroupDefinition groupDefinition = groupsDefinition.getGroup();
                    this.groupsDefinitionMap.put(groupDefinition.getName(), groupDefinition);
                }
            }
            this.rulesLoaded = true;
        }

    }

    public void setRulesLocations(String rulesLocations) {
        this.rulesLocations = rulesLocations;
    }

    public Map<String, GroupDefinition> getGroupsDefinitionMap() {
        return groupsDefinitionMap;
    }
}
