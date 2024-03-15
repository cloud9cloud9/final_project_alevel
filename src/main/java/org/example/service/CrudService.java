package org.example.service;

import org.example.model.BaseEntity;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface CrudService<E extends BaseEntity> {

    void create(E entity);
    void update(Long id, E entity);
    void delete(Long id);
    E findById(Long id);
    Collection<E> findAll();
}
