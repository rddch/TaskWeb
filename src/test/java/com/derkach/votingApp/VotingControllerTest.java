package com.derkach.votingApp;

import com.derkach.votingApp.controller.VotingController;
import com.derkach.votingApp.model.Vote;
import com.derkach.votingApp.model.Voting;
import com.derkach.votingApp.repository.VotingRepository;
import com.derkach.votingApp.service.VotingService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VotingAppApplication.class)
public class VotingControllerTest {

    private static final String DEFAULT_URL = "/api/voting/";

    private static final String DEFAULT_THEME = "hello world";
    private static final String DEFAULT_OPTION_NAME = "dog";
    private static final Integer DEFAULT_OPTION_AMOUNT = 0;
    private static final Integer CHANGED_OPTION_AMOUNT = 35;
    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean CHANGED_STATUS = true;

    /** MediaType for JSON UTF8 */
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private VotingService votingService;

    @Autowired
    private VotingRepository votingRepository;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restVotingMockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VotingController votingController = new VotingController(votingService);
        this.restVotingMockMvc = MockMvcBuilders.standaloneSetup(votingController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setMessageConverters(jacksonMessageConverter)
                .build();
    }

    @Test
    public void createVoting() throws Exception {
        Voting voting = new Voting(DEFAULT_THEME, DEFAULT_STATUS, null);

        restVotingMockMvc.perform(post(DEFAULT_URL)
        .contentType(APPLICATION_JSON_UTF8)
        .content(json(voting)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].theme").value(DEFAULT_THEME))
                .andExpect(jsonPath("$.[*].status").value(DEFAULT_STATUS));
    }

    @Test
    public void updatingVotingTest() throws Exception {
        Voting voting = votingService.createVoting(new Voting(DEFAULT_THEME, DEFAULT_STATUS, null));

        voting.setStatus(CHANGED_STATUS);
        restVotingMockMvc.perform(put(DEFAULT_URL + voting.getId())
        .contentType(APPLICATION_JSON_UTF8)
        .content(json(voting)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").isNotEmpty())
                .andExpect(jsonPath("$.[*].theme").value(DEFAULT_THEME))
                .andExpect(jsonPath("$.[*].status").value(CHANGED_STATUS));
    }

    @Test
    public void voteRegistrationTest() throws Exception {
        List<Vote> votes = new ArrayList<>();
        votes.add(new Vote(DEFAULT_OPTION_NAME, DEFAULT_OPTION_AMOUNT));
        Voting voting = votingService.createVoting(new Voting(DEFAULT_THEME, DEFAULT_STATUS, votes));

        voting.getOptions().get(0).setAmount(CHANGED_OPTION_AMOUNT);
        restVotingMockMvc.perform(put(DEFAULT_URL + voting.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(json(voting)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.success.options[0].amount").value(CHANGED_OPTION_AMOUNT));
    }

    @SuppressWarnings("unchecked")
    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.jacksonMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
