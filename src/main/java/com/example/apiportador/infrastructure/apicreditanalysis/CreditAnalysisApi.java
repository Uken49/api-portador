package com.example.apiportador.infrastructure.apicreditanalysis;

import com.example.apiportador.infrastructure.apicreditanalysis.dto.CreditAnalysisDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "creditAnalisysApi", url = "${url.api-credit-analysis}")
public interface CreditAnalysisApi {

    @GetMapping("/{creditAnalysisId}")
    CreditAnalysisDto getCreditAnalysisById(@PathVariable UUID creditAnalysisId);
}
