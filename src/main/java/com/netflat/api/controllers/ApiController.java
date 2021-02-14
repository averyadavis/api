package com.netflat.api.controllers;

import java.util.Random;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import com.netflat.api.service.DataService;

@RestController
@RequestMapping("api")
public class ApiController {
	
	 private final Logger log = LoggerFactory.getLogger(this.getClass());
	   	
	@Autowired
	private DataService dataService;
	
	@Autowired
	private ExecutorService sseExecutor;
	
    @GetMapping("/")
    public SseEmitter getData() {
    	
    	log.debug("getData()");
    	
        SseEmitter emitter = new SseEmitter();
        sseExecutor.execute(() -> {
        	Random random = new Random();
        	try {
                for (int i = 0; i < 5; i++) {
                	log.debug("Sending event " + i);
                    SseEventBuilder event = SseEmitter.event()
                      .data(dataService.findDataById(i).getData())
                      .id(String.valueOf(i))
                      .name("status");
                    emitter.send(event);                    
                    Thread.sleep(5000);
                }

                String json = "{\"city\":\"chicago\",\"name\":\"jon doe\",\"age\":\"22\"}";
                SseEventBuilder event = SseEmitter.event()
                        .data(json)
                        .id(String.valueOf(5))
                        .name("complete");

                emitter.send(event);


            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
            finally {
              emitter.complete();    
            }
        });

        return emitter;
    	
    }

}
