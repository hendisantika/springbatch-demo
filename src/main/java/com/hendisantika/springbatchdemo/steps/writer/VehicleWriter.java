package com.hendisantika.springbatchdemo.steps.writer;

import com.hendisantika.springbatchdemo.model.Vehicle;
import com.hendisantika.springbatchdemo.repository.VehicleRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:54
 */
@Component
public class VehicleWriter implements ItemWriter<Vehicle> {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public void write(List<? extends Vehicle> vechiles) throws Exception {
        this.vehicleRepository.saveAll(vechiles);

    }

}