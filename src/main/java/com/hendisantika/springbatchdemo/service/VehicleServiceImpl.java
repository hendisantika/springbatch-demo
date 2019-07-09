package com.hendisantika.springbatchdemo.service;

import com.hendisantika.springbatchdemo.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * Project : springbatch-demo
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 2019-07-09
 * Time: 21:51
 */
@Service
@Transactional
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;


    @Override
    public boolean deleteAll() {
        // TODO Auto-generated method stub
        try {
            this.vehicleRepository.deleteAll();
            return true;

        } catch (Exception e) {
            return false;
        }

    }

}