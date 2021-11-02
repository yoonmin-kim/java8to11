package com.example.java8to11;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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
	void streamTest() {
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
	void steamAPI() {
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

	@Test
	@DisplayName("Optional API 사용")
	void optionalTest() {
		List<OnlineClass> onlineClass = new ArrayList<>();
		onlineClass.add(new OnlineClass(1, "spring boot", false));
		onlineClass.add(new OnlineClass(5, "rest api development", false));

		Optional<OnlineClass> findClass = onlineClass.stream()
			.filter(oc -> oc.getTitle().startsWith("jpa"))
			.findFirst();

		System.out.println(findClass.isPresent());
		// if로 감쌀필요 없이 ifPresent 를 사용하면 값이 있을때만 실행함
		findClass.ifPresent(oc -> System.out.println(oc.getTitle()));

		// 값을 꺼내야할때(값이 없으면 NoSuchElementException 발생)
		OnlineClass onlineClass1 = findClass.get();
		// if로 감쌀필요 없이 값을 꺼내야할때
		OnlineClass aClass1 = findClass.orElse(getDefaultClass()); // 값이 있든 없든 getDefaultClass() 실행
		OnlineClass aClass2 = findClass.orElseGet(
			Java8to11ApplicationTests::getDefaultClass); // 값이 없을때만 getDefaultClass() 실행
		OnlineClass aClass3 = findClass.orElseThrow(
			() -> new IllegalStateException()); // 값이 없을때 exception 발생 원하는 exception 을 supplier 로 설정할 수 있다

		// Optional 의 map
		Optional<String> optionalTitle1 = findClass.map(OnlineClass::getTitle);
		Optional<Optional<Progress>> progress1 = findClass.map(
			OnlineClass::getProgress); // Optional 을 반환하면 값을 찾기위해 2번 까야됨
		Optional<Progress> progress = findClass.flatMap(OnlineClass::getProgress); // flatMap() 을 사용하면 한방에 가능
	}

	public static OnlineClass getDefaultClass() {
		System.out.println("getDefaultClass start");
		return new OnlineClass(1, "jpa", false);
	}

	@Test
	@DisplayName("Date 와 Time API, (Instant, ZonedDateTime, LocalDateTime)")
	void dateTimeTest() {
		Instant instant = Instant.now();
		System.out.println("instant = " + instant); // 기준시 UTC, GMT

		ZoneId zone = ZoneId.systemDefault(); // 해당 시스템의 위치
		System.out.println("zone = " + zone);
		ZonedDateTime zonedDateTime = instant.atZone(zone); // zone 으로 설정한 위치 기준으로
		System.out.println("zonedDateTime = " + zonedDateTime);

		LocalDateTime now = LocalDateTime.now(); // 해당 시스템의 위치 기준으로
		System.out.println("now = " + now);
		LocalDateTime.of(1994, Month.OCTOBER, 26, 12, 0, 0);

		ZonedDateTime seoulDateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
	}

	@Test
	@DisplayName("Date 와 Time API, (Period, Duration)")
	void periodDurationTest() {
		LocalDate today = LocalDate.now();
		LocalDate myBirthDay = LocalDate.of(1994, Month.OCTOBER, 26);

		// Period 사람친숙 기간
		Period between = Period.between(today, myBirthDay);
		System.out.println("between.getDays() = " + between.getDays());

		Period until = today.until(myBirthDay);
		System.out.println("until.get(ChronoUnit.DAYS) = " + until.get(ChronoUnit.DAYS));

		// Duration 기계친숙 기간
		Instant now = Instant.now();
		Instant plus = now.plus(10, ChronoUnit.SECONDS);
		Duration betweenNowPlus = Duration.between(now, plus);
		System.out.println("betweenNowPlus = " + betweenNowPlus.getSeconds());

		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter MMddyyyy = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		System.out.println(localDateTime.format(MMddyyyy));

		LocalDate parse = LocalDate.parse("10/26/1994", MMddyyyy);
		System.out.println("parse = " + parse);

		// NEW API 와 OLD API 는 서로 왔다갔다 할 수 있음
		Date date = new Date();
		Instant instant = date.toInstant();
		Date newDate = Date.from(instant);

		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		ZonedDateTime zonedDateTime = gregorianCalendar.toInstant().atZone(ZoneId.systemDefault());
		System.out.println("zonedDateTime = " + zonedDateTime);
		GregorianCalendar from = GregorianCalendar.from(zonedDateTime);

		ZoneId zoneId = TimeZone.getTimeZone("PTS").toZoneId();
		TimeZone timeZone = TimeZone.getTimeZone(zoneId);

		// 주의점 NEW API 는 Immutable 하기 때문에 연산 후 값을 꺼내써야한다
		// (예시)
		LocalDateTime exampleNow = LocalDateTime.now();
		System.out.println("exampleNow = " + exampleNow);
		LocalDateTime plusTime = exampleNow.plus(10, ChronoUnit.DAYS);
		System.out.println("exampleNow = " + exampleNow);
		System.out.println("plusTime = " + plusTime);


	}

	@Test
	@DisplayName("Executors")
	void executorsTest() {
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.submit(() -> System.out.println(Thread.currentThread().getName() + "kim"));
		es.submit(() -> System.out.println(Thread.currentThread().getName() + "yoon"));
		es.submit(() -> System.out.println(Thread.currentThread().getName() + "min"));

		ExecutorService es2 = Executors.newFixedThreadPool(2);
		es2.submit(() -> System.out.println(Thread.currentThread().getName() + "kim"));
		es2.submit(() -> System.out.println(Thread.currentThread().getName() + "yoon"));
		es2.submit(() -> System.out.println(Thread.currentThread().getName() + "min"));
	}

	/**
	 * ScheduledExecutorService
	 * 딜레이, 기간 을 줄수있는 Executors
	 */
	public static void main(String[] args) {
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
		// ses.schedule(() -> System.out.println(Thread.currentThread().getName() + "schedule"), 1L, TimeUnit.SECONDS);
		ses.scheduleAtFixedRate(() -> System.out.println(Thread.currentThread().getName() + "schedule"), 1, 2,
			TimeUnit.SECONDS);
		// ses.execute(() -> System.out.println(Thread.currentThread().getName() + "schedule"));
	}

	@Test
	@DisplayName("Callable")
	void callableTest() throws ExecutionException, InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(3);

		Callable<String> callable1 = () -> {
			Thread.sleep(2000L);
			return "kim";
		};
		Callable<String> callable2 = () -> {
			Thread.sleep(3000L);
			return "yoon";
		};
		Callable<String> callable3 = () -> {
			Thread.sleep(1000L);
			return "min";
		};

		List<Future<String>> futures = executorService.invokeAll(Arrays.asList(callable1, callable2, callable3));
		for (Future<String> future : futures) {
			System.out.println(future.get());
		}

		String s = executorService.invokeAny(Arrays.asList(callable1, callable2, callable3));
		System.out.println("s = " + s);

		executorService.shutdown();
	}

	@Test
	@DisplayName("CompletableFuture1")
	void completableFutureTest() throws ExecutionException, InterruptedException {
		// ExecutorService executorService = Executors.newFixedThreadPool(1);
		// Future<String> submit = executorService.submit(() -> "test");
		// submit.get(); // 외부에서 완료처리 불가능

		// 일반 future 와 다르게 콜백을 미리 정의하고 비동기 처리가 가능하다
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		CompletableFuture<String> future = CompletableFuture.supplyAsync(
			() -> {
				System.out.println(Thread.currentThread().getName());
				return "test";
			}, executorService).thenApplyAsync(s -> s.toUpperCase(), executorService);

		// future.complete("test"); // 외부에서 완료처리가 가능
		System.out.println(future.get()); // get()은 값이 넘어올때까지 대기하는 건 똑같다
		// CompletableFuture<Void> runAsync = CompletableFuture.runAsync(
		// 	() -> System.out.println("1 - " + Thread.currentThread().getName()));
		//
		// CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> "2 - " + Thread.currentThread().getName());
		// System.out.println(supplyAsync.get());
	}

	@Test
	@DisplayName("CompletableFuture2")
	void CompletableFutureTest2() throws ExecutionException, InterruptedException {
		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			return "Hello";
		});
		// 두 Future 간에 의존성이 있는 경우, A하고 곧바로 B하고
		CompletableFuture<String> future = hello.thenCompose(this::getWorld);
		System.out.println(future.get());


		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			return "World";
		});
		// 서로 관계가 없는 Future 를 둘다 호출하고 둘다 결과가 도착했을때 뭔가 실행하고 싶은 경우
		CompletableFuture<String> thenCombine = hello.thenCombine(world, (h, w) -> h + " " + w);
		System.out.println(thenCombine.get());

		// Future 가 두개 이상일 때, 모든 서브 task 를 합쳐서 실행하는 방법
		CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(hello, world)
			.thenAccept(System.out::println); // 여러개의  Future 의 반환이 다 다를 수 있기 때문에 결과값 무의미

		List<CompletableFuture<String>> futures = Arrays.asList(hello, world);
		CompletableFuture[] completableFutures = futures.toArray(new CompletableFuture[futures.size()]);

		CompletableFuture<List<String>> results = CompletableFuture.allOf(completableFutures).thenApply(v -> {
			return futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
		});

		results.get().forEach(System.out::println);

		// allOf 와 다르게 아무거나 하나라도 결과가 올 경우
		CompletableFuture<Void> voidCompletableFuture1 = CompletableFuture.anyOf(hello, world)
			.thenAccept(System.out::println);
	}

	@Test
	@DisplayName("CompletableFuture 에러 처리")
	void CompletableFutureException() throws ExecutionException, InterruptedException {

		boolean isException = true;

		CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> {

			if (isException) {
				throw new IllegalArgumentException();
			}

			System.out.println(Thread.currentThread().getName());
			return "Hello";
		}).exceptionally(ex -> { // 에러가 발생할 경우 처리할 경우
			System.out.println(ex);
			return "Error!";
		});

		System.out.println(hello.get());

		CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> {

			if (isException) {
				throw new IllegalArgumentException();
			}

			System.out.println(Thread.currentThread().getName());
			return "World";
		}).handle((h, ex) -> { // 정상적인 값과 에러를 함께 처리할 경우
			if(ex != null){
				System.out.println(ex);
				return "Error!";
			}
			return h;
		});

		System.out.println(world.get());


	}

	private CompletableFuture<String> getWorld(String message) {
		return CompletableFuture.supplyAsync(() -> {
			System.out.println(Thread.currentThread().getName());
			return message + " World";
		});
	}

}
