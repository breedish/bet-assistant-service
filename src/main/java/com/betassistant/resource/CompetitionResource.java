package com.betassistant.resource;

import com.betassistant.domain.Competition;
import com.betassistant.domain.Team;
import com.betassistant.resource.response.MatchesSummaryResult;
import com.betassistant.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * @author zenind
 */
@Component
@Path("competitions/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CompetitionResource {

    private CompetitionService competitionService;

    @Autowired
    public CompetitionResource(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GET
    public Collection<Competition> getCompetitions() {
        return competitionService.getCompetitions();
    }

    @GET
    @Path("/teams/{competition}")
    public Collection<Team> getTeams(@PathParam("competition") @NotNull Competition competition) {
        return competitionService.getTeams(competition);
    }

    @GET
    @Path("/matches/summary/{competition}")
    public MatchesSummaryResult getSummary(@PathParam("competition") @NotNull Competition competition) {
        return competitionService.getMatchesResults(competition);
    }

}
