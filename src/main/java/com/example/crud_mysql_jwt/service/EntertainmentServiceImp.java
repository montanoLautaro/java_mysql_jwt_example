package com.example.crud_mysql_jwt.service;

import com.example.crud_mysql_jwt.dto.EntertainmentDTO;
import com.example.crud_mysql_jwt.dto.EntertainmentResponse;
import com.example.crud_mysql_jwt.exceptions.ResourceNotFoundException;
import com.example.crud_mysql_jwt.models.Entertainment;
import com.example.crud_mysql_jwt.models.User;
import com.example.crud_mysql_jwt.repositories.EntertainmentRepository;
import com.example.crud_mysql_jwt.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntertainmentServiceImp implements EntertainmentService {
    private final EntertainmentRepository entertainmentRepository;

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public EntertainmentServiceImp(EntertainmentRepository entertainmentRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.entertainmentRepository = entertainmentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EntertainmentDTO createEntertainment(EntertainmentDTO entertainmentDTO, long user_id) {
        EntertainmentDTO result = null;
        if (entertainmentDTO != null) {
            Entertainment entertainment = mapToEntertainment(entertainmentDTO);

            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", user_id));

            entertainment.setUser(user);

            Entertainment newEntertainment = entertainmentRepository.save(entertainment);
            result = mapToEntertainmentDTO(newEntertainment);
        }
        return result;
    }

    @Override
    public EntertainmentResponse getAllEntertainments(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Si el parametro sortDir == asc, se va a ordenar de forma ascendente, si sortDir == desc pasaria lo contrario
        // el parametro sortBy indica por que elemento se va a ordenar, ej: id
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        System.out.println(pageNumber);
        System.out.println(pageSize);
        System.out.println(sortBy);


        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        System.out.println(pageable);

        Page<Entertainment> entertainments = entertainmentRepository.findAll(pageable);
        List<Entertainment> entertainmentList = entertainments.getContent();

        List<EntertainmentDTO> content = entertainmentList.stream()
                .map(this::mapToEntertainmentDTO)
                .collect(Collectors.toList());

        EntertainmentResponse entertainmentResponse = new EntertainmentResponse();
        entertainmentResponse.setContent(content);
        entertainmentResponse.setPageNumber(entertainments.getNumber());
        entertainmentResponse.setPageSize(entertainments.getSize());
        entertainmentResponse.setTotalEntertainments(entertainments.getTotalElements());
        entertainmentResponse.setTotalPages(entertainments.getTotalPages());
        entertainmentResponse.setLast(entertainments.isLast());
        return entertainmentResponse;
    }


    @Override
    public EntertainmentDTO getEntertainmentByID(long id) {
        Entertainment entertainment = entertainmentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretenimiento", "id", id));
        return mapToEntertainmentDTO(entertainment);
    }

    @Override
    public EntertainmentDTO updateEntertainment(EntertainmentDTO entertainmentDTO, long id) {
        Entertainment entertainment = entertainmentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretenimiento", "id", id));

        if (entertainmentDTO.getTitle() != null) {
            entertainment.setTitle(entertainmentDTO.getTitle());
        }
        if (entertainmentDTO.getReview() != null) {
            entertainment.setReview(entertainmentDTO.getReview());
        }
        if (entertainmentDTO.getDuration() >= 0) {
            entertainment.setDuration(entertainmentDTO.getDuration());
        }
        if (entertainmentDTO.getScore() >= 0 && entertainmentDTO.getScore() < 11) {
            entertainment.setScore(entertainmentDTO.getScore());
        }

        Entertainment entertainmentUpdated = entertainmentRepository.save(entertainment);

        return mapToEntertainmentDTO(entertainmentUpdated);
    }

    @Override
    public void deleteEntertainment(long id) {
        Entertainment entertainment = entertainmentRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entretenimiento", "id", id));

        entertainmentRepository.delete(entertainment);
    }

    private EntertainmentDTO mapToEntertainmentDTO(Entertainment entertainment) {
        return modelMapper.map(entertainment, EntertainmentDTO.class);
    }

    private Entertainment mapToEntertainment(EntertainmentDTO entertainmentDTO) {
        return modelMapper.map(entertainmentDTO, Entertainment.class);
    }
}
