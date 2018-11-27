/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.rule;

import cn.mmy.nics.rules.annotation.Action;
import cn.mmy.nics.rules.annotation.Condition;
import cn.mmy.nics.rules.annotation.Fact;

import static nics.easy.rules.spring.util.Constants.FACTS_PARAM_KEY;

/**
 * 抽象简单最小规则
 * @author Selwyn
 * @version $Id: AbstractSimpleRule.java, v 0.1 11/21/2018 3:08 PM Selwyn Exp $
 */
public abstract class AbstractSimpleRule<T> extends AbstractRule{

    /**
     *判断是否满足规则
     * @param t
     * @return
     */
    public abstract boolean doEvaluate(T t);

    /**
     * 执行
     * @param t
     */
    public abstract void doAction(T t);

    @Condition
    public boolean evaluate(@Fact(FACTS_PARAM_KEY) T t)
    {
        return this.doEvaluate(t);
    }

    @Action
    public void action(@Fact(FACTS_PARAM_KEY) T t)
    {
        this.doAction(t);
    }

}
