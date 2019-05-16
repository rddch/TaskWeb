package com.derkach.votingApp.repository;

import com.derkach.votingApp.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRepository extends JpaRepository<Voting, Long> {

}
