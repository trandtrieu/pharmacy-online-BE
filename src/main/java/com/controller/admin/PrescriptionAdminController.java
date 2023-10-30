package com.controller.admin;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dto.PrescriptionDTO;
import com.model.Prescription;
import com.repository.PrescriptionRepository;
import com.service.PrescriptionService;

@CrossOrigin(origins = "http://localhost:3006")
@RestController
@RequestMapping("/pharmacy-online/admin/prescriptions")
public class PrescriptionAdminController {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionService prescriptionService;

    @GetMapping("/list")
    public List<PrescriptionDTO> getAllPrescriptions(@RequestParam(required = false) Integer status) {
        return prescriptionRepository.findAll().stream()
                .filter(p -> status == null || p.getStatus() == status)
                .map(this::createPrescriptionDTO)
                .sorted(Comparator.comparing(PrescriptionDTO::getCreatedDate)
                        .thenComparing(PrescriptionDTO::getCreatedTime))
                .collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<PrescriptionDTO> createPrescription(@RequestBody Prescription prescription,
                                                             @RequestParam("account_id") Long accountId) {
        prescription.setCreatedDate(LocalDate.now());
        prescription.setCreatedTime(LocalTime.now());
        Prescription createdPrescription = prescriptionService.createPrescription(prescription, accountId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPrescriptionDTO(createdPrescription));
    }

    @PutMapping("/update-status/{prescriptionId}")
    public ResponseEntity<PrescriptionDTO> updatePrescriptionStatus(@PathVariable Long prescriptionId,
                                                                   @RequestParam("status") int newStatus) {
        Prescription prescription = prescriptionService.getPrescriptionById(prescriptionId);
        if (prescription == null) {
            return ResponseEntity.notFound().build();
        }
        prescription.setStatus(newStatus);
        Prescription updatedPrescription = prescriptionService.updatePrescription(prescription, prescriptionId);
        return ResponseEntity.ok(createPrescriptionDTO(updatedPrescription));
    }

    @DeleteMapping("/delete/{prescriptionId}")
    public ResponseEntity<String> deletePrescription(@PathVariable Long prescriptionId) {
        if (prescriptionService.deletePrescription(prescriptionId)) {
            return ResponseEntity.ok("Prescription deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    private PrescriptionDTO createPrescriptionDTO(Prescription prescription) {
        PrescriptionDTO prescriptionDTO = new PrescriptionDTO(prescription.getId(), prescription.getNote(),
                prescription.getImageUrls(), prescription.getStatus(), prescription.getAccount().getName(),
                prescription.getAccount().getId(), prescription.getPhone(), prescription.getEmail());
        prescriptionDTO.setCreatedDate(prescription.getCreatedDate());
        prescriptionDTO.setCreatedTime(prescription.getCreatedTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        prescriptionDTO.setUpdatedDate(prescription.getUpdatedDate());
        prescriptionDTO.setUpdatedTime(prescription.getUpdatedTime() != null ?
                prescription.getUpdatedTime().format(DateTimeFormatter.ofPattern("HH:mm")) : null);
        return prescriptionDTO;
    }
}
