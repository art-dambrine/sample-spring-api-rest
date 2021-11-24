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
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
                .andExpect(content().string(containsString("expirationTime")))
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

    @Test
    @DisplayName("Example POST /partner")
    public void whenPostPartner_thenSuccessful() throws Exception {
        Map<String, Object> partner = new HashMap<>();
        partner.put("name", "UPS");
        partner.put("reference", "FYI25");
        partner.put("locale", "en_BE");
        partner.put("expirationTime", "2013-10-03T12:18:46+01:00");

        this.mockMvc.perform(post("/partner").contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(partner)))
                .andExpect(status().isCreated())
                .andDo(document("createPartner", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                        requestFields(fieldWithPath("name").description("The name of the partner"),
                                fieldWithPath("reference").description("The unique reference of the partner"),
                                fieldWithPath("locale").description("A valid Locale of the partner"),
                                fieldWithPath("expirationTime").description("The ISO-8601 UTC date time when the partner is going to expire"))));
    }

    @Test
    @DisplayName("Example DELETE /partner")
    public void whenDeletePartner_thenSuccessful() throws Exception {
        this.mockMvc.perform(delete("/partner/{id}", 2))
                .andExpect(status().isOk())
                .andDo(document("deletePartner", pathParameters(parameterWithName("id").description("The id of the partner to delete"))));
    }

    @Test
    @DisplayName("Example PUT /partner")
    public void whenUpdatePartner_thenSuccessful() throws Exception {

        ConstraintDescriptions desc = new ConstraintDescriptions(Partner.class);

        Map<String, Object> partner = new HashMap<>();
        partner.put("name", "DHL");
        partner.put("reference", "FYI255");
        partner.put("locale", "de_DE");
        partner.put("expirationTime", "2022-05-23T12:18:46+01:00");

        this.mockMvc.perform(put("/partner/{id}", 3).contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(partner)))
                .andExpect(status().isOk())
                .andDo(document("updatePartner", pathParameters(parameterWithName("id").description("The id of the partner to update")),
                        responseFields(fieldWithPath("id").description("The id of the updated partner" + collectionToDelimitedString(desc.descriptionsForProperty("id"), ". ")),
                                fieldWithPath("name").description("The name of the partner"),
                                fieldWithPath("reference").description("The unique reference of the partner"),
                                fieldWithPath("locale").description("A valid Locale of the partner"),
                                fieldWithPath("expirationTime").description("The ISO-8601 UTC date time when the partner is going to expire"))));
    }
}
