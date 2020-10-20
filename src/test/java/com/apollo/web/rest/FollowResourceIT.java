package com.apollo.web.rest;

import com.apollo.SallefyApp;
import com.apollo.domain.User;
import com.apollo.repository.UserRepository;
import com.apollo.service.FollowService;
import com.apollo.service.PlaylistService;
import com.apollo.service.UserDeleteService;
import com.apollo.service.UserService;
import com.apollo.service.dto.UserDTO;
import com.apollo.service.dto.criteria.UserCriteriaDTO;
import com.apollo.service.impl.TrackQueryService;
import com.apollo.service.impl.UserQueryService;
import com.apollo.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import static com.apollo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Pageable.unpaged;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = SallefyApp.class)
public class FollowResourceIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private UserQueryService userQueryService;

    private MockMvc restUserMockMvc;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private TrackQueryService trackQueryService;

    @Autowired
    private UserDeleteService userDeleteService;

    @BeforeEach()
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserResource userResource = new UserResource(userService, userRepository, followService, playlistService, trackQueryService, userQueryService, userDeleteService);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator)
            .build();
    }

    @Test
    @Transactional
    @WithMockUser("not-following-user")
    public void shouldReturnNotFollowingUsers() {

        // Initialize the database
        User user = UserResourceIT.createBasicUserWithUsername("not-following-user");
        userRepository.save(user);

        User follower1 = UserResourceIT.createEntity();
        userRepository.save(follower1);

        final Page<UserDTO> nonFollowingUsers = userQueryService.findByCriteria(new UserCriteriaDTO(null, null, true), unpaged());
        final int sizeBeforeFollowing = nonFollowingUsers.getSize();

        assertThat(sizeBeforeFollowing).isGreaterThan(0);

        followService.toggleFollowUser(follower1.getLogin());

        final Page<UserDTO> notFollowingUsersAfterUpdating = userQueryService.findByCriteria(new UserCriteriaDTO(null, null, true), PageRequest.of(0, 10));

        assertThat(notFollowingUsersAfterUpdating.getSize()).isLessThan(sizeBeforeFollowing);

    }

    @Test
    @Transactional
    @WithMockUser
    public void shouldReturnNotFollowingUsersWithLimit() throws Exception {

        restUserMockMvc.perform(get("/api/users?notFollowing=true&size=5"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(APPLICATION_JSON_UTF8_VALUE));

    }
}
