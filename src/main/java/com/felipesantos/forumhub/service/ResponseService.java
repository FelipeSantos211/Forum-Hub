package com.felipesantos.forumhub.service;

import com.felipesantos.forumhub.dto.ResponseDTO;
import com.felipesantos.forumhub.model.Response;
import com.felipesantos.forumhub.model.Topic;
import com.felipesantos.forumhub.model.Users;
import com.felipesantos.forumhub.repository.ResponseRepository;
import com.felipesantos.forumhub.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;

    public ResponseService(ResponseRepository responseRepository, TopicRepository topicRepository) {
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
    }

    public Response addResponse(Long topicId, String content, Users creator) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        Response response = new Response();
        response.setContent(content);
        response.setCreator(creator);
        response.setTopic(topic);
        response.setCreatedAt(LocalDateTime.now());

        return responseRepository.save(response);
    }

    public List<ResponseDTO> getResponsesByTopicId(Long topicId) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        return topic.getResponses().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteResponse(Long responseId, Users users) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Response not found"));

        if (!response.getCreator().getId().equals(users.getId())) {
            throw new RuntimeException("You can only delete your own responses");
        }

        responseRepository.delete(response);
    }

    // Conversão de Response para ResponseDTO
    public ResponseDTO toResponseDTO(Response response) {
        ResponseDTO dto = new ResponseDTO();
        dto.setId(response.getId());
        dto.setContent(response.getContent());
        dto.setCreatorName(response.getCreator().getName());
        dto.setCreatedAt(response.getCreatedAt());
        return dto;
    }
}