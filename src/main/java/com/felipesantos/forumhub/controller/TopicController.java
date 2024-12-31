package com.felipesantos.forumhub.controller;

import com.felipesantos.forumhub.dto.TopicDTO;
import com.felipesantos.forumhub.model.Topic;
import com.felipesantos.forumhub.model.Users;
import com.felipesantos.forumhub.repository.UserRepository;
import com.felipesantos.forumhub.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    @Autowired
    private UserRepository userRepository;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity<TopicDTO> createTopic(@RequestBody Topic topic, @AuthenticationPrincipal UserDetails userDetails) {

        Users users = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        topic.setCreator(users);  // associando o criador ao tópico
        TopicDTO createdTopic = topicService.createTopic(topic.getTitle(), topic.getContent(), users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    @GetMapping
    public List<TopicDTO> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/{id}")
    public TopicDTO getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, @RequestBody Topic updatedTopic,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        Users users = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        TopicDTO topic = topicService.updateTopic(id, updatedTopic.getTitle(), updatedTopic.getContent(), users);
        return ResponseEntity.ok(topic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id,
                                              @AuthenticationPrincipal UserDetails userDetails) {
        Users users = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        topicService.deleteTopic(id, users);
        return ResponseEntity.ok("Topic deleted successfully");
    }
}

