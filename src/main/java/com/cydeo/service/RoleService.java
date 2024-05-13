package com.cydeo.service;

import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<RoleDTO> listAllRoles();
    RoleDTO findById(Long id);
}
