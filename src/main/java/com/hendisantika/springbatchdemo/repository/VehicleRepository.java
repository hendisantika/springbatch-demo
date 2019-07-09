package com.hendisantika.springbatchdemo.repository;

import com.hendisantika.springbatchdemo.model.Vehicle;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:49
 */

public interface VehicleRepository extends CrudRepository<Vehicle, Long> {
}