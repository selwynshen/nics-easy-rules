# Nics Easy Rules  简易规则引擎（基于easy-rules）
基于[easy-rules](https://github.com/j-easy/easy-rules)改进开发的一个简单的规则引擎工具，适合判断条件比较多的项目使用，尤其是那种如果满足条件，则会执行一堆业务逻辑或者校验很频繁的业务场景。使用规则引擎可以解耦条件判断执行，让主业务方法看起来更清晰，维护起来也更容易。

## 名词解释
- **最小规则(Rule)**： 最基础的规则，包含条件（condition）和执行（action），可能存在复用
- **规则组(GroupRule)**：最小规则的集合，用于完成一个业务逻辑的校验执行，内置Rules对象，用于最小规则的注册
- **规则引擎(RulesEngine)**: 执行具体规则
- **具体业务逻辑(Service)**：可以执行多个规则组，组成一个完整的业务逻辑处理

## 针对easy-rules的改进点
- 集成spring
- 规则可复用，优先级可修改
- 引入规则组概念
- 增加基类，初步封装
- 同组规则可进行中断或者跳过后续某个规则的执行
- mvel的语法覆盖面还是不够广，因此摒弃，采用java代码实现业务，yml实现规则组灵活配置的形式

## 项目依赖
```xml
<!--nics-easy-rules-->
<dependency>
    <groupId>com.github.selwynshen</groupId>
    <artifactId>nics-easy-rules-spring</artifactId>
    <version>1.1</version>
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
        //return new RulesEngineFactoryBean();
        return new ProtoRulesEngineFactoryBean();
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
最小规则类写法，Rule注解包含了该规则的名称，注释和优先级（可选）
```java
@Rule(name="orgNotExistRuleRule", description = "机构不存在规则", priority = 1020)
@Component
@Scope("prototype")
public class OrgNotExistRule extends AbstractRule {
    //condition注解，表明这是一个条件方法，如果返回true，则执行下面action
    @Condition
    public boolean condition(AdvFacts facts)
    {
        return RandomUtils.nextBoolean();
    }

    //action注解，表明这是一个执行方法，如果上面condition返回true，则执行该方法
    @Action
    public void action(AdvFacts facts)
    {
        System.out.println("机构信息不全，报问题件");
        //结束后续规则的校验
        facts.putEnd(true);
        //跳过某个规则类的执行
        facts.put(Constants.FACTS_SKIP_KEY, SocCatelogNotExistRule.class);
    }
}
```
规则组写法：
```java
@Component
public class EntryInvoiceGroupRules extends AbstractGroupRules {
    @Override
    public void registerRules(Rules rules) {
        //使用了yml配置，不需要自己注册最小规则了，此方法体为空就行
    }
}
```
然后在resources/rules下面创建规则组配置文件，注意name就是bean的名称。通过配置文件的形式，可以动态进行规则的配置，无需修改代码。
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
facts.put("param", "case");
this.rulesEngine.fire(entryInvoiceGroupRules.getRules(), facts);
```
## 案例
[源码](https://github.com/selwynshen/nics-easy-rules-showcase)

## 更新日志
01/29/2019 (v1.1)
1. 修复spring boot项目jar包执行时无法读取规则文件的bug
2. 新增跳过指定规则的listener
3. 规则引擎factory bean改成多例的
