打包：
    com.xukea.common.*;
    用eclipse打包导出时，需要选择 Add directory entries，从而保证jar包中的xml配置文件生效

与主框架整合步骤：
    1. 添加工程lib      ：把com.xukea.common-${version}.jar和引用的第三方包复制到你的工程lib目录下，做为你工程的lib；
    2. 配置自定义TAG标签：复制WEB-INF/tld/xukea-common.tld到WEB-INF/tld中；

依赖关系：
    com.xukea.framework.*;
    com.xukea.system.settings.*;
    com.xukea.system.attachment.*;
    com.xukea.system.message.*;
    com.xukea.main.user.*;
