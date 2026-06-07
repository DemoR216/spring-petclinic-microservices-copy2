package org.springframework.samples.petclinic.genai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/")
public class PetclinicChatClient {

    private static final Logger LOG = LoggerFactory.getLogger(PetclinicChatClient.class);

    private static final String SYSTEM_PROMPT = "You are a friendly AI assistant designed to help with "
        + "the management of a veterinarian pet clinic called Spring Petclinic. "
        + "Your job is to answer questions about and to perform actions on the user's behalf, mainly around "
        + "veterinarians, owners, owners' pets and owners' visits. "
        + "You are required to answer in a professional manner. If you don't know the answer, politely tell the user "
        + "you don't know the answer, then ask the user a followup question to try and clarify the question they are asking. "
        + "If you do know the answer, provide the answer but do not provide any additional followup questions. "
        + "When dealing with vets, if the user is unsure about the returned results, explain that there may be additional data that was not returned. "
        + "Only if the user is asking about the total number of all vets, answer that there are a lot and ask for some additional criteria. "
        + "For owners, pets or visits - provide the correct data.";

    private final ChatModel chatModel;

    public PetclinicChatClient(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chatclient")
    public String exchange(@RequestBody String query) {
        try {
            Prompt prompt = new Prompt(Arrays.asList(
                new SystemMessage(SYSTEM_PROMPT),
                new UserMessage(query)
            ));
            ChatResponse response = this.chatModel.call(prompt);
            return response.getResult().getOutput().getContent();
        } catch (Exception exception) {
            LOG.error("Error processing chat message", exception);
            return "Chat is currently unavailable. Please try again later.";
        }
    }
}
