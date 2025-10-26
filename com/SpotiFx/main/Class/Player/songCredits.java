package com.SpotiFx.main.Class.Player;
import java.util.HashMap;

public class songCredits {
    private final HashMap<contributionType,HashMap<String,contributionType>> songCredits;

    public songCredits() {
        this.songCredits = new HashMap<>();
        contributionType[] contribuitons = contributionType.values();
        for (contributionType contribuiton : contribuitons) {
            songCredits.put(contribuiton, new HashMap<>());
        }
    }

    public void addContributor(contributionType role , String name) {
        songCredits.get(role).put(name,role);
    }

    public void addContributors(contributionType role , String... names) {
        for(String name : names) addContributor(role,name);
    }

    public void removeContributor(contributionType role , String name) {
        songCredits.get(role).remove(name);
    }

    public void removeContributors(contributionType role , String... names){
        for(String name : names) removeContributor(role,name);
    }

    public void removeContribution(contributionType role) {
        songCredits.remove(role);
    }

    public void removeContributions(contributionType... roles){
        for(contributionType role : roles) removeContribution(role);
    }

    public void addContribution(contributionType role){
        songCredits.putIfAbsent(role , new HashMap<>());
    }

    public void addContributions(contributionType... roles){
        for (contributionType role : roles) addContribution(role);
    }

    public boolean isContributor(contributionType role , String name) {
        return songCredits.containsKey(role) && songCredits.get(role).containsKey(name);
    }

    public void displayCredits() {
        System.out.println("\uD83C\uDFB5 SONG CREDITS \uD83C\uDFB5\n" +
                "==================");
        for(contributionType role : contributionType.values()) {
            System.out.print("• " + role + " : ");
            for(String name : songCredits.get(role).keySet()) {
                System.out.print(name + ",");
            }
            System.out.println();
        }
    }
}