package cn.tedu.camper.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailService userDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailService);
    }






    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .authorizeRequests().antMatchers(

                            "/bootstrap3/**",
                    "/js/**",
                    "/browser_components/**",
                    "/css/*",
                    "/images/*",
                    "/login.html",
                    "/register.html",
                    "/register",
                    "/v1/product",
                    "/v1/product/type_1",
                    "/v1/product/type_2",
                    "/v1/product/type_3",
                    "/v1/user/me"
            ).permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin()
                    .loginPage("/login.html")
                    .loginProcessingUrl("/login")
                    .failureUrl("/login.html?error")
                    .defaultSuccessUrl("/index.html")
                    .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/logout.html?logout");
    }
}
