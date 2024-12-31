package com.felipesantos.forumhub.controller;

import com.felipesantos.forumhub.dto.MessageResponse;
import com.felipesantos.forumhub.dto.ResponseDTO;
import com.felipesantos.forumhub.model.Response;
import com.felipesantos.forumhub.model.Users;
import com.felipesantos.forumhub.repository.UserRepository;
import com.felipesantos.forumhub.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/topics/{topicId}/responses")
public class ResponseController {

    private final ResponseService responseService;
    private final UserRepository userRepository;

    @Autowired
    public ResponseController(ResponseService responseService, UserRepository userRepository) {
        this.responseService = responseService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addResponse(
            @PathVariable Long topicId,
            @RequestBody Map<String, String> data,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String content = data.get("content");
        Users user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Response response = responseService.addResponse(topicId, content, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseService.toResponseDTO(response));
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getResponsesByTopic(@PathVariable Long topicId) {
        List<ResponseDTO> responses = responseService.getResponsesByTopicId(topicId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{responseId}")
    public ResponseEntity<?> deleteResponse(@PathVariable Long responseId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        Users user = getUserFromDetails(userDetails);

        // Remove a resposta
        responseService.deleteResponse(responseId, user);
        return ResponseEntity.ok(new MessageResponse("Resposta removida com sucesso!"));
    }

    private Users getUserFromDetails(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}