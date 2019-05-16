package com.derkach.votingApp.controller;

import com.derkach.votingApp.exception.NotFoundException;
import com.derkach.votingApp.model.ApiResponse;
import com.derkach.votingApp.model.Voting;
import com.derkach.votingApp.service.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/voting")
public class VotingController {

    private VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping("/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ApiResponse<Voting> createVoting(@RequestBody Voting voting) {
        return new ApiResponse<>(votingService.createVoting(voting), null);
    }

    @PutMapping("/{id}")
    public ApiResponse<Voting> updateVoting(@RequestBody Voting voting, @PathVariable Long id) {
        voting.setId(id);
        return new ApiResponse<>(votingService.updateVoting(voting), null);
    }

    @GetMapping("/")
    public ApiResponse<List<Voting>> getVotings() {
        return new ApiResponse<>(votingService.getVotings(), null);
    }

    @GetMapping("/{id}")
    public ApiResponse<Voting> getVoting(@PathVariable Long id) {
        Optional<Voting> voteOptional = votingService.getVotingInfo(id);
        if (voteOptional.isPresent()) {
            return new ApiResponse<>(voteOptional.get(), null);
        } else {
            throw new NotFoundException("Not found com id: " + id.toString());
        }
    }

    @PutMapping("/{id}/vote")
    public ApiResponse<Voting> voteRegistration(@PathVariable Long id, @RequestBody Voting voting) {
        voting.setId(id);
        return new ApiResponse<>(votingService.saveVoting(voting), null);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> prepareValidationErrorMessage(ConstraintViolationException exception) {
        StringBuilder builder = new StringBuilder();
        for (ConstraintViolation violation: exception.getConstraintViolations()) {
            builder.append("Field ").
                    append(violation.getPropertyPath()).
                    append(" ").
                    append(violation.getMessage()).
                    append("; ");
        }
        return new ApiResponse<>(null, new ApiResponse.ApiError(builder.toString()));
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> notFoundVotingExceptionHandler(NotFoundException exception) {
        return new ApiResponse<>(null, new ApiResponse.ApiError(exception.getMessage()));
    }
}
