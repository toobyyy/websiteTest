package com.example.ylswebsite.controller;

import com.example.ylswebsite.dto.BookingDTO;
import com.example.ylswebsite.dto.CustomerDTO;
import com.example.ylswebsite.model.Booking;
import com.example.ylswebsite.model.Customer;
import com.example.ylswebsite.model.persistence.BookingRepository;
import com.example.ylswebsite.model.persistence.CustomerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.print.Book;
import java.net.URI;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping(value = "/bookings")
public class BookingController {
    private final BookingRepository bookingService;
    private final CustomerRepository customerService;
    private final SortedSet<LocalTime> timeslots = new TreeSet<>();

    public BookingController(BookingRepository bookingService, CustomerRepository customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @GetMapping("/getbookings")
    public ResponseEntity<List<BookingDTO>> getBookingList() {
        Iterable<Booking> bookings = bookingService.findAll();
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        bookings.forEach(b -> bookingDTOS.add(convertToDto(b)));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").buildAndExpand("").toUri();
        return ResponseEntity.created(uri).body(bookingDTOS);
    }

    @GetMapping
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
        if (timeslots.isEmpty()) {
            for (int i = 9; i <= 18; i++) {
                timeslots.add(LocalTime.of(i, 0, 0));
            }
        }
        model.addAttribute("booking", new BookingDTO());
        model.addAttribute("timeslots", timeslots);
        model.addAttribute("bookinglist", bookings);
        return "booking-new";
    }

    @PostMapping
    public String addBooking(BookingDTO booking) {
        Iterable<Booking> bookings = bookingService.findAll();
        for (Booking bookingCheck : bookings) {
            if (bookingCheck.getDate().equals(booking.getDate()) && bookingCheck.getTime().equals(booking.getTime())) {
                return "error";
            }
        }

        convertToEntity(booking);
        bookingService.save(convertToEntity(booking));
        return "redirect:/bookings";
    }

    protected BookingDTO convertToDto(Booking entity) {
        BookingDTO dto = new BookingDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getDate(), entity.getTime());
        return dto;
    }

    protected Booking convertToEntity(BookingDTO dto) {
        //29-06-1963
        Booking booking = new Booking(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getDate(), dto.getTime());
        if (!StringUtils.isEmpty(dto.getId())) {
            booking.setId(dto.getId());
        }
        return booking;
    }

    protected CustomerDTO convertToCustomerDto(Customer entity) {
        CustomerDTO dto = new CustomerDTO(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail(), entity.getPhoneNumber());
        return dto;
    }

    protected Customer convertToCustomerEntity(CustomerDTO dto) {
        //29-06-1963
        Customer customer = new Customer(dto.getUsername(), dto.getName(), dto.getEmail(), dto.getPhoneNumber());
        if (!StringUtils.isEmpty(dto.getId())) {
            customer.setId(dto.getId());
        }
        return customer;
    }
}

