package com.example.java8to11;

public class BooDefault implements Boo{

	private String name;

	public BooDefault(String name) {
		this.name = name;
	}

	@Override
	public void printName() {
		System.out.println(name);
	}

	@Override
	public void printNameUpperCase() {
		System.out.println(name.toUpperCase());
	}

	@Override
	public String getName() {
		return name;
	}
}
