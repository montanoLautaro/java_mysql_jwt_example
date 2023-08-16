package com.example.crud_mysql_jwt.service;

import com.example.crud_mysql_jwt.dto.EntertainmentDTO;
import com.example.crud_mysql_jwt.dto.EntertainmentResponse;


public interface EntertainmentService {
    EntertainmentDTO createEntertainment(EntertainmentDTO entertainmentDTO, long user_id);
    EntertainmentResponse getAllEntertainments(int pageNumber, int pageSize, String sortBy, String sortDir);

    EntertainmentDTO getEntertainmentByID(long id);

    EntertainmentDTO updateEntertainment(EntertainmentDTO entertainmentDTO, long id);

    void deleteEntertainment(long id);
}
