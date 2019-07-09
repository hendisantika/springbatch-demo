package com.hendisantika.springbatchdemo.steps.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:57
 */
@Component
public class WordReader implements ItemReader<String> {

    String[] words = {"Laptop", "Pamtop", "Watch"};

    int count = 0;

    @Override
    public String read() throws Exception {

        if (count < words.length) {
            return words[count++];
        } else {
            count = 0;
        }
        return null;

    }

}