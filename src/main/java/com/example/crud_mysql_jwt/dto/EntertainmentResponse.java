package com.example.crud_mysql_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntertainmentResponse {
    private List<EntertainmentDTO> content;
    private int pageNumber;
    // cantidad de elementos que devuelve la peticion
    private int pageSize;
    private long totalEntertainments;
    private int totalPages;
    private boolean last;
}
