package com.example.java8to11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class GreetingApp {

	public static void main(String[] args) {
		// 스태틱 메소드 참조
		UnaryOperator<String> hi = Greeting::hi;

		Greeting greeting = new Greeting();
		// 인스턴스 메소드 참조
		UnaryOperator<String> hello = greeting::hello;

		// 기본 생성자 참조
		Supplier<Greeting> newGreeting = Greeting::new;
		Greeting getGreeting = newGreeting.get();

		// 매개변수가 존재하는 생성자 참조
		Function<String, Greeting> newStringGreeting = Greeting::new;
		Greeting getStringGreeting = newStringGreeting.apply("name");

		// 임의 객체의 인스턴스 메소드 참조
		String[] names = {"yoonmin", "coding", "program"};
		Arrays.sort(names, String::compareToIgnoreCase);
		System.out.println(Arrays.toString(names));
	}
}
