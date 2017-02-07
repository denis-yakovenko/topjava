package ru.javawebinar.topjava.web.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.List;

/**
 * User: grigory.kislin
 */
@RestController
@RequestMapping("/ajax/admin/users")
public class AdminAjaxController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email,
                               @RequestParam("password") String password) {
        User user = new User(id, name, email, password, Role.ROLE_USER);
        if (user.isNew()) {
            super.create(user);
        } else {
            super.update(user, id);
        }
    }

    @PostMapping("/{id}/updateField")
    public void updateField(
            @PathVariable("id") int id,
            @RequestParam(value = "enabled", required = false) Boolean enabled,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "caloriesPerDay", required = false) Integer caloriesPerDay
    ){
        User user = super.get(id);
        if (enabled != null) {user.setEnabled(enabled);}
        if (name != null) {user.setName(name);}
        if (email != null) {user.setEmail(email);}
        if (password != null) {user.setPassword(password);}
        if (caloriesPerDay != null) {user.setCaloriesPerDay(caloriesPerDay);}
        super.update(user, id);
    }
}
