//package com.funong.funong.conf;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * @Author: cytern
// * @Date: 2020/5/20 17:39
// */
//@EnableWebSecurity
//public class SecurityConf extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests().antMatchers("/").permitAll();
////        .antMatchers("/**").hasRole("root");
//        http.authorizeRequests().antMatchers("/**").hasRole("root")
//                .and().formLogin().loginPage("/page/account_login").permitAll()
//                .and().logout().permitAll();
//
//
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//       auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//               .withUser("cytern").password(new BCryptPasswordEncoder().encode("12345")).roles("root");
//    }
//}
