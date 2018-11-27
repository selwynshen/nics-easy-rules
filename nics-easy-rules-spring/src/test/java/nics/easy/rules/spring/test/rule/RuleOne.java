/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.test.rule;

import nics.easy.rules.spring.rule.AbstractSimpleRule;
import com.github.selwynshen.nics.rules.annotation.Rule;

/**
 * @author Selwyn
 * @version $Id: RuleOne.java, v 0.1 11/24/2018 4:29 PM Selwyn Exp $
 */
@Rule(name="ruleOne", priority = 2)
public class RuleOne extends AbstractSimpleRule<String>{
    @Override
    public boolean doEvaluate(String s) {
        return true;
    }

    @Override
    public void doAction(String s) {
        System.out.println("rule1");
    }
}
