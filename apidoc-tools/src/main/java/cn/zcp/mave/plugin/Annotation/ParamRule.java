package cn.zcp.mave.plugin.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 16:54
 * @describe maven-plugin-parent
 *
 * 定义接口参数说明
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamRule {

    /**
     * 字段名称
     * @return
     */
    String name() default "";

    /**
     * 字段描述
     * @return
     */
    String desc() default "";


}
