package com.hyf.demo.streamTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    // 姓名
    private String name;
    // 年龄
    private int age;
    //性别
    private String sex;
    // 薪资
    private int salary;
}
