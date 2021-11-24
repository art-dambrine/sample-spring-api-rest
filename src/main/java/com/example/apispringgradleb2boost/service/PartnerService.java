package com.example.apispringgradleb2boost.service;

import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.repository.PartnerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Data
@Service
public class PartnerService {
    // TODO : service layer will be transactional and encapsulate all validation and database interactions

    @Autowired
    private PartnerRepository partnerRepository;

    public Iterable<Partner> getPartners() {
        return partnerRepository.findAll();
    }

    public Iterable<Partner> getPartners(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return partnerRepository.findAll(pageable);
    }

    public Optional<Partner> getPartnerById(Long Id) {
        return partnerRepository.findById(Id);
    }
}
