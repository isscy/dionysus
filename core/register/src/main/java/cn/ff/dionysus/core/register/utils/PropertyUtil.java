package cn.ff.dionysus.core.register.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * _
 *
 * @author fengfan 2020/3/20
 */
public class PropertyUtil {

    private static Properties properties = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyUtil.class);

    public PropertyUtil() {
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static List<String> getPropertyList(String key) {
        List<String> valueList = new ArrayList<>();
        for(int i = 0; i < Integer.MAX_VALUE; i++) {
            String value = properties.getProperty(key + "[" + i + "]");
            if (StringUtils.isBlank(value)) {
                break;
            }
            valueList.add(value);
        }
        return valueList;
    }

    static {
        Object inputStream = null;
        try {
            String baseDir = System.getProperty("nacos.home");
            if (!StringUtils.isBlank(baseDir)) {
                inputStream = new FileInputStream(baseDir + "/conf/application.properties");
            } else {
                inputStream = PropertyUtil.class.getResourceAsStream("/application.properties");
            }

            properties.load((InputStream)inputStream);
        } catch (Exception var5) {
            LOGGER.error("read property file error:" + var5);
        } finally {
            IOUtils.closeQuietly((InputStream)inputStream);
        }

    }
}
