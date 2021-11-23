package com.example.apispringgradleb2boost.repository;

import com.example.apispringgradleb2boost.model.Partner;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {
}
