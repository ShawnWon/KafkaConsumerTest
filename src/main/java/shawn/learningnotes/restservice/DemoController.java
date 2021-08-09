package shawn.learningnotes.restservice;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class DemoController {

	private static final String template = "Hello, %s!";

	@GetMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
               return name;
	}

        @GetMapping("/records")
	public String records(@RequestParam(value = "num", defaultValue = "5") Long num) {
                ArrayList<String> resp = new ArrayList<String>();
                String thisLine = null;
                try {
                  FileReader fr = new FileReader("src/main/resources/temp.txt");
                  BufferedReader br = new BufferedReader(fr);
                  
                                    while ((thisLine = br.readLine()) != null){
                    resp.add(thisLine);
                  }
                    
                } catch (Exception e){
                  System.out.println(e);                   
                }
                
                return resp.stream().limit(num).collect(Collectors.toList()).toString(); 
         }

}
