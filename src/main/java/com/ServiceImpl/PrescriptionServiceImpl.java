package com.ServiceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.model.Account;
import com.model.Prescription;
import com.repository.AccountRepository;
import com.repository.PrescriptionRepository;
import com.service.PrescriptionService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {
	private final PrescriptionRepository prescriptionRepository;
	private final AccountRepository accountRepository;

	public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository, AccountRepository accountRepository) {
		this.prescriptionRepository = prescriptionRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Prescription createPrescription(Prescription prescription, Long accountId) {
		Account account = accountRepository.findById(accountId)
				.orElseThrow(() -> new EntityNotFoundException("Không tìm thấy tài khoản với ID: " + accountId));
		prescription.setAccount(account);

		return prescriptionRepository.save(prescription);
	}

	@Override
	public Prescription updatePrescription(Prescription prescription, Long prescriptionId) {
		Prescription existingPrescription = prescriptionRepository.findPrescriptionById(prescriptionId);
		if (existingPrescription == null) {
			throw new EntityNotFoundException("Prescription with ID " + prescriptionId + " not found.");
		}

		existingPrescription.setNote(prescription.getNote());
		existingPrescription.setImageUrls(prescription.getImageUrls());
		existingPrescription.setStatus(prescription.getStatus());
		existingPrescription.setName(prescription.getName());
		existingPrescription.setPhone(prescription.getPhone());
		existingPrescription.setEmail(prescription.getEmail());
		existingPrescription.setUpdatedDate(LocalDate.now());
		existingPrescription.setUpdatedTime(LocalTime.now());

		return prescriptionRepository.save(existingPrescription);
	}

	@Override
	public Prescription getPrescriptionById(Long prescriptionId) {
		return prescriptionRepository.findById(prescriptionId).orElse(null);
	}

	@Override
	public boolean deletePrescription(Long prescriptionId) {
		Optional<Prescription> prescriptionOptional = prescriptionRepository.findById(prescriptionId);
		if (prescriptionOptional.isPresent()) {
			prescriptionRepository.delete(prescriptionOptional.get());
			return true;
		}
		return false;
	}
}
