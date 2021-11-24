package com.example.apispringgradleb2boost.service;

import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.repository.PartnerRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.Optional;

@Data
@Service
@Validated
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    public Iterable<Partner> getPartners() {
        return partnerRepository.findAll();
    }

    public Iterable<Partner> getPartners(@Min(0) final int from, @Min(1) final int size) {
        Pageable pageable = PageRequest.of(from, size);
        return partnerRepository.findAll(pageable);
    }

    public Optional<Partner> getPartnerById(@Min(1) final Long Id) {
        return partnerRepository.findById(Id);
    }

    public Partner savePartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    public void deletePartner(@Min(0) final Long Id) {
        partnerRepository.deleteById(Id);
    }
}
