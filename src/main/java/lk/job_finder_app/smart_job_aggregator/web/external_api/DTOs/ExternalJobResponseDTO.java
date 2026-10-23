package lk.job_finder_app.smart_job_aggregator.web.external_api.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ExternalJobResponseDTO(
        @JsonProperty("results") List<TheMuseJob> jobs
) {
    //map each records that taken from API
    public record TheMuseJob(
            String name,
            String contents,
            Company company,
            List<Location> locations
    ) {}

    public record Company(String name) {}
    public record Location(String name) {}
}