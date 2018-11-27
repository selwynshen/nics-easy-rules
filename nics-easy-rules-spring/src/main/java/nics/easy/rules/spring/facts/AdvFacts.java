/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.facts;

import cn.mmy.nics.rules.api.Facts;

import static nics.easy.rules.spring.util.Constants.*;

/**
 * 高端Facts
 * @author Selwyn
 * @version $Id: AdvFacts.java, v 0.1 11/24/2018 4:55 PM Selwyn Exp $
 */
public class AdvFacts extends Facts{

    /**
     * 放置参数
     * @param param
     * @param <T>
     */
    public <T> void putParam(T param)
    {
        put(FACTS_PARAM_KEY, param);
    }

    /**
     * 放置结束
     * @param param
     * @param <T>
     */
    public <T> void putEnd(T param)
    {
        put(FACTS_END_KEY, param);
    }
}
