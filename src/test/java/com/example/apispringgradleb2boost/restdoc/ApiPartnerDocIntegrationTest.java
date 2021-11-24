package com.example.apispringgradleb2boost.restdoc;

import com.example.apispringgradleb2boost.ApiSpringGradleB2boostApplication;
import com.example.apispringgradleb2boost.model.Partner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(classes = ApiSpringGradleB2boostApplication.class)
public class ApiPartnerDocIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DisplayName("Example GET /partners")
    public void whenGetPartners_thenSuccessful() throws Exception {
        this.mockMvc.perform(get("/partners"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("B2boost")))
                .andDo(document("getAllPartners"));
    }

    @Test
    @DisplayName("Example GET /partner/1")
    public void whenGetPartnerById_thenSuccessful() throws Exception {
        ConstraintDescriptions desc = new ConstraintDescriptions(Partner.class);
        this.mockMvc.perform(get("/partner/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("getAPartner", preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("id").description("id of partner to be searched")),
                        responseFields(fieldWithPath("id")
                                        .description("The id of the partner" +
                                                collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
                                fieldWithPath("name").description("The name of the partner"),
                                fieldWithPath("reference").description("The unique reference of the partner"),
                                fieldWithPath("locale").description("A valid Locale of the partner"),
                                fieldWithPath("expirationTime").description("The ISO-8601 UTC date time when the partner is going to expire"))));
    }
}
