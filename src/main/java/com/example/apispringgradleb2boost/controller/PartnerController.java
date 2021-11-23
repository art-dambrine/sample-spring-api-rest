package com.example.apispringgradleb2boost.controller;

import com.example.apispringgradleb2boost.model.Partner;
import com.example.apispringgradleb2boost.service.PartnerService;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

@RestController
public class PartnerController {

    @Autowired
    PartnerService partnerService;


    @GetMapping("/partners")
    public Iterable<Partner> getPartners() {
        return partnerService.getPartners();
    }

    @GetMapping("/partner/{id}")
    public Partner getPartnerById(@PathVariable("id") final Long Id, HttpServletResponse response) throws IOException {

        Optional<Partner> partner = partnerService.getPartnerById(Id);
        if (partner.isPresent()) {
            return partner.get();
        } else {
            // Error handling when !partner.isPresent()
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("code", String.valueOf(HttpStatus.NOT_FOUND.value()));
            responseBody.put("message", String.format("Partner with id %d not found!", Id));

            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(responseBody));
            out.flush();

            return null;
        }
    }

}
