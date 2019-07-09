package com.hendisantika.springbatchdemo.steps.writer;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:53
 */
@Component
public class WordWriter implements ItemWriter<String> {

    public void write(List<? extends String> items) throws Exception {
        items.stream().forEach(item -> {
            System.out.println("Writing from ItemWriter" + item);
        });
    }
}