package lk.job_finder_app.smart_job_aggregator.usecase.external_api.museAPI.client;

import lk.job_finder_app.smart_job_aggregator.web.external_api.DTOs.ExternalJobResponseDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class TheMuseClient {

    //initiate rest client
    private final RestClient restClient;

    //prepare rest client for get data
    public TheMuseClient() {
        this.restClient = RestClient.create("https://www.themuse.com/api/public");
    }

    //get values from API
    public List<ExternalJobResponseDTO.TheMuseJob>fetchExternalJobs(){
        try{
            //through get req gets API values
            //RETRIEVE - get API res values
            //turn into responseDTO
            ExternalJobResponseDTO responseDTO = restClient.get()
                    .uri("/jobs?page=1")
                    .retrieve()
                    .body(ExternalJobResponseDTO.class);

            if (responseDTO != null && responseDTO.jobs() != null){
                return responseDTO.jobs();
            }

        }catch (Exception e){
            System.out.println("Error getting external jobs: " + e.getMessage());
        }
        return List.of();
    }

}
