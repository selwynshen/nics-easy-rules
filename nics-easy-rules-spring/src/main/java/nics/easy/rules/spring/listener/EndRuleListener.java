/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.listener;

import cn.mmy.nics.rules.api.Facts;
import cn.mmy.nics.rules.api.Rule;
import cn.mmy.nics.rules.core.DefaultRuleListener;

import static nics.easy.rules.spring.util.Constants.FACTS_END_KEY;

/**
 * 结束规则listener，往Facts里面塞FACTS_END_KEY为true参数，则该组规则接下来就会中断检验
 * @author Selwyn
 * @version $Id: EndRuleListener.java, v 0.1 11/23/2018 11:41 AM Selwyn Exp $
 */
public class EndRuleListener extends DefaultRuleListener {
    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        Object obj = facts.get(FACTS_END_KEY);
        if (obj != null && obj instanceof Boolean) {
            return !(Boolean)obj;
        }
        return true;
    }
}
