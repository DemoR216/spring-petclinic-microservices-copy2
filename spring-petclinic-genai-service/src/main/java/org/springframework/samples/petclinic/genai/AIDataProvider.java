package org.springframework.samples.petclinic.genai;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.genai.dto.OwnerDetails;
import org.springframework.samples.petclinic.genai.dto.PetDetails;
import org.springframework.samples.petclinic.genai.dto.PetRequest;
import org.springframework.samples.petclinic.genai.dto.Vet;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIDataProvider {

	private final VectorStore vectorStore;

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

	public AIDataProvider(VectorStore vectorStore, DiscoveryClient discoveryClient) {
        this.restTemplate = new RestTemplate();
        this.vectorStore = vectorStore;
        this.discoveryClient = discoveryClient;
    }

	public List<OwnerDetails> getAllOwners() {
        return restTemplate.exchange(
            getCustomerServiceUri() + "/owners",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<OwnerDetails>>() {}
        ).getBody();
	}

    public List<String> getVets(Vet vetRequest) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String vetAsJson = objectMapper.writeValueAsString(vetRequest);

        int topK = 20;
        if (vetRequest == null) {
            topK = 50;
        }
        SearchRequest sr = SearchRequest.query(vetAsJson).withTopK(topK);

		List<Document> topMatches = this.vectorStore.similaritySearch(sr);
		return topMatches.stream().map(Document::getContent).collect(Collectors.toList());
	}

	public PetDetails addPetToOwner(int ownerId, PetRequest petRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PetRequest> entity = new HttpEntity<>(petRequest, headers);
        return restTemplate.postForObject(
            getCustomerServiceUri() + "/owners/" + ownerId + "/pets",
            entity,
            PetDetails.class
        );
	}

	public OwnerDetails addOwnerToPetclinic(OwnerRequest ownerRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OwnerRequest> entity = new HttpEntity<>(ownerRequest, headers);
        return restTemplate.postForObject(
            getCustomerServiceUri() + "/owners",
            entity,
            OwnerDetails.class
        );
	}

    private URI getCustomerServiceUri() {
        return discoveryClient.getInstances("customers-service").get(0).getUri();
    }
}
