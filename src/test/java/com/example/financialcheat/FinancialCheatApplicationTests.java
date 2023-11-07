package com.example.financialcheat;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlScript;
import org.apache.commons.jexl3.MapContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FinancialCheatApplicationTests {

	@Test
	void contextLoads() {
		String s1="if(#1#a#>#C#2#b#){#1#a#=#V#1#a#+1;}else{#1#a#=#V#1#a#-2;}";
		String s2="if(a>b){a=a+1;}else{a=a-2;}";

//		Long variable;
//		String dataType;
//
//		int flag=0;
//		for(int i=0;i<rule.length();++i){
//			if(rule.charAt(i)=='#'){
//
//				flag+=1;
//			}
//		}
		JexlBuilder jexlBuilder = new JexlBuilder();
		JexlEngine jexlEngine = jexlBuilder.create();
		JexlScript script = jexlEngine.createScript(s2);
		MapContext mapContext = new MapContext();
		mapContext.set("a",2);
		mapContext.set("b",1);
		Object execute = script.execute(mapContext);
		System.out.printf("结果:%s%n", execute);
	}

	@Test
	void Text1(){
		String[] parts ="@1@2@3@".split("@");
		List<Integer> numbers = new ArrayList<>();
		for (String part : parts) {
			if (!part.isEmpty()) {
				int number = Integer.parseInt(part);
				numbers.add(number);
			}
		}
		System.out.println(numbers);
	}

}
