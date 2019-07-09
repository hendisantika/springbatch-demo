package com.hendisantika.springbatchdemo.steps.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-10
 * Time: 05:35
 */
@Component
public class WordProcessor implements ItemProcessor<String, String> {

    @Override
    public String process(String item) throws Exception {
        return item.toLowerCase();
    }

}