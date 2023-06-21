package com.example.apiportador.infrastructure.apicreditanalisys;

import com.example.apiportador.infrastructure.apicreditanalisys.dto.CreditAnalisysDto;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "creditAnalisysApi", url = "${url.api-credit-analysis}")
public interface CreditAnalisysApi {

    @GetMapping("/{creditAnalysisId}")
    CreditAnalisysDto getCreditAnalisysById(@PathVariable UUID creditAnalysisId);
}
