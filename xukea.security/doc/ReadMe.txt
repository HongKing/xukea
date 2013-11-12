打包：
    com.xukea.common.*;
    spring/applicationContext-security.xml;
    用eclipse打包导出时，需要选择 Add directory entries，从而保证jar包中的xml配置文件生效

与主框架（main）整合步骤：
    1. 添加工程lib ：把com.xukea.security-${version}.jar和引用的第三方包复制到你的工程lib目录下，做为你工程的lib；
    2. 合并web.xml ：整合web-security.xml到工程的web.xml中；
    3. 复制配置信息：整合config/config-security.properties到config.properties中；
    4. 配置security：复制spring/applicationContext-security.xml到项目的spring配置目录中（如果采用默认配置，则跳过此步）。
    5. 配置自定义TAG标签：复制WEB-INF/tld/xukea-security.tld到WEB-INF/tld中；

依赖关系：
    com.xukea.common.util.cache.Config;
    com.xukea.common.util.WebUtil;
    com.xukea.common.UserBasicInfo;
    com.xukea.main.role.*;
    com.xukea.main.user.*;