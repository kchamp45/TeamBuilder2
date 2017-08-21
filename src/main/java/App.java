/**
 * Created by Kimberly Lu on 8/11/17.
 */
import java.util.*;

import dao.Sql2oMemberDao;
import dao.Sql2oTeamDao;
import models.Member;
import models.Team;
import org.sql2o.Sql2o;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/todolist.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        Sql2oMemberDao memberDao = new Sql2oMemberDao(sql2o);
        Sql2oTeamDao teamDao = new Sql2oTeamDao(sql2o);

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            List<Member> members = memberDao.getAll();
            model.put("members", members);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //show general info about team event
        get("/weekend", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "weekend.hbs");
        }, new HandlebarsTemplateEngine());

        // delete all teams
        get("/teams/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            teamDao.clearAllTeams();
            memberDao.clearAllMembers();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //show new team form
        get("/teams/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> teams = teamDao.getAll();
            model.put("teams", teams);
            return new ModelAndView(model, "team-form.hbs"); //new
        }, new HandlebarsTemplateEngine());

        //process new team form
        post("/teams/new", (request, response) -> { //new
            Map<String, Object> model = new HashMap<>();
            String name = request.queryParams("name");
            String description = request.queryParams("description");
            Team newTeam = new Team(name, description);
            teamDao.add(newTeam);
            List<Team> teams = teamDao.getAll(); //refresh list of links for navbar.
            model.put("teams", teams);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get more info about a team
        get("/teams/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToFind = Integer.parseInt(req.params("id"));
            List<Team>teams = teamDao.getAll();
            model.put("teams", teams);
            Team foundTeam = teamDao.findById(idOfTeamToFind); //use it to find team
            model.put("team", foundTeam); //add it to model for template to display
            List<Member> allMembersByTeam = teamDao.getAllMembersByTeam(idOfTeamToFind);
            model.put("members", allMembersByTeam);
            return new ModelAndView(model, "Team-detail.hbs"); //individual team page
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a team
        get("/teams/:team_id/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int thisId = Integer.parseInt(req.params("team_id"));
            model.put("editTeam", true);
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "Team-form.hbs");
        }, new HandlebarsTemplateEngine());

        //post: process a form to update a team and members it contains
        post("/teams/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToEdit = Integer.parseInt(req.queryParams("editTeamId"));
            String newName = req.queryParams("newTeamName");
            String newDescription = req.queryParams("description");
            teamDao.update(teamDao.findById(idOfTeamToEdit).getId(), newName, newDescription);
            List<Team> allTeams = teamDao.getAll(); //refresh list of links for navbar.
            model.put("teams", allTeams);
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual team
        get("/teams/:team_id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfTeamToDelete = Integer.parseInt(req.params("team_id")); //pull id - must match route segment
            Team deleteTeam = teamDao.findById(idOfTeamToDelete); //use it to find task
            teamDao.deleteTeamById(idOfTeamToDelete);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new member form
        get("/members/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);
            return new ModelAndView(model, "Member-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process new member form
        post("/members/new", (request, response) -> { //URL to make new task on POST route
            Map<String, Object> model = new HashMap<>();
            List<Team> allTeams = teamDao.getAll();
            model.put("teams", allTeams);

            String name = request.queryParams("name");
            int teamId = Integer.parseInt(request.queryParams("teamId"));
            Member newMember = new Member(name, teamId);
            memberDao.add(newMember);
            model.put("member", newMember);
            return new ModelAndView(model, "success2.hbs");
        }, new HandlebarsTemplateEngine());

        //get information about a member
        get("/teams/:team_id/members/:member_id", (req, res) ->{
           Map<String, Object> model = new HashMap<>();
           int idOfMemberToFind = Integer.parseInt(req.params("member_id"));
           Member foundMember = memberDao.findById(idOfMemberToFind);
           model.put("member", foundMember);
           return new ModelAndView(model, "Member-detail.hbs");
        }, new HandlebarsTemplateEngine());

        //show a form to update a member
        get("/teams/:id/members/:teamId/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int teamIdOfMemberToEdit = Integer.parseInt(req.params("id"));
            int idOfMemberToEdit = Integer.parseInt(req.params("teamId"));
            Member editMember = memberDao.findById(idOfMemberToEdit);
            model.put("editMember", true);
            model.put("teamIdOfMemberToEdit", teamIdOfMemberToEdit);
            List<Member>allMembers = memberDao.getAll();
            List<Team> allTeams = teamDao.getAll();
            model.put("idOfMemberToEdit", idOfMemberToEdit);
            model.put("members", allMembers);//add all members to model
            model.put("teams", allTeams); //add all teams to model
            return new ModelAndView(model, "member-form.hbs");
        }, new HandlebarsTemplateEngine());

        //process a form to update a member
        post("/teams/:id/members/:teamId/update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            String newName = req.queryParams("name");
            int teamIdOfMemberToEdit = Integer.parseInt(req.params("id"));
            int idOfMemberToEdit = Integer.parseInt(req.params("teamId"));
            Member editMember = memberDao.findById(idOfMemberToEdit);
            memberDao.update(idOfMemberToEdit,newName, teamIdOfMemberToEdit);
            model.put("idOfMemberToEdit", idOfMemberToEdit);
            model.put("teamIdOfMemberToEdit", teamIdOfMemberToEdit);
            return new ModelAndView(model, "success2.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual member
        get("/teams/:id/members/:teamId/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int teamIdOfMemberToDelete = Integer.parseInt(req.params("id"));
            int idOfMemberToDelete = Integer.parseInt(req.params("teamId"));
            Member deleteMember = memberDao.findById(idOfMemberToDelete); //use it to find member
            memberDao.deleteById(idOfMemberToDelete);
            List<Member> members = memberDao.getAll();
            model.put("members", members);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //delete all members
        get("/members/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            memberDao.clearAllMembers();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

    }

}


