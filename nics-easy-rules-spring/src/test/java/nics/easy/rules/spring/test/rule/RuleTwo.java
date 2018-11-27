/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.test.rule;

import nics.easy.rules.spring.rule.AbstractSimpleRule;
import cn.mmy.nics.rules.annotation.Rule;

/**
 * @author Selwyn
 * @version $Id: RuleTwo.java, v 0.1 11/24/2018 4:37 PM Selwyn Exp $
 */
@Rule(name="ruleTwo", priority = 3)
public class RuleTwo extends AbstractSimpleRule<String> {
    @Override
    public boolean doEvaluate(String s) {
        return true;
    }

    @Override
    public void doAction(String s) {
        System.out.println("rule2");
    }
}
