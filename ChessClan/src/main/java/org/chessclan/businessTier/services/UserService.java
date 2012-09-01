/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.businessTier.services;

import java.io.Serializable;
import java.util.List;
import org.chessclan.businessTier.exceptions.UserNotFoundException;
import org.chessclan.dataTier.models.User;

/**
 * Declares methods used to obtain and modify person information.
 * @author Petri Kainulainen
 */
public interface UserService {

    /**
     * Creates a new person.
     * @param created   The information of the created person.
     * @return  The created person.
     */
    public User create(User created);

    /**
     * Deletes a person.
     * @param personId  The id of the deleted person.
     * @return  The deleted person.
     * @throws UserNotFoundException  if no person is found with the given id.
     */
    public User delete(Long personId) throws UserNotFoundException;

    /**
     * Finds all users.
     * @return  A list of persons.
     */
    public List<User> findAll();

    /**
     * Finds person by id.
     * @param id    The id of the wanted person.
     * @return  The found person. If no person is found, this method returns null.
     */
    public User findById(Long id);

    /**
     * Updates the information of a person.
     * @param updated   The information of the updated person.
     * @return  The updated person.
     * @throws UserNotFoundException  if no person is found with given id.
     */
    public User update(User updated) throws UserNotFoundException;
}