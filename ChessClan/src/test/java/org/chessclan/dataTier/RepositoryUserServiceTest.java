/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier;

import org.chessclan.dataTier.jpa.RepositoryUserService;
import org.chessclan.dataTier.DTO.UserDTO;
import org.chessclan.businessTier.exceptions.UserNotFoundException;
import org.chessclan.dataTier.jpa.RepositoryUserService;
import org.chessclan.dataTier.jpa.UserRepository;
import org.chessclan.dataTier.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author Daniel
 */
public class RepositoryUserServiceTest {

    private static final Long PERSON_ID = Long.valueOf(5);
    private static final String FIRST_NAME = "Foo";
    private static final String FIRST_NAME_UPDATED = "FooUpdated";
    private static final String LAST_NAME = "Bar";
    private static final String LAST_NAME_UPDATED = "BarUpdated";
    
    private RepositoryUserService personService;

    private UserRepository personRepositoryMock;

    @Before
    public void setUp() {
        personService = new RepositoryUserService();

        personRepositoryMock = mock(UserRepository.class);
        personService.setUserRepository(personRepositoryMock);
    }
    
    @Test
    public void create() {
        UserDTO created = UserTestUtil.createDTO(null, FIRST_NAME, LAST_NAME);
        User persisted = UserTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        
        when(personRepositoryMock.save(any(User.class))).thenReturn(persisted);
        
        User returned = personService.create(created);

        ArgumentCaptor<User> personArgument = ArgumentCaptor.forClass(User.class);
        verify(personRepositoryMock, times(1)).save(personArgument.capture());
        verifyNoMoreInteractions(personRepositoryMock);

        assertUser(created, personArgument.getValue());
        assertEquals(persisted, returned);
    }
    
    @Test
    public void delete() throws UserNotFoundException {
        User deleted = UserTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(deleted);
        
        User returned = personService.delete(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verify(personRepositoryMock, times(1)).delete(deleted);
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(deleted, returned);
    }
    
    @Test(expected = UserNotFoundException.class)
    public void deleteWhenUserIsNotFound() throws UserNotFoundException {
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(null);
        
        personService.delete(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(personRepositoryMock);
    }
    
    @Test
    public void findAll() {
        List<User> persons = new ArrayList<User>();
        when(personRepositoryMock.findAll()).thenReturn(persons);
        
        List<User> returned = personService.findAll();
        
        verify(personRepositoryMock, times(1)).findAll();
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(persons, returned);
    }
    
    @Test
    public void findById() {
        User person = UserTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        when(personRepositoryMock.findOne(PERSON_ID)).thenReturn(person);
        
        User returned = personService.findById(PERSON_ID);
        
        verify(personRepositoryMock, times(1)).findOne(PERSON_ID);
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertEquals(person, returned);
    }
    
    @Test
    public void update() throws UserNotFoundException {
        UserDTO updated = UserTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        User person = UserTestUtil.createModelObject(PERSON_ID, FIRST_NAME, LAST_NAME);
        
        when(personRepositoryMock.findOne(updated.getId())).thenReturn(person);
        
        User returned = personService.update(updated);
        
        verify(personRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(personRepositoryMock);
        
        assertUser(updated, returned);
    }
    
    @Test(expected = UserNotFoundException.class)
    public void updateWhenUserIsNotFound() throws UserNotFoundException {
        UserDTO updated = UserTestUtil.createDTO(PERSON_ID, FIRST_NAME_UPDATED, LAST_NAME_UPDATED);
        
        when(personRepositoryMock.findOne(updated.getId())).thenReturn(null);

        personService.update(updated);

        verify(personRepositoryMock, times(1)).findOne(updated.getId());
        verifyNoMoreInteractions(personRepositoryMock);
    }

    private void assertUser(UserDTO expected, User actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), expected.getLastName());
    }
}