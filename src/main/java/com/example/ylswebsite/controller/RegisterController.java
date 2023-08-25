package com.example.ylswebsite.controller;

import com.example.ylswebsite.dto.BookingDTO;
import com.example.ylswebsite.dto.UserDTO;
import com.example.ylswebsite.model.Booking;
import com.example.ylswebsite.model.User;
import com.example.ylswebsite.model.persistence.BookingRepository;
import com.example.ylswebsite.model.persistence.RoleRepository;
import com.example.ylswebsite.model.persistence.UserRepository;
import com.example.ylswebsite.model.persistence.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegisterController {
    UserRepository userRepository;
    UserService userService;
    RoleRepository roleRepository;
    private final BookingRepository bookingRepository;

    public RegisterController(RoleRepository roleRepository, UserRepository userRepository, UserService userService, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.bookingRepository = bookingRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "register";
    }

    //TODO: Check confirm password javascript
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDto,
                               BindingResult result,
                               Model model,
                               RedirectAttributes redirectAttributes){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        redirectAttributes.addAttribute("succes", "U bent geregistreerd");
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<UserDTO> users = userService.findAllUsers();
        String userEmail = userDetails.getUsername();
        Iterable<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        List<Long> count = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
       // bookings.forEach(b -> count.add(bookingRepository.countByEmail(b.getEmail())));
        bookings.forEach(b -> {
            count.add(bookingRepository.countBookings(b.getEmail()));
        });
        model.addAttribute("count", count);
        model.addAttribute("users", users);
        return "users";
    }

    protected BookingDTO convertToDto(Booking entity) {
        BookingDTO dto = new BookingDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(), entity.getDate(), entity.getTime());
        return dto;
    }

    protected User convertToEntity(UserDTO dto) {

        User user = new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getPassword());
        if (!StringUtils.isEmpty(dto.getId())) {
            user.setId(dto.getId());
        }
        //user.setRoles(roleRepository.findByName("ROLE_USER"));
        return user;
    }

}
