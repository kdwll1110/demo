package com.hyf.demo.streamTest2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	private String name;  // 姓名
	private int salary; // 薪资
	private int age; // 年龄
	private String sex; //性别
	private String area;  // 地区



}
