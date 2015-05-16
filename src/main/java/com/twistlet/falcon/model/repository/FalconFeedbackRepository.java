package com.twistlet.falcon.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twistlet.falcon.model.entity.FalconFeedback;

public interface FalconFeedbackRepository extends JpaRepository<FalconFeedback, Integer> {

}
