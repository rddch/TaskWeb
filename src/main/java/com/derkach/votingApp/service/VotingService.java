package com.derkach.votingApp.service;

import com.derkach.votingApp.model.Voting;

import java.util.List;
import java.util.Optional;

/**
 * Created com Erizo on 10.06.2017.
 */

public interface VotingService {


    Voting createVoting(Voting voting);

    List<Voting> getVotings();

    Voting updateVoting(Voting voting);

    Optional<Voting> getVotingInfo(Long id);

    Voting saveVoting(Voting voting);
}
