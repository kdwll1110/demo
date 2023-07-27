package com.hyf.demo.streamTest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@SpringBootTest
public class Demo1 {

    List<User> list = new ArrayList();

    @BeforeEach
    void init() {
        list = Arrays.asList(new User("亚索", 20, "男", 1000),
                new User("阿狸", 18, "女", 2000),
                new User("李青", 21, "男", 5000),
                new User("菲奥娜", 25, "女", 2500),
                new User("乐芙兰", 18, "女", 8000),
                new User("千珏", 30, "男", 10000));
    }

    /**
     * filter条件过滤，仅保留流中满足指定条件的数据，其他不满足的数据都会被删除。
     */
    @Test
    void filter_test() {
        // 查询年纪大于 20 且性别为 男 的对象
        List<User> userList = list.stream().filter(user -> user.getAge() > 20 && "男".equals(user.getSex())).collect(Collectors.toList());
        //输出
        userList.stream().forEach(System.out::println);

    }

    /**
     * distinct
     * 去除集合中重复的元素，该方法没有参数，去重的规则与 HashSet 相同。
     */
    @Test
    void distinct_test() {
        List<String> list = Arrays.asList("a", "b", "a", "d");
        Stream<String> stream = list.stream().distinct();
        stream.forEach(System.out::println);
    }

    @Test
    void sorted_test() {
        // 按薪资排序
        Stream<User> sorted = list.stream().sorted((o1, o2) -> o1.getSalary() - o2.getSalary());
        sorted.forEach(System.out::println);

    }

    @Test
    void limit_skip_test() {
        // 截取 list 中的前 3 个
        list.stream().limit(3).forEach(System.out::println);
        System.out.println("----------------------------分割线--------------------------");

        // 跳过 list 中的前 2 个
        list.stream().skip(2).forEach(System.out::println);
        System.out.println("----------------------------分割线--------------------------");

        // 跳过 list 中的前 2 个，然后截取 2个
        list.stream().skip(2).limit(2).forEach(System.out::println);

    }


    @Test
    void map_test() {
        // 把所有人的年龄改为 18
        list.stream().map(user -> {
            user.setAge(18);
            return user;
        }).forEach(System.out::println);

        System.out.println("----------------------------分割线--------------------------");

        // 返回所有人的名字
        List<String> collect = list.stream().map(User::getName).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);


    }



}
