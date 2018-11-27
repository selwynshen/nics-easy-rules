# Nics Easy Rules  简易规则引擎（基于easy-rules）
一个简单的规则引擎工具，适合判断条件比较多的项目使用，尤其是那种如果满足条件，则会执行一堆业务逻辑或者校验很频繁的业务场景。

## 项目依赖
```xml
<!--nics-easy-rules-->
<dependency>
    <groupId>cn.mmy</groupId>
    <artifactId>nics-easy-rules-spring</artifactId>
    <version>1.0</version>
</dependency>
```
## nics-easy-rules 配置(spring boot项目)
新增配置类，RulesEngineFactoryBean用于生成默认的规则引擎对象，RulesDefinitionFactory用于初始化加载yml规则组配置，GroupRulesRegister用于给规则组对象配置规则
```java
@Configuration
public class EasyRulesConfigurer {

    @Bean
    RulesEngineFactoryBean rulesEngineFactoryBean()
    {
        return new RulesEngineFactoryBean();
    }

    @Bean
    @ConfigurationProperties(prefix = "nics.easyrules")
    RulesDefinitionFactory rulesDefinitionFactory()
    {
        return new RulesDefinitionFactory();
    }

    @Bean
    GroupRulesRegister groupRulesRegister()
    {
        return new GroupRulesRegister();
    }
}
```
项目application.properties添加如下配置
```properties
nics.easyrules.rules-locations=classpath:rules/*-rules.yml
```
最小规则类写法：
```java
@Rule(name="orgNotExistRuleRule", description = "机构不存在规则", priority = 1020)
@Component
@Scope("prototype")
public class OrgNotExistRule extends AbstractRule {
    @Condition
    public boolean condition(AdvFacts facts)
    {
        return RandomUtils.nextBoolean();
    }

    @Action
    public void action(AdvFacts facts)
    {
        System.out.println("机构信息不全，报问题件");
        //结束后续规则的校验
        facts.putEnd(true);
    }
}
```
规则组写法：
```java
@Component
public class EntryInvoiceGroupRules extends AbstractGroupRules {
    @Override
    public void registerRules(Rules rules) {
    }
}
```
然后再resources/rules下面创建规则组配置文件
```yaml
group:
  name: entryInvoiceGroupRules
  rules:
    - name: hospitalNotDesignatedRule
      priority: 2
    - name: orgNotExistRule
      priority: 3
    - name: socCatelogNotExistRule
      priority: 4
```
执行规则组的大致写法：
```java
@Autowired
private RulesEngine rulesEngine;

AdvFacts facts = new AdvFacts();
//往facts里塞参数
this.rulesEngine.fire(entryInvoiceGroupRules.getRules(), facts);
```