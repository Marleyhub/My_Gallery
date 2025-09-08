package com.gabriel.java_API;

public class Aluno {
	
	private String name;
	private Integer age;
	
	public Aluno(String name, Integer age){
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Integer getAge() {
		return this.age;
	}

}
