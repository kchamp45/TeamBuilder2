///**
// * Created by Kimberly Lu on 8/11/17.
// */
//import java.util.*;
//
//import spark.ModelAndView;
//import spark.template.handlebars.HandlebarsTemplateEngine;
//import static spark.Spark.*;
//
//public class App {
//    public static void main(String[] args) {
//        staticFileLocation("/public");
//
//        get("/", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            ArrayList<Team> teams = Team.getAll();
//            model.put("teams", teams);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "team-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/weekend", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "weekend.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        post("/teams/new", (request, response) -> { //URL to make new team on POST route
//            Map<String, Object> model = new HashMap<String, Object>();
//            String newName = request.queryParams("name");
//            String newDescription = request.queryParams("description");
//            String newOne = request.queryParams("memberName");
//            String newTwo = request.queryParams("memberName2");
//            Team team = new Team(newName, newDescription);
//            team.addMember(newOne);
//            team.addMember(newTwo);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams", (request, response) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            ArrayList<Team> teams = Team.getAll();
//            model.put("teams", teams);
//            return new ModelAndView(model, "index.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            Team.clearAllTeams();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/:id", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToFind = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Team foundTeam = Team.findById(idOfTeamToFind); //use it to find team
//            model.put("team", foundTeam); //add it to model for template to display
//            return new ModelAndView(model, "team-details.hbs"); //individual team page.
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/:id/addMember", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            return new ModelAndView(model, "member-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        post("member/new", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            String teamName = req.queryParams("name");
//            String newThree = req.queryParams("memberName3");
//            Team editTeam = Team.findTeam(teamName);
//            if (editTeam !=null)
//                editTeam.addMember(newThree);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/:id/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToEdit = Integer.parseInt(req.params("id"));
//            Team editTeam = Team.findById(idOfTeamToEdit);
//            model.put("editTeam", editTeam);
//            return new ModelAndView(model, "team-form.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        post("/teams/:id/update", (req, res) -> {
//            Map<String, Object> model = new HashMap<String, Object>();
//            String newName = req.queryParams("name");
//            String newDescription = req.queryParams("description");
//            int idOfTeamToEdit = Integer.parseInt(req.params("id"));
//            Team editTeam = Team.findById(idOfTeamToEdit);
//            editTeam.update(newName, newDescription);
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//        get("/teams/:id/delete", (req, res) -> {
//            Map<String, Object> model = new HashMap<>();
//            int idOfTeamToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
//            Team deleteTeam = Team.findById(idOfTeamToDelete); //use it to find team
//            deleteTeam.deleteTeam();
//            return new ModelAndView(model, "success.hbs");
//        }, new HandlebarsTemplateEngine());
//
//    }
//
//}
//
//
