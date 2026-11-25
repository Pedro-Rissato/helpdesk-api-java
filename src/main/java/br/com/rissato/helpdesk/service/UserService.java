package br.com.rissato.helpdesk.service;

import java.util.Optional;

import br.com.rissato.helpdesk.enums.UserRole;
import br.com.rissato.helpdesk.exception.BusinessRuleException;
import br.com.rissato.helpdesk.exception.UserNotFoundException;
import br.com.rissato.helpdesk.model.User;
import br.com.rissato.helpdesk.repository.UserRepository;
import br.com.rissato.helpdesk.validation.EmailValidator;

public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User getExistingUser(Long id){
        if(id == null){
            throw new BusinessRuleException("User ID cannot be null");
        }

        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));

    }
    public User getActiveUser(Long id){
        User user = getExistingUser(id);
        if(!user.isActive()){
            throw new BusinessRuleException("User is not active.");
        }
        return user;
    }
    public void validateName(String name){
        if(name == null || name.isBlank()){
            throw new BusinessRuleException("Name cannot be null or empty");
        }
    }
    public void validateEmail(String email){
         if (!EmailValidator.isValid(email)) {
            throw new BusinessRuleException("Invalid email format.");
        
        }

    }
    public void validateUniqueEmail (Long userId, String email){
        Optional<User> userWithEmail = userRepository.findByEmail(email);
        
        if(userWithEmail.isPresent() && !userWithEmail.get().getId().equals(userId)){
            throw new BusinessRuleException("E-mail already in use.");

        }
    }
    public void validateUserCreation(User user){
        if(user == null){
            throw new BusinessRuleException("User cannot be null.");
        }
        validateName(user.getName());
        validateEmail(user.getEmail());
        validateUserCanBeVip(user.isVip(), user.getRole());
        validateUserRole(user.getRole());
    }
    public void validateUserCanBeVip(boolean vip, UserRole userRole){
      if(vip && userRole == UserRole.AGENT){
            throw new BusinessRuleException("Agent cannot be a VIP.");
        }
    }
    public void validateUserRole(UserRole role){
        if(role == null){
            throw new BusinessRuleException("Role cannot be null");
        }
    }
    public void checkUserExists(Long id){
        if(id == null){
            throw new BusinessRuleException("User ID cannot be null");
        }
        if(!userRepository.existsById(id)){
            throw new UserNotFoundException(id);
        }
    }
    public void saveUser(User user){
        validateUserCreation(user);
        validateUniqueEmail(null, user.getEmail());
        userRepository.save(user);
    }
    public void deactivateUser(Long id){
        User user = getActiveUser(id);
        user.setActive(false);
        userRepository.update(user);
    }
    public void activateUser(Long id){
        User user = getExistingUser(id);
        if (user.isActive()){
            throw new BusinessRuleException("Cannot activate a user that is already active.");
        }
        user.setActive(true);
        userRepository.update(user);
    }
    public void deleteUser(Long id){
        getExistingUser(id);
        userRepository.deleteById(id);
    }
    public void adjustName(Long id, String name){
        User user = getExistingUser(id);
        validateName(name);
        user.setName(name);
        userRepository.update(user);
    }
    public void adjustEmail (Long id, String email){
        User user = getExistingUser(id);
        validateEmail(email);
        validateUniqueEmail(id, email);
        user.setEmail(email);
        userRepository.update(user);
    }
    public void adjustVip(Long id, boolean vip){
        User user = getActiveUser(id);
        validateUserCanBeVip(vip, user.getRole());
        user.setVip(vip);
        userRepository.update(user);
    }
    public void adjustRole(Long id, UserRole role){
        User user = getExistingUser(id);
        validateUserRole(role);

        validateUserCanBeVip(user.isVip(), role);
        user.setRole(role);
        userRepository.update(user);
    }
}
