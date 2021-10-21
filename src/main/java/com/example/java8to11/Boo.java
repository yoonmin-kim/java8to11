package com.example.java8to11;

public interface Boo {

	void printName();

	/**
	 * @implSpec
	 * 이 구현체는 getName()으로 가져온 문자열을 대문자로 바꿔 출력한다.
	 */
	default void printNameUpperCase() {
		System.out.println(getName().toUpperCase());
	}

	static void printBoo() {
		System.out.println("Boo");
	}

	String getName();
}
