package dao;

import models.Team;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

/**
 * Created by Guest on 8/18/17.
 */
public class Sql2oTeamDaoTest {

    private Sql2oTeamDao teamDao;
    private Connection conn;
    private Sql2oMemberDao memberDao;


    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        teamDao = new Sql2oTeamDao(sql2o);
        memberDao = new Sql2oMemberDao(sql2o);
        conn = sql2o.open();

    }

    @After
    public void tearDown() throws Exception {
    conn.close();}


    @Test
    public void addingCourseSetsId() throws Exception {
        Team team = setupNewTeam();
        int origTeamId = team.getId();
        teamDao.add(team);
        assertNotEquals(origTeamId, team.getId());
    }

    @Test
    public void existingTeamCanBeFoundById() throws Exception {
        Team team = setupNewTeam();
        teamDao.add(team);
        Team foundTeam = teamDao.findById(team.getId());
        assertEquals(team, foundTeam);
    }
    @Test
    public void addedTeamsAreReturnedFromGetAll() throws Exception {
        Team team = setupNewTeam();
        teamDao.add(team);
        assertEquals(1, teamDao.getAll().size());
    }

    @Test
    public void noCuisinesReturnsEmptyList() throws Exception {
        assertEquals(0, teamDao.getAll().size());
    }

    public Team setupNewTeam() { return new Team("Red", "We love to code");}
    public Team setupNewTeam2() { return new Team("Blue", "We love to hack");}

}