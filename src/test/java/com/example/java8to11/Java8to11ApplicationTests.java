package com.example.java8to11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class Java8to11ApplicationTests {

	@Test
	@DisplayName("자바 8 API의 기본 메소드와 스태틱 메소드")
	void defaultStaticMethod() {
		List<String> name = new ArrayList<>();
		name.add("yoonmin");
		name.add("kim");
		name.add("first");
		name.add("second");


		// name.forEach(System.out::println);

		// iterator 와 동일하지만 split 기능까지 제공
		Spliterator<String> spliterator = name.spliterator();
		Spliterator<String> stringSpliterator = spliterator.trySplit();
		while (spliterator.tryAdvance(System.out::println));
		System.out.println("===============================");
		while (stringSpliterator.tryAdvance(System.out::println));

		System.out.println("===============================");
		List<String> collect = name.stream().map(s -> s + 1)
			.filter(s -> s.startsWith("yoon"))
			.collect(Collectors.toList());
		collect.forEach(System.out::println);

		System.out.println("===============================");
		// Predicate 에 해당하는 값 제거
		name.removeIf(s -> s.startsWith("yoon"));
		name.forEach(System.out::println);

		System.out.println("===============================");
		Comparator<String> stringComparator = String::compareToIgnoreCase;
		name.sort(stringComparator.reversed()); // 역순으로 정렬되도록 기본메소드 제공
		name.forEach(System.out::println);
	}

}
