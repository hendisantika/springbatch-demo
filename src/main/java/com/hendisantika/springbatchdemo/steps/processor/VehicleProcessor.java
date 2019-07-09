package com.hendisantika.springbatchdemo.steps.processor;

import com.hendisantika.springbatchdemo.model.Vehicle;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-10
 * Time: 05:34
 */
@Component
public class VehicleProcessor implements ItemProcessor<Vehicle, Vehicle> {

    private static long id = 0;

    @Override
    public Vehicle process(Vehicle vechile) throws Exception {

        if (Integer.parseInt(vechile.getBuilt()) > 1998) {
            final String model = firstIndexCapital(vechile.getModel()).toString();
            vechile = new Vehicle(++id, vechile.getType(), model, vechile.getBuilt());
        }

        return vechile;
    }

    public StringBuilder firstIndexCapital(String word) {
        StringBuilder sb = new StringBuilder();
        sb.append(word.charAt(0) + "".toUpperCase());
        sb.append(word.subSequence(1, word.length()));
        return sb;
    }
}
