package com.example.apispringgradleb2boost.controller;

import com.example.apispringgradleb2boost.exceptionhandling.CustomError;
import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.service.PartnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RestController
@Validated
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @GetMapping("/partners")
    public Iterable<Partner> getPartners(@Param("from") @Min(0) final Integer from, @Param("size") @Min(1) final Integer size) {
        if (from != null && size != null) {
            return partnerService.getPartners(from, size);
        }
        return partnerService.getPartners();
    }

    @GetMapping("/partner/{id}")
    public Partner getPartnerById(@PathVariable("id") @Min(1) final Long Id, HttpServletResponse response) throws IOException {

        Optional<Partner> partner = partnerService.getPartnerById(Id);
        if (partner.isPresent()) {
            return partner.get();
        } else {
            // Error handling when !partner.isPresent()
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType(String.valueOf(MediaType.APPLICATION_JSON));
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(
                    new CustomError(HttpStatus.NOT_FOUND.value(),
                            String.format("Partner with id %d not found!", Id))
            ));
            out.flush();
            return null;
        }
    }

}
