package com.mmall.util;

import com.mmall.pojo.User;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

 // 序列化对象
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        // 取消摩尔转换 timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        // 忽略 空 Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, true);
        // 所有日期都统一为 yyyy-MM-dd HH:mm:ss"
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 反序列化 的属性设置
        // 忽略json字符串中存在，但 在java对象中不存在对应属性的情况
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    /**
     * 序列化
     * 对象 转换 为 String
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String objToString(T obj) {
        if (obj == null) {
            return null;
        }
        // 如果 是String，直接返回
        if (obj instanceof String) {
            return (String) obj;
        } else {

            try {
                return objectMapper.writeValueAsString(obj);
            } catch (IOException e) {
                log.warn(" 转换 对象 到 字符串 错误", e);
                return null;
            }
        }
    }


    /**
     * 对象 转换 为 格式化好的 json  String
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String objToStringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        // 如果 是String，直接返回
        if (obj instanceof String) {
            return (String) obj;
        } else {
            try {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
            } catch (IOException e) {
                log.warn(" 转换 对象 到 字符串 错误", e);
                return null;
            }
        }
    }

    /**
     * json字符串 转换为 指定的class对象类型
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T stringToObj(String str, Class<T> clazz) {
        if (str.isEmpty() || str == null || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("字符串 转换对象 失败", e);
            return null;
        }
    }

    /** 针对 反序列化  <List<User>>类型
     *  List list = JsonUtil.stringToObj(sList, new TypeReference<List<User>>() {
     });
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T stringToObj(String str, TypeReference<T> typeReference) {
        if (str.isEmpty() || str == null ||  typeReference == null) {
            return null;
        }
        try {
            // 如果是String类型
            if (typeReference.getType().equals(String.class)) {
                return (T)str;
            }else{
                // 否则
                return objectMapper.readValue(str,typeReference);
            }
        } catch (IOException e) {
            log.warn("字符串 转换对象 失败", e);
            return null;
        }
    }

    /**
     * 可以处理 反序列化 复杂类型 Map<User,String> 等等
     *  List<User> list = JsonUtil.stringToObj(sList, List.class, User.class);
     * @param str
     * @param collectionClass  集合 的class
     * @param elementClasses  实体类的class
     * @param <T>
     * @return
     */
    public static <T> T stringToObj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        if (str.isEmpty() || str == null ||  elementClasses == null || elementClasses == null) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        try {
          return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("字符串 转换对象 失败", e);
            return null;
        }
    }


    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setEmail("uangg@111.com");
        User user2 = new User();
        user2.setId(2);
        user2.setEmail("uangg@222.com");


        String s1 = JsonUtil.objToString(user);

        String s2 = JsonUtil.objToStringPretty(user);

        log.info(s1);
        log.info(s2);

        User user1 = JsonUtil.stringToObj(s1, User.class);

        System.out.println("end");




        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        String sList = JsonUtil.objToStringPretty(userList);
        log.info("===========");
        log.info(sList);

        // 反序列化 List<User>

//        List list = JsonUtil.stringToObj(sList, new TypeReference<List<User>>() {
//        });
//        System.out.println("end");

        List<User> list = JsonUtil.stringToObj(sList, List.class, User.class);

    }}
