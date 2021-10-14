package com.example.java8to11;

@FunctionalInterface
public interface RunSomething {

	void doIt();

	static void printName() {
		System.out.println("YoonMin");
	}

	default void printAge() {
		System.out.println("28");
	}
}
