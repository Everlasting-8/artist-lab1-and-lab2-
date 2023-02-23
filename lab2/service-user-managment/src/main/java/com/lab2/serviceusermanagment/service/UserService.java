@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
    
    public User createUser(User user) {
        // validate user fields
        validateUserFields(user);
        // check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Username already exists.");
        }
        return userRepository.save(user);
    }
    
    public User updateUser(Long userId, User updatedUser) {
        // validate user fields
        validateUserFields(updatedUser);
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        // check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(updatedUser.getUsername());
        if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
            throw new BadRequestException("Username already exists.");
        }
        user.setUsername(updatedUser.getUsername());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());
        return userRepository.save(user);
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    private void validateUserFields(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new BadRequestException("Username and password are required.");
        }
        // validate other fields as needed
    }
}
