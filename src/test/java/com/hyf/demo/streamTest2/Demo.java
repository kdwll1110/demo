package com.hyf.demo.streamTest2;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
public class Demo {
    List<Person> personList = new ArrayList<>();
    @BeforeEach
    public void init(){
        personList = Arrays.asList(new Person("Tom", 8900, 18,"male", "New York"),
                new Person("Jack", 7000, 20,"male", "Washington"),
                new Person("Lily", 7800, 30,"female", "Washington"),
                new Person("Anni", 8200, 40,"female", "New York"),
                new Person("Owen", 9500, 25,"male", "New York"),
                new Person("Alisa", 7900, 20,"female", "New York"));
    }

    @Test
    public void test1(){
        //筛选出集合中年龄大于等于25的元素，并打印出来
        personList.stream().filter(person->person.getAge()>=25).forEach(System.out::println);
    }

    @Test
    public void test2(){
        //筛选员工中工资高于8000的人，并形成新的集合。 形成新集合依赖collect（收集）
        List<Person> collect = personList.stream().filter(person -> person.getSalary() > 8000).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
    }

    @Test
    public void test3(){
        //获取String集合中最长的元素。
        List<String> stringList = Arrays.asList("a", "ab", "abc", "ac", "bbbc");
        Optional<String> max = stringList.stream().max((s1, s2) -> s1.length() - s2.length());
        System.out.println("max.get() = " + max.get());
        System.out.println("----------------------------分割线--------------------------");
        Optional<String> max1 = stringList.stream().max(Comparator.comparing(String::length));
        System.out.println("max1.get() = " + max1.get());
    }


    @Test
    public void test4(){
        List<Integer> list = Arrays.asList(7, 6, 9, 4, 11, 6);
        // 自然排序
        Optional<Integer> max = list.stream().max(Integer::compareTo);
        // 自定义排序（从大到小排序）
        Optional<Integer> max2 = list.stream().max((o1, o2) -> o2 - o1);
        System.out.println("自然排序的最大值：" + max.get());
        System.out.println("自定义排序的最大值：" + max2.get());
    }

    @Test
    public void test5(){
        //获取员工薪资最高的人。
        Optional<Person> max = personList.stream().max(Comparator.comparing(Person::getSalary));
        System.out.println("max.get() = " + max.get());

        //计算集合中大于6000的人的个数。
        long count = personList.stream().filter(person -> person.getSalary() > 8000).count();
        System.out.println("count = " + count);

    }

