package be.brickbit.lpm.catering.service.user.dto;

public class UserDto {
    private Long id;
    private String username;
    private String lastName;
    private String firstName;
    private String email;

    public UserDto(Long someId, String someUsername, String someLastName, String someFirstName, String someEmail) {
        id = someId;
        username = someUsername;
        lastName = someLastName;
        firstName = someFirstName;
        email = someEmail;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }
}
