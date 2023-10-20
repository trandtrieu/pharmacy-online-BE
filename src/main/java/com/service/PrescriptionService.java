package com.service;

import org.springframework.stereotype.Service;

import com.model.Prescription;

@Service
public interface PrescriptionService {

    Prescription createPrescription(Prescription prescription, Long accountId);

}