    @Test
    public void test6(){
        //英文字符串数组的元素全部改为大写。
        String[] strArr = { "abcd", "bcdd", "defde", "fTr" };
        List<String> collect = Arrays.asList(strArr).stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("collect = " + collect);
        //整数数组每个元素+3。
        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> collect1 = intList.stream().map(integer -> integer + 3).collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);
    }

    @Test
    public void test7(){
        //将员工的薪资全部增加1000。
        List<Integer> collect = personList.stream().map(person -> person.getSalary() + 1000).collect(Collectors.toList());
        collect.stream().forEach(System.out::println);

        //将两个字符数组合并成一个新的字符数组。
        List<String> list = Arrays.asList("m,k,l,a", "1,3,5,7");
        List<String> collect1 = list.stream().flatMap(s -> {
            String[] split = s.split(",");
            Stream<String> stream = Arrays.asList(split).stream();
            return stream;
        }).collect(Collectors.toList());
        System.out.println("collect1 = " + collect1);

        System.out.println("----------------------------分割线--------------------------");

        String[] s1 = {"m,k,l,a"};
        String[] s2 = {"1,3,5,7"};
        Stream<String> concat = Stream.concat(Arrays.asList(s1).stream(), Arrays.asList(s2).stream());
        System.out.println("concat.count() = " + concat.count());//这个只能是合并到一个集合中，不能将每个元素最终合并成一个集合


    }



    @Test
    public void test8(){
        // 输出字符串集合中每个字符串的长度
        List<String> stringList = Arrays.asList("mu", "CSDN", "hello",
                "world", "quickly");
        stringList.stream().mapToInt(String::length).forEach(System.out::println);
        // 将int集合的每个元素增加1000
        List<Integer> integerList = Arrays.asList(4, 5, 2, 1, 6, 3);
        integerList.stream().mapToInt(x -> x + 1000).forEach(System.out::println);

    }



    @Test
    public void test9(){
        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);

        // 求和方式1
        Optional<Integer> reduce = list.stream().reduce((a, b) -> a + b);
        System.out.println("reduce = " + reduce.get());

        // 求和方式2
        Optional<Integer> reduce1 = list.stream().reduce(Integer::sum);
        System.out.println("reduce1.get() = " + reduce1.get());

        // 求和方式3
        Integer reduce2 = list.stream().reduce(0, Integer::sum);
        System.out.println("reduce2 = " + reduce2);


        // 求乘积
        Optional<Integer> reduce3 = list.stream().reduce((a, b) -> a * b);
        System.out.println("reduce3.get() = " + reduce3.get());

        // 求最大值方式1
        Optional<Integer> reduce4 = list.stream().reduce(Integer::max);
        System.out.println("reduce4 = " + reduce4.get());

        // 求最大值写法2
        Optional<Integer> reduce5 = list.stream().reduce((a,b)-> a<b?a:b);
        System.out.println("reduce5.get() = " + reduce5.get());

    }


    @Test
    public void test10(){
        //求所有员工的工资之和和最高工资。
        Integer reduce = personList.stream().reduce(0, (sum, p) -> sum += p.getSalary(), Integer::sum);
        System.out.println("reduce = " + reduce);

        //方法2
        Optional<Integer> reduce1 = personList.stream().map(Person::getSalary).reduce(Integer::sum);
        System.out.println("reduce1.get() = " + reduce1.get());

        // 求最高工资方式1：
        Integer maxSalary = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                Integer::max);
        // 求最高工资方式2：
        Integer maxSalary2 = personList.stream().reduce(0, (max, p) -> max > p.getSalary() ? max : p.getSalary(),
                (max1, max2) -> max1 > max2 ? max1 : max2);
        // 求最高工资方式3：
        Integer maxSalary3 = personList.stream().map(Person::getSalary).reduce(Integer::max).get();

        System.out.println("maxSalary3 = " + maxSalary3);
    }


    /**
     * 收集 collect
     */
    @Test
    public void test11(){

        List<Integer> list = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        //过滤大于2的数据，收集成一个新的List集合
        List<Integer> collect = list.stream().filter(num -> num > 2).collect(Collectors.toList());
        System.out.println("collect = " + collect);

        Set<Integer> collect1 = list.stream().filter(n -> n % 2 == 0).collect(Collectors.toSet());
        System.out.println("collect1 = " + collect1);

        Map<String, Person> personMap = personList.stream().
                filter(person -> person.getSalary() > 7500).
                collect(Collectors.toMap(Person::getName, person -> person));

        System.out.println("stringMap = " + personMap);

    }


    /**
     * 聚合操作——统计
     */
    @Test
    public void test12(){
        // 将员工按薪资是否高于8000分组（partitioningBy分成两个map）
        Map<Boolean, List<Person>> collect = personList.stream().collect(Collectors.partitioningBy(person -> person.getSalary() > 8000));
        System.out.println("collect = " + collect);

        // 将员工按性别分组（groupingBy分成多个map）
        Map<String, List<Person>> collect1 = personList.stream().collect(Collectors.groupingBy(Person::getSex));
        System.out.println("collect1 = " + collect1);

        // 将员工先按性别分组，再按地区分组(分组中再分组，套娃再套娃)
        Map<String, Map<String, List<Person>>> stringMapMap = personList.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        System.out.println("stringMapMap = " + stringMapMap);
    }


    /**
     * 排序sorted
     */
    @Test
    public void test13(){
        //按工资倒序排序
        personList.stream().sorted(Comparator.comparing(Person::getAge).reversed()).forEach(System.out::println);

        System.out.println("-----------------------------------------------");
        //先按工资再按年龄升序排序
        personList.stream()
                .sorted(Comparator
                        .comparing(Person::getSalary)
                        .thenComparing(Person::getAge))
                .forEach(System.out::println);


        //合并 : 将两个数组对象转换成stream流类型，然后通过Stream的concat方法，拼接在一起，再用distinct()方法去重，最后手机为一个list集合
        String[] arr1 = {"a","b","c","d","f"};
        String[] arr2 = {"c","b","a","d","e","g"};
        Stream<String> stringStream1 = Stream.of(arr1);
        Stream<String> stringStream2 = Stream.of(arr2);
        List<String> collect = Stream.concat(stringStream1, stringStream2).distinct().collect(Collectors.toList());
        System.out.println("collect = " + collect);

    }



    @Test
    public void test14(){
        List<List<String>> grades = new ArrayList<>();

        // 添加第一个年级的学生
        List<String> grade1 = new ArrayList<>();
        grade1.add("令狐冲");
        grade1.add("岳灵珊");
        grade1.add("林平之");

        // 添加第二个年级的学生
        List<String> grade2 = new ArrayList<>();
        grade2.add("张无忌");
        grade2.add("赵敏");
        grade2.add("小龙女");

        // 添加第三个年级的学生
        List<String> grade3 = new ArrayList<>();
        grade3.add("黄蓉");
        grade3.add("郭靖");
        grade3.add("杨过");

        // 将年级添加到年级列表中
        grades.add(grade1);
        grades.add(grade2);
        grades.add(grade3);

        // 打印生成的年级数据结构
        grades.stream().flatMap(gs->gs.stream().map(g->g+"嘿嘿")).forEach(System.out::println);

    }


    @Test
    public void t15(){
        List<List<List<String>>> arr1 = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            List<List<String>> arr2 = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                List<String> arr3 = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    arr3.add("路人"+i+j+(k+1));
                }
                arr2.add(arr3);
            }
            arr1.add(arr2);
        }
        //System.out.println("arr1 = " + arr1);

        arr1.stream().flatMap(a->a.stream()).flatMap(a1->a1.stream()).forEach(System.out::println);






    }

    @Test
    public void t16(){
//        Object o = Optional.ofNullable(66).orElse(2);
//        Object o1 = Optional.ofNullable(null).orElseThrow(RuntimeException::new);
        Integer a = 1;
        Optional.ofNullable(a).ifPresent(System.out::println);
//        System.out.println("o = " + o);
//        System.out.println("o1 = " + o1);
    }


    @Test
    public void test17(){
        //创建一个容量为100的线程池对象
        ExecutorService executorService = Executors.newFixedThreadPool(100000);

        //

        for (int i = 1; i <= 100000; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("当前线程："+Thread.currentThread().getName());
                }
            });
        }
        executorService.shutdown();

    }

    


}
