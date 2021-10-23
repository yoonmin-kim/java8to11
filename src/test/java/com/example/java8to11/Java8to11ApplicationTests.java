package com.example.java8to11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class Java8to11ApplicationTests {

	@Test
	@DisplayName("자바 8 API의 기본 메소드와 스태틱 메소드")
	void defaultStaticMethod() {
		List<String> names = new ArrayList<>();
		names.add("yoonmin");
		names.add("kim");
		names.add("first");
		names.add("second");


		// name.forEach(System.out::println);

		// iterator 와 동일하지만 split 기능까지 제공
		Spliterator<String> spliterator = names.spliterator();
		Spliterator<String> stringSpliterator = spliterator.trySplit();
		while (spliterator.tryAdvance(System.out::println));
		System.out.println("===============================");
		while (stringSpliterator.tryAdvance(System.out::println));

		System.out.println("===============================");
		List<String> collect = names.stream().map(s -> s + 1)
			.filter(s -> s.startsWith("yoon"))
			.collect(Collectors.toList());
		collect.forEach(System.out::println);

		System.out.println("===============================");
		// Predicate 에 해당하는 값 제거
		names.removeIf(s -> s.startsWith("yoon"));
		names.forEach(System.out::println);

		System.out.println("===============================");
		Comparator<String> stringComparator = String::compareToIgnoreCase;
		names.sort(stringComparator.reversed()); // 역순으로 정렬되도록 기본메소드 제공
		names.forEach(System.out::println);
	}

	@Test
	@DisplayName("Stream 소개")
	public void streamTest() {
		List<String> names = new ArrayList<>();
		names.add("yoonmin");
		names.add("kim");
		names.add("first");
		names.add("second");

		// 스트림 생성시 map()은 실행되지 않는다
		Stream<String> stringStream = names.stream().map((s) -> {
			System.out.println("s = " + s);
			return s.toUpperCase();
		});

		// 종료오퍼레이션인 collect()를 실해하면 비로소 위에서 선언한 map()이 동작한다
		List<String> collect = stringStream.collect(Collectors.toList());
		for (String s : collect) {
			System.out.println("s = " + s);
		}

		System.out.println("=========================================");

		// 스트림에서 적용한 toUpperCase()가 원본 데이터에는 영향을 미치지 않는다
		for (String name : names) {
			System.out.println("name = " + name);
		}
	}

}
