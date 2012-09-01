package org.chessclan.dataTier.jpa;

import java.util.List;
import javax.annotation.Resource;
import org.chessclan.dataTier.DTO.UserDTO;
import org.chessclan.dataTier.exceptions.UserNotFoundException;
import org.chessclan.dataTier.models.User;
import org.chessclan.dataTier.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This implementation of the PersonService interface communicates with
 * the database by using a Spring Data JPA repository.
 * @author Petri Kainulainen
 */
@Service
public class RepositoryUserService implements UserService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryUserService.class);
    
    @Resource
    private UserRepository personRepository;

    @Transactional
    @Override
    public User create(UserDTO created) {
        LOGGER.debug("Creating a new user with information: " + created);
        
        //User person = User.getBuilder(created.getFirstName(), created.getLastName()).build();
        
        return personRepository.save(person);
    }

    @Transactional(rollbackFor = UserNotFoundException.class)
    @Override
    public User delete(Long personId) throws UserNotFoundException {
        LOGGER.debug("Deleting user with id: " + personId);
        
        User deleted = personRepository.findOne(personId);
        
        if (deleted == null) {
            LOGGER.debug("No user found with id: " + personId);
            throw new UserNotFoundException();
        }
        
        personRepository.delete(deleted);
        return deleted;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        LOGGER.debug("Finding all users");
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        LOGGER.debug("Finding user by id: " + id);
        return personRepository.findOne(id);
    }

    @Transactional(rollbackFor = UserNotFoundException.class)
    @Override
    public User update(UserDTO updated) throws UserNotFoundException {
        LOGGER.debug("Updating user with information: " + updated);
        
        User user = personRepository.findOne(updated.getId());
        
        if (user == null) {
            LOGGER.debug("No user found with id: " + updated.getId());
            throw new UserNotFoundException();
        }
        
        //user.update(updated.getFirstName(), updated.getLastName());

        return user;
    }

    /**
     * This setter method should be used only by unit tests.
     * @param userRepository
     */
    public void setUserRepository(UserRepository personRepository) {
        this.personRepository = personRepository;
    }
}