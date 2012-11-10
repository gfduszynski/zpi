/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier.repositoriesImpl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.chessclan.dataTier.models.Club;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.repositories.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Daniel
 */
@Repository("UserRepository")
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserRepository repository;
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public List<User> findUsersByUserClub(Club club) {
        return repository.findUsersByUserClub(club);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    @Override
    public <S extends User> List<S> save(Iterable<S> itrbl) {
        return repository.save(itrbl);
    }

    @Override
    public void flush() {
        repository.flush();
    }

    @Override
    public User saveAndFlush(User t) {
        return repository.saveAndFlush(t);
    }

    @Override
    public void deleteInBatch(Iterable<User> itrbl) {
        repository.deleteInBatch(itrbl);
    }

    @Override
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    @Override
    public Page<User> findAll(Pageable pgbl) {
        return repository.findAll(pgbl);
    }

    @Override
    public <S extends User> S save(S s) {
        return repository.save(s);
    }

    @Override
    public User findOne(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(Integer id) {
        return repository.exists(id);
    }

    @Override
    public Iterable<User> findAll(Iterable<Integer> itrbl) {
        return findAll(itrbl);
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
    public void delete(User t) {
        repository.delete(t);
    }

    @Override
    public void delete(Iterable<? extends User> itrbl) {
        repository.delete(itrbl);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public User findUserByEmailWithClub(String email) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Expression.eq("email", email));
        List<User> result = criteria.list();
        if (result == null || result.isEmpty()) {
            session.close();
            return null;
        } else {
            session.close();
            result.get(0).getUserClub().getId();
            return result.get(0);
        }
    }
}
