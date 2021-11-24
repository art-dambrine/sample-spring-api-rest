package com.example.apispringgradleb2boost.controller;

import com.example.apispringgradleb2boost.exceptionhandling.CustomError;
import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.service.PartnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    /**
     * Read - Get all partners
     *
     * @return - An Iterable object of Partner
     */
    @GetMapping("/partners")
    public Iterable<Partner> getPartners(@Param("from") final Integer from, @Param("size") final Integer size) {
        if (from != null && size != null) {
            return partnerService.getPartners(from, size);
        }
        return partnerService.getPartners();
    }

    /**
     * Read - Get one partner
     *
     * @param Id The id of the partner
     * @param response
     * @return A Partner object
     */
    @GetMapping("/partner/{id}")
    public Partner getPartnerById(@PathVariable("id") final Long Id, HttpServletResponse response) throws CustomError {

        Optional<Partner> partner = partnerService.getPartnerById(Id);
        if (partner.isPresent()) {
            return partner.get();
        } else {
            // Error handling when !partner.isPresent()
            handlePartnerResourceIsNotPresentReturnNotFound(Id);
            return null;
        }
    }

    /**
     * Create - Add a new partner
     *
     * @param partner An object partner
     * @return The partner object saved
     */
    @PostMapping("/partner")
    public Partner createPartner(@RequestBody Partner partner) {
        return partnerService.savePartner(partner);
    }

    /**
     * Update - Update an existing partner
     *
     * @param Id      - The id of the partner to update
     * @param partner - The partner object updated
     * @param response
     * @return The partner object updated
     */
    @PutMapping("/partner/{id}")
    public Partner updatePartner(@PathVariable("id") final Long Id, @RequestBody Partner partner,
                                 HttpServletResponse response) throws CustomError {

        Optional<Partner> p = partnerService.getPartnerById(Id);
        if (p.isPresent()) {
            return partnerService.updatePartner(partner, p);
        } else {
            handlePartnerResourceIsNotPresentReturnNotFound(Id);
            return null;
        }
    }

    /**
     * Delete - Delete a partner
     *
     * @param response
     * @param Id - The id of the partner to delete
     */
    @DeleteMapping("/partner/{id}")
    public void deletePartner(@PathVariable("id") final Long Id, HttpServletResponse response) throws CustomError {
        Optional<Partner> partner = partnerService.getPartnerById(Id);
        if (partner.isPresent()) {
            partnerService.deletePartner(Id);
        } else {
            handlePartnerResourceIsNotPresentReturnNotFound(Id);
        }

    }

    /**
     * Partner not found handling - Extraction of duplicated code when !partner.isPresent()
     *
     * @param Id
     * @throws CustomError
     */
    private void handlePartnerResourceIsNotPresentReturnNotFound(final Long Id) throws CustomError {
        // Error handling when !partner.isPresent()
        throw new CustomError(HttpStatus.NOT_FOUND.value(), new Gson().toJson(
                new CustomError(HttpStatus.NOT_FOUND.value(),
                        String.format("Partner with id %d not found!", Id))
        ));
    }

}
