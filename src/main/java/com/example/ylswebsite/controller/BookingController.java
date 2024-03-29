package com.example.ylswebsite.controller;

import com.example.ylswebsite.dto.BookingDTO;
import com.example.ylswebsite.model.Booking;
import com.example.ylswebsite.model.User;
import com.example.ylswebsite.model.persistence.BookingRepository;
import com.example.ylswebsite.model.persistence.EmailService;
import com.example.ylswebsite.model.persistence.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Controller
@RequestMapping(value = "/bookings")
public class BookingController {
    private final BookingRepository bookingService;
    private final UserService userService;
    private final EmailService emailService;
    @Autowired
    public BookingController(BookingRepository bookingService, UserService userService, EmailService emailService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/getbookings")
    public ResponseEntity<List<BookingDTO>> getBookingList() {
        Iterable<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body(bookingDTOS);
    }

    @GetMapping("/showallbookings")
    public String getBookings(Model model) {
        Iterable<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
        model.addAttribute("bookings", bookingDTOS);
        return "bookings";
    }

    @GetMapping("/new")
    public String newEvent(Model model) {
        Iterable<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
        model.addAttribute("booking", new BookingDTO());
        //model.addAttribute("bookinglist", bookings);
        return "booking-new";
    }

    @PostMapping("/save")
    public String addBooking(BookingDTO booking, RedirectAttributes redirAttrs, Model model) {
        Booking entity = convertToEntity(booking);
        String fullname = entity.getFirstName() + " " + entity.getLastName();
        String confirmed = "Beste " + entity.getFirstName()
                + "\nBij deze bevestigen wij uw afspraak op " + entity.getDate() + " om " + entity.getTime()
                + "\nIndien deze afspraak niet kan doorgaan gelieve 24u op voorhand te verwittigen.";
        String body = "Geboekt door: " + fullname
                + " \nEmail: " + entity.getEmail()
                + " \nvoor een afspraak op " + entity.getDate()
                + " om " + entity.getTime()
                + "\nTelefoonnummer: " + entity.getPhoneNumber();
        String customerEmail = "tobyy16@gmail.com";
        String businessEmail = "AmineSaxe@outlook.com";
        //TODO: Change mail user
        //emailService.sendEmail(customerEmail, "Afspraak bevestiging", confirmed);
        //emailService.sendEmail(businessEmail, "Nieuwe afspraak", body);
        convertToEntity(booking);
        bookingService.save(convertToEntity(booking));

        redirAttrs.addFlashAttribute("message", "Uw afspraak is gemaakt. U ontvangt zo dadelijk een mail");

        return ("redirect:/bookings/new?succes");
    }

    @GetMapping("/showallbookings/{id}")
    public String deleteBooking(@PathVariable("id") long id, Model model) {
        bookingService.deleteById(id);
        return "redirect:/bookings/showallbookings";
    }

    @GetMapping("/mybookings")
    public String myBookings(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String userEmail = userDetails.getUsername();
        User user = userService.findUserByEmail(userEmail);
        List<Booking> bookings = bookingService.findByEmail(userEmail);
        model.addAttribute("bookings", bookings);

        return "mybooking";
    }

    @GetMapping("/bookinguser")
    public String bookingUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Iterable<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
        model.addAttribute("booking", new BookingDTO());

        String userEmail = userDetails.getUsername();
        User user = userService.findUserByEmail(userEmail);

        model.addAttribute("user", user);

        return "booking-user";
    }
    protected BookingDTO convertToDto(Booking entity) {
        BookingDTO dto = new BookingDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber(), entity.getDate(), entity.getTime());
        return dto;
    }

    protected Booking convertToEntity(BookingDTO dto) {
        //29-06-1963
        Booking booking = new Booking(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), dto.getDate(), dto.getTime());
        if (!StringUtils.isEmpty(dto.getId())) {
            booking.setId(dto.getId());
        }
        return booking;
    }
}

