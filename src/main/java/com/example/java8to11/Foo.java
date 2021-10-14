package com.example.java8to11;

public class Foo {

	public static void main(String[] args) {
		RunSomething runSomething = () -> System.out.println("doIt!!");
		runSomething.doIt();

	}
}
