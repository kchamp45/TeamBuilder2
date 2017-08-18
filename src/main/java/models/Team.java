package models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guest on 8/11/17.
 */
public class Team {

    private String name;
    private String description;
    private int id;
//    private static ArrayList<Team> instances = new ArrayList<>();
//    private LocalDateTime createdAt;


//    private String memberName;
//    private ArrayList<String> members;

    public Team (String name, String description) {
        this.name = name;
        this.description = description;
//        this.id = id;
//        this.createdAt = LocalDateTime.now();
//        this.members = new ArrayList<String>();
//        instances.add(this);
//        this.id = instances.size();
//        this.memberName = memberName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (id != team.id) return false;
        if (!name.equals(team.name)) return false;
        return description.equals(team.description);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + id;
        return result;
    }

//    public ArrayList<String> addMember(String memberName) {
//        this.members.add(memberName);
//        return members;
//    }

//    public ArrayList<String> getMembers(){
//        return members;
//    }


//    public LocalDateTime getCreatedAt() {
//        return createdAt;
//    }

//    public static void clearAllTeams(){
//        instances.clear();
//    }

//    public static ArrayList<Team> getAll(){
//        return instances;
//    }



//    public static Team findById(int id) {
//        return instances.get(id-1);
//    }
//
//    public void update(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    public void deleteTeam(){
//        instances.remove(id-1); //same reason
//    }
//
//    public String getMemberName() {
//        return memberName;
//    }
//
//    public static ArrayList<Team> getInstances() {
//        return instances;
//    }
//
//    public static Team findTeam(String teamName) {
//        Team aTeam = null;
//        for (Team team: instances) {
//            String aName = team.getName();
//            if(aName.equals(teamName)) {
//                aTeam = team;
//            }
//        }
//        return aTeam;
//    }


}

