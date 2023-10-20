package com.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.PrescriptionDTO;
import com.model.Prescription;
import com.repository.PrescriptionRepository;
import com.service.PrescriptionService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/pharmacy-online/prescriptions")
public class PrescriptionController {

	@Autowired
	private PrescriptionRepository prescriptionRepository;

	@Autowired
	private PrescriptionService prescriptionService;

	@GetMapping("/list")
	public List<PrescriptionDTO> getAllPresciption() {
		List<Prescription> prescriptions = prescriptionRepository.findAll();
		List<PrescriptionDTO> prescriptionDTOs = prescriptions.stream().map(prescription -> {
			return new PrescriptionDTO(prescription.getId(), prescription.getNote(), prescription.getImageUrls(),
					prescription.getStatus());
		}).collect(Collectors.toList());
		return prescriptionDTOs;
	}

	@GetMapping("/byid")
	public List<PrescriptionDTO> getProductsByCategoryId(@RequestParam("account_id") Long account_id) {
		List<Prescription> prescriptions = prescriptionRepository.findPresciprionByAccountId(account_id);
		List<PrescriptionDTO> prescriptionDTOs = prescriptions.stream().map(prescription -> {
			return new PrescriptionDTO(prescription.getId(), prescription.getNote(), prescription.getImageUrls(),
					prescription.getStatus(), prescription.getAccount().getName(), prescription.getAccount().getId());
		}).collect(Collectors.toList());
		return prescriptionDTOs;
	}

	@PostMapping("/create")
	public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription,
			@RequestParam("account_id") Long accountId) {
		Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);

		PrescriptionDTO prescriptionDTO = new PrescriptionDTO(createdPrescription.getId(),
				createdPrescription.getNote(), createdPrescription.getImageUrls(), createdPrescription.getStatus(),
				createdPrescription.getAccount().getName(), createdPrescription.getAccount().getId());

		return new ResponseEntity<>(prescriptionDTO, HttpStatus.CREATED);
	}
}
