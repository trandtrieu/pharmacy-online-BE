package com.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.FeedbackDTO;
import com.model.Feedback;
import com.model.Product;
import com.repository.FeedbackRepository;
import com.repository.ProductRepository;

@Service
public class FeedbackService {
    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    ProductRepository productRepository;




}
