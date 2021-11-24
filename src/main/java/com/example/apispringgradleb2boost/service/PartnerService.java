package com.example.apispringgradleb2boost.service;

import com.example.apispringgradleb2boost.exceptionhandling.CustomError;
import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.repository.PartnerRepository;
import com.google.gson.Gson;
import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Locale;
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
        if (localeIsValid(partner.getLocale()) && utcDateTimeValidation(partner.getExpirationTime())) {
            return partnerRepository.save(partner);
        } else {
            return null;
        }
    }

    public Partner updatePartner(Partner partner, Optional<Partner> p) {
        Partner currentPartner = p.get();

        String name = partner.getName();
        if (name != null) {
            currentPartner.setName(name);
        }
        String reference = partner.getReference();
        if (reference != null) {
            currentPartner.setReference(reference);
        }
        Locale locale = partner.getLocale();
        if (localeIsValid(locale)) {
            currentPartner.setLocale(locale);
        }
        String expirationTime = partner.getExpirationTime();
        if (utcDateTimeValidation(expirationTime)) {
            currentPartner.setExpirationTime(expirationTime);
        }
        savePartner(currentPartner);
        return currentPartner;
    }

    public void deletePartner(@Min(0) final Long Id) {
        partnerRepository.deleteById(Id);
    }

    /**
     * Check whether the locale object is valid locale if not throw custom error
     *
     * @param locale
     * @return Boolean
     */
    public Boolean localeIsValid(Locale locale) {
        if (Arrays.asList(Locale.getAvailableLocales()).contains(locale)) {
            return true;
        } else {
            throw new CustomError(HttpStatus.BAD_REQUEST.value(), new Gson().toJson(
                    new CustomError(HttpStatus.BAD_REQUEST.value(),
                            String.format("Locale %s is an invalid Locale!", locale.toString()))
            ));
        }
    }

    /**
     * DateTime validation (ISO-8601 UTC date time) before calling database
     *
     * @param utcDateTimeToCheck
     * @return Boolean
     */
    private Boolean utcDateTimeValidation(String utcDateTimeToCheck) {
        try {
            ZonedDateTime.parse(utcDateTimeToCheck);
            return true;
        } catch (Exception e) {
            throw new CustomError(HttpStatus.BAD_REQUEST.value(), new Gson().toJson(
                    new CustomError(HttpStatus.BAD_REQUEST.value(),
                            String.format("Date %s is an invalid ISO-8601 UTC date time! Should be something like %s",
                                    utcDateTimeToCheck, "2017-10-03T12:18:46+00:00"))
            ));
        }
    }
}
