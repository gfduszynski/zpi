/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.chessclan.dataTier;

import org.chessclan.dataTier.DTO.UserDTO;
import org.chessclan.dataTier.models.User;

/**
 *
 * @author Daniel
 */
/**
 * An utility class which contains useful methods for unit testing person related
 * functions.
 * @author Petri Kainulainen
 */
public class UserTestUtil {

    public static UserDTO createDTO(Long id, String firstName, String lastName) {
        UserDTO dto = new UserDTO();

        dto.setId(id);
        dto.setFirstName(firstName);
        dto.setLastName(lastName);

        return dto;
    }

    public static User createModelObject(Long id, String firstName, String lastName) {
        User model = User.getBuilder(firstName, lastName).build();

        model.setId(id);

        return model;
    }
}