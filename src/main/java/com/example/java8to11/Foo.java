package com.example.java8to11;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class Foo {

	public static void main(String[] args) {
		// Foo.runSomeThing();
		Foo.runFunction();
	}

	public static void runFunction() {
		// Plus10 plus10_class = new Plus10();
		// System.out.println("plus10.apply(1) = " + plus10_class.apply(1));

		Function<Integer, Integer> plus10 = i -> i + 10;
		Function<Integer, Integer> multiply2 = i -> i * 2;
		Function<Integer, Integer> multiply2AndPlus10 = plus10.compose(multiply2);
		Function<Integer, Integer> plus10AndMultiply2 = plus10.andThen(multiply2);

		// System.out.println("plus10_function.apply(1) = " + plus10.apply(1));
		// System.out.println("multiply2.apply(2) = " + multiply2.apply(2));
		// System.out.println("multiply2AndPlus10.apply(2) = " + multiply2AndPlus10.apply(2));
		// System.out.println("plus10AndMultiply2.apply(2) = " + plus10AndMultiply2.apply(2));

		// Function 에서 입력타입과 반환타입이 같을 경우 축약형태로 사용
		UnaryOperator<Integer> plus10_unary = i -> i + 10;

		// Function 과 동일하지만 입력값이 2개인점이 차이
		BiFunction<Integer, Integer, Integer> plusAB = (a, b) -> a + b;
		System.out.println("plusAB.apply(10, 20) = " + plusAB.apply(10, 20));


		// BiFunction 의 3개의 타입이 같을경우 축양형태로 사용
		BinaryOperator<Integer> plusAB_binaryOp = (a, b) -> a + b;

		Consumer<Integer> printT = (a) -> System.out.println("a = " + a);
		printT.accept(1);

		// 무조건 어떤 값을 받겠다
		Supplier<Integer> get10 = () -> 10;
		System.out.println("get10.get() = " + get10.get());

		// True|False 를 반환한다
		Predicate<String> startWithYoonMin = (s) -> s.startsWith("YoonMin");
		Predicate<Integer> isEven = (i) -> i % 2 == 0;
		System.out.println("startWithYoonMin.test(\"YoonMin\") = " + startWithYoonMin.test("YoonMin"));
	}

	public static void runSomeThing() {
		RunSomething runSomething = () -> System.out.println("doIt!!");
		runSomething.doIt();
		runSomething.printAge();
		RunSomething.printName();
	}
}
