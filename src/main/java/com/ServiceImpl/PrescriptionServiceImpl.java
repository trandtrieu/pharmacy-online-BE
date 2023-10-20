package com.ServiceImpl;

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
}
