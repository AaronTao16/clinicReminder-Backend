package com.pitt.backend.service;

import com.pitt.backend.entity.ClinicalOrder;
import com.pitt.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @Override
    public ClinicalOrder save(ClinicalOrder clinicalOrder) {
        return this.orderRepository.save(clinicalOrder);
    }

//    @Override
//    public ClinicalOrder update(ClinicalOrder clinicalOrder) {
//        return this.orderRepository.update(clinicalOrder);
//    }
}
