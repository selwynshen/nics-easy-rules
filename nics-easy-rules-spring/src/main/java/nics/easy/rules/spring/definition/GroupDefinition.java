/**
 * hzmmy.com Inc.
 * Copyright (c) 2013-2066 All Rights Reserved.
 */
package nics.easy.rules.spring.definition;

import lombok.Data;

import java.util.List;

/**
 * @author Selwyn
 * @version $Id: GroupDefinition.java, v 0.1 11/24/2018 7:03 PM Selwyn Exp $
 */
@Data
public class GroupDefinition {

    private String name;

    private List<RuleDefinition> rules;
}
