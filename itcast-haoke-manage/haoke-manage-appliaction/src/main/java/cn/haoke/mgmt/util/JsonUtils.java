package cn.haoke.mgmt.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonUtils {

    public static<T> T readJsonFromClassPath(String path, Type type) throws IOException {
        File file = ResourceUtils.getFile("classpath:" + path);
        InputStream inputStream = new FileInputStream(file);
        if(file.exists()){

            return JSON.parseObject(inputStream, StandardCharsets.UTF_8,type,
                    //自动关闭流
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用BigDecimal
                    Feature.UseBigDecimal);
        }else{
            throw new IOException();
        }

    }
    private static File getResFile(String filename)throws FileNotFoundException{
        File file = ResourceUtils.getFile("classpath:" + filename);
        return file;
    }

    public static void main(String[] args)throws Exception {
        Object o = readJsonFromClassPath("menu.json", Map.class);
        System.out.println(o);
    }
}
