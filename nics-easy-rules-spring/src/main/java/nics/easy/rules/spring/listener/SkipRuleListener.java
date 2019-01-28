/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.listener;

import com.github.selwynshen.nics.rules.api.Facts;
import com.github.selwynshen.nics.rules.api.Rule;
import com.github.selwynshen.nics.rules.core.DefaultRuleListener;
import com.github.selwynshen.nics.rules.core.RuleProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import static nics.easy.rules.spring.util.Constants.FACTS_SKIP_KEY;

/**
 * 规则跳过listener
 * @author Selwyn
 * @version $Id: SkipRuleListener.java, v 0.1 1/11/2019 7:57 PM Selwyn Exp $
 */
@Slf4j
public class SkipRuleListener extends DefaultRuleListener {

    private Class retriveTargetClass(Rule rule) throws Exception
    {
        RuleProxy proxy =
                ((RuleProxy) Proxy.getInvocationHandler(rule));
        Method method =
                ReflectionUtils.findMethod(RuleProxy.class, "getTargetClass");
        method.setAccessible(true);
        return (Class)method.invoke(proxy, null);
    }

    @Override
    public boolean beforeEvaluate(Rule rule, Facts facts) {
        Object obj = facts.get(FACTS_SKIP_KEY);
        if (obj instanceof Class) {
            try{
                Class ruleClass = this.retriveTargetClass(rule);
                boolean needSkip = (ruleClass == (Class)obj);
                if (needSkip) {
                    return false;
                }
            }catch(Exception e){
                log.warn("failed to retrieve rule class: {}", e);
            }
        }
        return true;
    }
}
