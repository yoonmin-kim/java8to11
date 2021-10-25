package com.example.java8to11;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

	@Test
	@DisplayName("Stream API")
	public void steamAPI() {
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mcv", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));

		System.out.println("[spring 으로 시작하는 수업]");
		springClasses.stream()
			.filter(onlineClasses -> onlineClasses.getTitle().startsWith("spring"))
			.forEach(onlineClasses -> System.out.println(onlineClasses.getTitle()));

		System.out.println("[close 되지 않은 수업]");
		springClasses.stream()
			.filter(Predicate.not(OnlineClass::isClosed))
			.forEach(onlineClass -> System.out.println(onlineClass.getTitle()));

		System.out.println("[수업 이름만 모아서 스트림 만들기]");
		springClasses.stream()
			.map(OnlineClass::getTitle)
			.forEach(System.out::println);

		List<OnlineClass> javaClasses = new ArrayList<>();
		javaClasses.add(new OnlineClass(6, "The java Test", true));
		javaClasses.add(new OnlineClass(7, "The java, Code manipulation", true));
		javaClasses.add(new OnlineClass(8, "The java, 8 to 11", false));

		List<List<OnlineClass>> yoonminEvents = new ArrayList<>();
		yoonminEvents.add(springClasses);
		yoonminEvents.add(javaClasses);

		System.out.println("[두 수업 목록에 들어있는 모든 수업 아이디 출력]");
		// yoonminEvents.stream()
		// 	.forEach(ocList -> ocList.stream()
		// 						.map(OnlineClass::getId)
		// 						.forEach(System.out::println)
		// 	);
		yoonminEvents.stream().flatMap(Collection::stream)
			.forEach(oc -> System.out.println(oc.getId()));

		System.out.println("[10부터 1씩 증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만]");
		Stream.iterate(10, i -> i + 1)
			.skip(10)
			.limit(10)
			.forEach(System.out::println);

		System.out.println("[자바 수업 중에 Test가 들어있는 수업이 있는지 확인]");
		System.out.println(javaClasses.stream()
			.anyMatch(oc -> oc.getTitle().contains("Test")));

		System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
		List<String> springTitles = springClasses.stream()
			.filter(oc -> oc.getTitle().contains("spring"))
			.map(OnlineClass::getTitle)
			.collect(Collectors.toList());
		springTitles.forEach(System.out::println);

	}

}
