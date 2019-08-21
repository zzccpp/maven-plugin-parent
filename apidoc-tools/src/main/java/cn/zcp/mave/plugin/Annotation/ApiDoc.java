package cn.zcp.mave.plugin.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 16:22
 * @describe maven-plugin-parent
 *
 * 标识接口描述的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiDoc {

    /**
     * 接口作者
     * @return
     */
    String author() default "";

    /**
     * 接口名称
     * @return
     */
    String name() default "";

    /**
     * 接口描述
     * @return
     */
    String desc() default "";

    /**
     * 接口参数描述
     * @return
     */
    ParamRule[] params();

}
