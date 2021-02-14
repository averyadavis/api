package com.netflat.api;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.netflat.api.model.Data;
import com.netflat.api.service.DataService;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public ExecutorService sseExecutor() {
		return Executors.newCachedThreadPool();
	}
	
	@Bean
	public DataService dataService() {
		return new DataService() {
			private List<Data> _data = new Vector<Data>() {
				private static final long serialVersionUID = 1L;

			{
				add(new Data("data - 0"));
				add(new Data("data - 1"));
				add(new Data("data - 2"));
				add(new Data("data - 3"));
				add(new Data("data - 4"));
				add(new Data("data - 5"));
			}};
			
			@Override
			public Data findDataById(int id) {
				return _data.get(id);
			}
			
		};
	}
	
}
