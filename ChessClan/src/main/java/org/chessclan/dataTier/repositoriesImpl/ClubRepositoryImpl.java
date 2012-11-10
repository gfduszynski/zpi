/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositoriesImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.repositories.ClubRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository("ClubRepository")
public class ClubRepositoryImpl implements ClubRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private ClubRepository repository;
    @Autowired
    private SessionFactory sessionFactory;

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
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Club.class);
        List<Club> clubs = criteria.list();
        for (Club c : clubs) {
            c.getUserSet().isEmpty();
        }
        session.close();
        return clubs;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
