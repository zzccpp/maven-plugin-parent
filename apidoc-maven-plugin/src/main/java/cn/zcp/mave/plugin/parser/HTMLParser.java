package cn.zcp.mave.plugin.parser;

import java.util.List;

/**
 * @author zhongchunping
 * @version 1.0
 * @Time 2019-08-21 15:33
 * @describe maven-plugin-parent
 *
 * HTML解析器,解析成为文档
 */
public class HTMLParser {

    /**
     * 解析@Controller注解,拿到注解相关参数
     * @param targetFile 最后生产的目标文件
     * @param controllerCls 所有Controller Class对象
     */
    public static void generate(String targetFile, List<Class> controllerCls) {
        System.out.println("--生成目标文档地址--"+targetFile);

    }
}