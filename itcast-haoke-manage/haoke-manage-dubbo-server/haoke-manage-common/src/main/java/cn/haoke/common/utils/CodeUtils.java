package cn.haoke.common.utils;

import java.util.Date;
import java.util.Random;

public class CodeUtils {

    public static String generateCode(String codePrefix) {

        String nowTime = DateUtils.toyyyyMMddHHmmss(new Date());
        Random random = new Random(1);
        int r = random.nextInt()*100;

        return codePrefix + nowTime + r;
    }

    public static void main(String[] args) {
        System.out.println(generateCode("cat"));
    }
}
