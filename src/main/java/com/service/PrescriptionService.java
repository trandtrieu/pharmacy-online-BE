package com.service;

import org.springframework.stereotype.Service;

import com.model.Prescription;

@Service
public interface PrescriptionService {

	Prescription createPrescription(Prescription prescription, Long accountId);

	Prescription updatePrescription(Prescription prescription, Long prescriptionId);

	Prescription getPrescriptionById(Long prescriptionId);

	boolean deletePrescription(Long prescriptionId);

}
