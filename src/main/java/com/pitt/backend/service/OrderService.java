package com.pitt.backend.service;

import com.pitt.backend.entity.ClinicalOrder;


public interface OrderService {
    /**
     *
     * @param clinicalOrder
     * @return
     */
//    boolean add(Order order);

    ClinicalOrder save(ClinicalOrder clinicalOrder);

    /**
     *
     * @param patId
     * @return
     */
//    List<Order> getByPatId(Long patId, int status, Date date);
}
