与主框架整合步骤：

1. 添加工程lib ：把com.xukea.security-${version}.jar和引用的第三方包复制到你的工程lib目录下，做为你工程的lib；
2. 合并web.xml ：整合web-security.xml到工程的web.xml中；
3. 复制配置信息：整合config/config-security.properties到config.properties中；
4. 合并xukea.tld标签 ：整合xukea-security.tld到工程的xukea.tld中；