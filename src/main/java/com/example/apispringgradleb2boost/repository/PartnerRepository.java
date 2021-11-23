package com.example.apispringgradleb2boost.repository;

import com.example.apispringgradleb2boost.model.Partner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {
}
