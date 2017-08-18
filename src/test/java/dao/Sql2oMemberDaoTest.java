package dao;

import models.Member;
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
public class Sql2oMemberDaoTest {
    private Sql2oMemberDao memberDao;
    private Connection conn; //must be sql2o class conn

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        memberDao = new Sql2oMemberDao(sql2o);
        conn = sql2o.open();
    }
    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingCourseSetsId() throws Exception {
        Member member = setupNewMember();
        int origMemberId = member.getId();
        memberDao.add(member);
        assertNotEquals(origMemberId, member.getId());
    }

    @Test
    public void existingMemberCanBeFoundById() throws Exception {
        Member member = setupNewMember();
        memberDao.add(member);
        Member foundMember = memberDao.findById(member.getId());
        assertEquals(member, foundMember);
    }
    @Test
    public void returnAlladdedMembersFromgetAll() throws Exception {
        Member member = setupNewMember();
        memberDao.add(member);
        assertEquals(1, memberDao.getAll().size());
    }
    @Test
    public void noMembersReturnsEmptyList() throws Exception {
        assertEquals(0, memberDao.getAll().size());
    }


    public Member setupNewMember() { return new Member("Perry");}
    public Member setupNewMember2() { return new Member("Tim");}
}