/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.test;

import nics.easy.rules.spring.definition.GroupsDefinition;
import nics.easy.rules.spring.facts.AdvFacts;
import nics.easy.rules.spring.test.rule.RuleOne;
import nics.easy.rules.spring.test.rule.RuleTwo;
import cn.mmy.nics.rules.api.AdvRules;
import cn.mmy.nics.rules.api.RulesEngine;
import cn.mmy.nics.rules.core.DefaultRulesEngine;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Selwyn
 * @version $Id: PriorityTest.java, v 0.1 11/24/2018 4:29 PM Selwyn Exp $
 */
public class PriorityTest {

    private RuleOne ruleOne;

    private RuleTwo ruleTwo;

    private RulesEngine rulesEngine;

    private AdvRules rules;

    @Before
    public void init()
    {
        this.ruleOne = new RuleOne();
        this.ruleTwo = new RuleTwo();

        this.rulesEngine = new DefaultRulesEngine();
        this.rules = new AdvRules();
    }

    @Test
    public void testPriority1()
    {
        AdvFacts advFacts = new AdvFacts();
        advFacts.putParam("testPriority1");

        rules.register(this.ruleTwo);
        rules.register(this.ruleOne);

        this.rulesEngine.fire(rules, advFacts);
    }

    @Test
    public void testPriority2()
    {
        AdvFacts advFacts = new AdvFacts();
        advFacts.putParam("testPriority2");

        rules.register(this.ruleOne, 7);
        rules.register(this.ruleTwo, 6);

        this.rulesEngine.fire(rules, advFacts);
    }

    @Test
    public void testYamlRead() throws Exception
    {
        Yaml yaml = new Yaml();
        File file = ResourceUtils.getFile("classpath:rules.yml");
        InputStream ins = new FileInputStream(file);
        GroupsDefinition groups = yaml.loadAs(ins, GroupsDefinition.class);
        System.out.println(groups);
    }

    @Test
    public void testMultiYamlRead() throws Exception
    {
        Yaml yaml = new Yaml();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:rules*.yml");
        List<GroupsDefinition> groupsList = new ArrayList<>();
        for (Resource resource : resources)
        {
            FileReader reader = new FileReader(resource.getFile());
            GroupsDefinition groups = yaml.loadAs(reader, GroupsDefinition.class);
            groupsList.add(groups);
        }
        System.out.println(groupsList);
    }

}
