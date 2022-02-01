package com.naprednoprogramiranje.aes.service.role;

import com.naprednoprogramiranje.aes.model.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for role manipulation
 *
 * @author Katarina Vucic
 * @version 1.0
 */
@Service
public interface RoleService {

    /**
     * Returns a list of all available roles in the system
     *
     * @return List of all available roles
     */
    List<Role> findAll();

    /**
     * Filters role by provided name
     *
     * @param roleName Role name used for filtering
     *
     * @return Filtered role (if found). If no role has been found, null is returned
     */
    Role findByRoleName(String roleName);

    /**
     * Filters role by provided id
     *
     * @param id Role id used for filtering
     *
     * @return Filtered Optional role (with no value if role is not found)
     */
    Optional<Role> findById(Integer id);

    /**
     * Saves provided role in a database
     *
     * @param role Role to be saved
     */
    void save(Role role);

    /**
     * Checks if role name already exists in database
     *
     * @param allRoles List of roles used for search
     * @param role Role to be searched for
     *
     * @return
     * <ul>
     * 		<li><b>true</b>, if provided role doesn't already exist in database</li>
     * 		<li><b>false</b>, otherwise</li>
     * </ul>
     */
    boolean checkIfRoleNameIsTaken(List<Role> allRoles, Role role);
}
