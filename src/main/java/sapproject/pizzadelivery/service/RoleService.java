package sapproject.pizzadelivery.service;

import sapproject.pizzadelivery.domain.models.service.RoleServiceModel;

import java.util.Set;

public interface RoleService {

    void  seedRolesInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
