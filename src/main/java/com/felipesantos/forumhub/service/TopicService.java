package com.felipesantos.forumhub.service;

import com.felipesantos.forumhub.dto.TopicDTO;
import com.felipesantos.forumhub.dto.ResponseDTO;
import com.felipesantos.forumhub.model.Topic;
import com.felipesantos.forumhub.model.Users;
import com.felipesantos.forumhub.repository.TopicRepository;
import com.felipesantos.forumhub.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    public TopicDTO createTopic(String title, String content, Users creator) {
        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setContent(content);
        topic.setCreator(creator);
        topic = topicRepository.save(topic);

        // Retorna o DTO do tópico recém-criado
        return toDTO(topic);
    }

    public List<TopicDTO> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TopicDTO> getTopicById(Long id) {
        return topicRepository.findById(id).map(this::toDTO);
    }

    public TopicDTO updateTopic(Long id, String title, String content, Users user) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        if (!topic.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to update this topic");
        }

        topic.setTitle(title);
        topic.setContent(content);
        topic.setUpdatedAt(LocalDateTime.now());
        topic = topicRepository.save(topic);

        // Retorna o DTO atualizado do tópico
        return toDTO(topic);
    }

    public void deleteTopic(Long id, Users user) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));

        if (!topic.getCreator().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this topic");
        }

        topicRepository.delete(topic);
    }

    // Conversão de Topic para TopicDTO
    private TopicDTO toDTO(Topic topic) {
        TopicDTO dto = new TopicDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setContent(topic.getContent());
        dto.setCreatorName(topic.getCreator().getName());
        dto.setCreatedAt(topic.getCreatedAt());
        dto.setUpdatedAt(topic.getUpdatedAt());
        dto.setResponses(topic.getResponses().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    // Conversão de Response para ResponseDTO
    private ResponseDTO toResponseDTO(com.felipesantos.forumhub.model.Response response) {
        ResponseDTO dto = new ResponseDTO();
        dto.setId(response.getId());
        dto.setContent(response.getContent());
        dto.setCreatorName(response.getCreator().getName());
        dto.setCreatedAt(response.getCreatedAt());
        return dto;
    }
}