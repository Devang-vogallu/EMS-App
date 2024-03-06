package com.example.configurations;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//import com.example.model.User;


//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//            .antMatchers("/public/**").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin().permitAll()
//            .and()
//            .logout().permitAll();
//    }
//
////    @Override
////    @Bean
////    public UserDetailsService userDetailsService() {
////        UserDetails user = User.withDefaultPasswordEncoder()
////            .username("user")
////            .password("password")
////            .roles("USER")
////            .build();
////
////        return new InMemoryUserDetailsManager(user);
////    }
////
////    @Bean
////    public PasswordEncoder passwordEncoder() {
////        return NoOpPasswordEncoder.getInstance();
////    }
//}