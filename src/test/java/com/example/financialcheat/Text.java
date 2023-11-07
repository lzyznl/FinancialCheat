package com.example.financialcheat;

import org.apache.commons.jexl3.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class Text {

    @Test
    void jexlIf() {
        JexlBuilder jexlBuilder = new JexlBuilder();
        JexlEngine jexlEngine = jexlBuilder.create();
        //IF判断表达式
        String expressionStr = "if(1< 2){ return '真';}else{ return '假';}";
        JexlScript expression = jexlEngine.createScript(expressionStr);
        MapContext mapContext = new MapContext();

        Object evaluate = expression.execute(mapContext);
        System.out.println(String.format("结果:%s", evaluate));
        //For 循环
        String expressionFor = "while (i <  100) {  i = i + 1;total = total + i;}";
        JexlScript scriptFor = jexlEngine.createScript(expressionFor);
        MapContext context = new MapContext();
        context.set("len", 100);
        context.set("total", 0);
        context.set("i", 0);
        Object execute = scriptFor.execute(context);
        System.out.println(String.format("结果:%s", execute));

//        else if b<-5 c=7 else c=8
//        if b>1 if b>10 c=5 else c=6
//        else if b>10 *c=3 else c=4 else a>1
//        if b<0 if (b<-5) c=1 else c=5
//        if a<0
//
//
//        int a = 1;
//        int b = 2;
//        int c;
//        if (a < 0) {
//            if (b < 0) {
//                if (b < -5) {
//                    c = 1;
//                } else {
//                    c = 5;
//                }
//            } else {
//                if (b > 10) {
//                    c = 3;
//                } else {
//                    c = 4;
//                }
//            }
//        } else (a > 1) {
//            if (b > 1) {
//                if (b > 10) {
//                    c = 5;
//                } else {
//                    c = 6;
//                }
//            } else {
//
//                if (b < -5) {
//                    c = 7;
//                } else {
//                    c = 8;
//                }
//            }
//        }
    }
}
