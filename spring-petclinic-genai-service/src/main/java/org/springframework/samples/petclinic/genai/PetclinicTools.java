package org.springframework.samples.petclinic.genai;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.samples.petclinic.genai.dto.OwnerDetails;
import org.springframework.samples.petclinic.genai.dto.PetDetails;
import org.springframework.samples.petclinic.genai.dto.PetRequest;
import org.springframework.samples.petclinic.genai.dto.Vet;
import org.springframework.stereotype.Component;

@Component
class PetclinicTools {

    private static final Logger LOG = LoggerFactory.getLogger(PetclinicTools.class);

    private final AIDataProvider petclinicAiProvider;

    PetclinicTools(AIDataProvider petclinicAiProvider) {
        this.petclinicAiProvider = petclinicAiProvider;
    }

	public List<OwnerDetails> listOwners() {
        LOG.info("listOwners()");
		return petclinicAiProvider.getAllOwners();
	}

	public OwnerDetails addOwnerToPetclinic(OwnerRequest ownerRequest) {
        LOG.info("addOwnerToPetclinic() ownerRequest={}", ownerRequest);
		return petclinicAiProvider.addOwnerToPetclinic(ownerRequest);
	}

	public List<String> listVets(Vet vetRequest) {
        LOG.info("listVets() vetRequest={}", vetRequest);
        try {
            return petclinicAiProvider.getVets(vetRequest);
        } catch (JsonProcessingException e) {
            LOG.error("Error processing JSON in the listVets function", e);
            return Collections.emptyList();
        }
	}

	public PetDetails addPetToOwner(int ownerId, PetRequest petRequest) {
        LOG.info("addPetToOwner() ownerId={} petRequest={}", ownerId, petRequest);
		return petclinicAiProvider.addPetToOwner(ownerId, petRequest);
	}
}
