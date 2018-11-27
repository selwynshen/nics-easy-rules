/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.support;

import com.github.selwynshen.nics.rules.api.Prioritized;
import nics.easy.rules.spring.definition.GroupDefinition;
import nics.easy.rules.spring.definition.RuleDefinition;
import nics.easy.rules.spring.group.AbstractGroupRules;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;

import java.util.Map;

/**
 * @author Selwyn
 * @version $Id: GroupRulesRegister.java, v 0.1 11/26/2018 2:26 PM Selwyn Exp $
 */
@DependsOn("rulesDefinitionFactory")
public class GroupRulesRegister implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private RulesDefinitionFactory definitionFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        //找到groupRules，对其进行注册rule操作
        Map<String, GroupDefinition> definitionMap = this.definitionFactory.getGroupsDefinitionMap();
        for (Map.Entry<String, GroupDefinition> entry : definitionMap.entrySet()) {
            AbstractGroupRules groupRules = this.applicationContext.getBean(entry.getKey(), AbstractGroupRules.class);
            if (groupRules != null) {
                this.registerRules(groupRules, entry.getValue());
            }
        }
    }

    /**
     * 最小规则注册
     * @param groupRules
     * @param groupDefinition
     * @throws Exception
     */
    private void registerRules(AbstractGroupRules groupRules, GroupDefinition groupDefinition) throws Exception
    {
        for (RuleDefinition ruleDefinition : groupDefinition.getRules())
        {
            Object ruleObj = this.applicationContext.getBean(ruleDefinition.getName());
            Integer priority = ruleDefinition.getPriority();
            if (ruleObj instanceof Prioritized) {
                groupRules.getRules().register((Prioritized)ruleObj, priority);
            } else {
                groupRules.getRules().register(ruleObj);
            }

        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
