/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.support;

import nics.easy.rules.spring.listener.EndRuleListener;
import com.github.selwynshen.nics.rules.api.RulesEngine;
import com.github.selwynshen.nics.rules.core.DefaultRulesEngine;
import org.springframework.beans.factory.FactoryBean;

/**
 * 默认规则引擎对象
 * @author Selwyn
 * @version $Id: RulesEngineFactoryBean.java, v 0.1 11/26/2018 10:21 AM Selwyn Exp $
 */

public class RulesEngineFactoryBean implements FactoryBean<RulesEngine> {
    /**
     * 默认规则引擎类（可结束）
     */
    private RulesEngine rulesEngine;

    private void init()
    {
        this.rulesEngine = new DefaultRulesEngine();
        EndRuleListener endRuleListener = new EndRuleListener();
        this.rulesEngine.getRuleListeners().add(endRuleListener);
    }

    public RulesEngineFactoryBean()
    {
        this.init();
    }

    @Override
    public RulesEngine getObject() throws Exception {
        return this.rulesEngine;
    }

    @Override
    public Class<?> getObjectType() {
        return RulesEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
