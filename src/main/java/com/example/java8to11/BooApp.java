package com.example.java8to11;

public class BooApp {

	public static void main(String[] args) {
		BooDefault booDefault = new BooDefault("yoonmin");
		booDefault.printName();
		booDefault.printNameUpperCase();

		Boo.printBoo();
	}
}
