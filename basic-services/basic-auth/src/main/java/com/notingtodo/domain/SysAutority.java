package com.notingtodo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by qilin.liu on 2018/6/20.
 */


/**
 * @EqualsAndHashCode: 有一下几点作用
 *      ① 此注解会自动声称 equals(Object other) 和 hashCode() 方法
 *      ② 它默认使用非静态，非瞬态属性
 *      ③ 可以通过参数 exclude 排除一些属性
 *      ④ 可以通过参数 of 指定仅使用哪些属性
 *      ⑤ 它默认使用该类中定义的属性，且不调用父类的方法
 *      ⑥ 可以通过 callSuper=true 解决上一点问题。让其在生成的方法中调用父类的方法。
 *
 *      另: @Data相当于 @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode 这5个属性的集合、
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class SysAutority extends AbstractAuditingEntity {

    private static final long serialVersionUID = -3195049120450320621L;

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String value;
}
