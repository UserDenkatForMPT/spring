package com.example.nedemo

import com.example.nedemo.repositories.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.filter.OrderedCharacterEncodingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.stereotype.Service
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Service
class PersonDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {

        if(username == "root")
        return User(
            "root",
            BCryptPasswordEncoder().encode("123"),
            listOf(
                SimpleGrantedAuthority("ADMIN")
            )
        )
        val user = userRepository.findByLogin(username)
        if(user!=null)
        return User(
            user.login,
            BCryptPasswordEncoder().encode(user.password),
            listOf(
                SimpleGrantedAuthority(user.role.nameRole)
            )
        )
        else throw UsernameNotFoundException(username)
    }
}

@Configuration
@EnableWebMvc
class MvcConfig(): WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/index").setViewName("index")
        registry.addViewController("/auth").setViewName("auth")
    }
}

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userDetailsService: PersonDetailsService,
) {
    @Bean
    protected fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }

    @Bean
    fun configure(http: HttpSecurity): DefaultSecurityFilterChain? {
        http.authorizeHttpRequests {
            it.requestMatchers(
                "/",
                "/index",
                "/registration",
                "/auth",
            ).permitAll()
            .requestMatchers(
                "/user/**",
                "/user/chats",
                "/user/chat/**",
                "/user/messages",
                "/user/message/**",
            ).hasAnyAuthority("USER", "ADMIN")
            .requestMatchers(
                "/employee/**",
                "/employee/models",
                "/employee/model/**",
                "/employee/modules",
                "/employee/module/**",
                "/employee/tarifes",
                "/employee/tarif/**",
            ).hasAnyAuthority("EMPLOYEE", "ADMIN")
                .anyRequest().hasAuthority("ADMIN")
        }
        http.csrf {
            it.disable()
        }
        http.formLogin{ it.loginPage("/auth")
            .successHandler(SuccessHandler()) }
        http.logout { lOut ->
            lOut.invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/auth?logout")
                .permitAll()
        }
        return http.build()
    }
}

class SuccessHandler: AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        when(authentication?.authorities?.first()?.authority){
            "ADMIN" -> response?.sendRedirect("/admin/index")
            "EMPLOYEE" -> response?.sendRedirect("/employee/index")
            "USER" -> response?.sendRedirect("/user/index?login=${authentication.name}")
            else -> throw Exception("Invalid role")
        }
    }
}