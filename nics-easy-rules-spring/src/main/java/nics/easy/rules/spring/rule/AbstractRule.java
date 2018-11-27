/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.rule;

import cn.mmy.nics.rules.api.Prioritized;

/**
 * @author Selwyn
 * @version $Id: AbstractRule.java, v 0.1 11/24/2018 4:30 PM Selwyn Exp $
 */
public abstract class AbstractRule implements Prioritized{

    private Integer priority;

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public Integer getPriority() {
        return this.priority;
    }
}
