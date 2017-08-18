package dao;

import models.Member;
import models.Team;

import java.util.List;

public interface TeamDao {

    //create -------------
    void add (Team team);
//    List<Member> getAllMembersByTeam(int teamId);
//
//    //read -------------
//    List<Team> getAll();
//
    //find individual team
    Team findById(int id);
    //
////
//    //update individual team
//    void update(int id, String name, String description);
//    //
////
//    //delete individual team
//    void deleteTeamById(int id);
//
//    //delete all teams
//    void clearAllTeams();
}
