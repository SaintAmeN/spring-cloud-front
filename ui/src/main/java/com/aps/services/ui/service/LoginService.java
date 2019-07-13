package com.aps.services.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employeeOptional = employeeService.findByUsername(username);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.isAccountActive()) {
                return User
                        .withUsername(employee.getUsername())
                        .password(employee.getPassword())
                        .roles(extractRoles(employee))
                        .disabled(false)
                        .accountExpired(false)
                        .accountLocked(false)
                        .build();
            }
            throw new AccountNotActiveException(username+"'s account is deactivated");
        }
        throw new UsernameNotFoundException("User not found by name: " + username);
    }

    private String[] extractRoles(Employee employee) {
        String[] rolesArray = new String[1];
        List<String> roles = Collections.singletonList(employee.getRole().toString());
        rolesArray = roles.toArray(rolesArray);
        return rolesArray;
    }

    public Employee getLoggedInUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null ||
                !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new UserNotLoggedInException();
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Employee> employeeOptional = employeeService.findByUsername(user.getUsername());
            if (employeeOptional.isPresent()){
                return employeeOptional.get();
            }
        }

        throw new UserNotLoggedInException();
    }

    public boolean isAdmin() {
        return getLoggedInUser().getRole().equals(EmployeeRole.ADMIN);
    }

    public boolean correctLogin(String error) {
        return error==null;
    }
}
