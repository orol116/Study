package com.rest.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@RequestMapping("/helloworld")
public class HelloController {

	@GetMapping("/string")
	public String helloString() {
		return "hello world";
	}

	@GetMapping("/json")
	public Hello helloJson() {
		Hello hello = new Hello();
		hello.setMessage("hello world");
		return hello;
	}
	
	@Data
	public static class Hello {
		private String message;
	}
}
