/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositoriesImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 *
 * @author Daniel
 */
public class ClubRepositoryImpl implements ClubRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ClubRepository repository;

    public ClubRepositoryImpl() {
    }

    @Override
    public Club findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Club> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Club> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <S extends Club> List<S> save(Iterable<S> itrbl) {
        return repository.save(itrbl);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public Club saveAndFlush(Club t) {
        return repository.saveAndFlush(t);
    }

    @Override
    public void deleteInBatch(Iterable<Club> itrbl) {
        repository.deleteInBatch(itrbl);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public Page<Club> findAll(Pageable pgbl) {
        return repository.findAll(pgbl);
    }

    @Override
    public <S extends Club> S save(S s) {
        return repository.save(s);
    }

    @Override
    public Club findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.exists(id);
    }

    @Override
    public Iterable<Club> findAll(Iterable<Integer> itrbl) {
        return repository.findAll(itrbl);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public void delete(Club t) {
        repository.delete(t);
    }

    @Override
    public void delete(Iterable<? extends Club> itrbl) {
        repository.delete(itrbl);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<Club> findAllWithMembers() {
        Query query = em.createQuery("SELECT c FROM Club c");
        List<Club> list = query.getResultList();
        for (Club c : list) {
            c.getUserSet().isEmpty();
        }
        return (List<Club>) list;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}
