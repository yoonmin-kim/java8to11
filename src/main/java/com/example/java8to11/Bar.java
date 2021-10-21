package com.example.java8to11;

public interface Bar extends Boo{

	/**
	 * Boo에서 제공하는 printNameUpperCase()를 제공하고 싶지 않을때
	 * 추상메소드로 재정의 한다
	 */
	void printNameUpperCase();
}
