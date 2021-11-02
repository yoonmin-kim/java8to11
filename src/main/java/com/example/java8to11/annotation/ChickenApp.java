package com.example.java8to11.annotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Chicken("양념")
@Chicken("후라이드")
public class ChickenApp {

	public static void main( String[] args) {
		Chicken[] annotationsByType = ChickenApp.class.getAnnotationsByType(Chicken.class);
		Arrays.stream(annotationsByType).forEach(chicken -> System.out.println(chicken.value()));
		List<String> list = new ArrayList<>();
	}


}
