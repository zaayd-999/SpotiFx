package com.structure.Class.Player.Song;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import com.structure.Class.User.artist;

public class songCredits {
    private final HashMap<contributionType, HashMap<artist, contributionType>> songCredits;

    public songCredits() {
        this.songCredits = new HashMap<>();
        contributionType[] contribuitons = contributionType.values();
        for (contributionType contribuiton : contribuitons) {
            songCredits.put(contribuiton, new HashMap<>());
        }
    }

    public void addContributor(contributionType role, artist contributor) {
        songCredits.get(role).put(contributor,role);
    }

    public void addContributors(contributionType role, artist... contributors) {
        for (artist contributor : contributors) addContributor(role, contributor);
    }

    public void removeContributor(contributionType role, artist contributor) {
        songCredits.get(role).remove(contributor);
    }

    public void removeContributors(contributionType role, artist... contributors) {
        for (artist contributor : contributors) removeContributor(role, contributor);
    }

    public void removeContribution(contributionType role) {
        songCredits.remove(role);
    }

    public void removeContributions(contributionType... roles) {
        for (contributionType role : roles) removeContribution(role);
    }

    public void addContribution(contributionType role) {
        songCredits.putIfAbsent(role, new HashMap<>());
    }

    public void addContributions(contributionType... roles) {
        for (contributionType role : roles) addContribution(role);
    }

    public boolean isContributor(contributionType role, artist contributor) {
        return songCredits.containsKey(role) && songCredits.get(role).containsKey(contributor);
    }

    public void displayCredits() {
        System.out.println("\uD83C\uDFB5 song CREDITS \uD83C\uDFB5\n" +
                "==================");
        for (contributionType role : contributionType.values()) {
            System.out.print("• " + role + " : ");
            for (artist contributor : songCredits.get(role).keySet()) {
                System.out.print(contributor.getUsername() + ",");
            }
            System.out.println();
        }
    }

    @Override
    public String toString() {
        return stringify();
    }

    public String stringify() {
        StringBuilder json = new StringBuilder();
        json.append("{");

        contributionType[] roles = contributionType.values();
        for (int i = 0; i < roles.length; i++) {
            contributionType role = roles[i];
            json.append("\"").append(role.toString().toLowerCase()).append("\":[");

            List<artist> contributors = new ArrayList<>(songCredits.get(role).keySet());
            for (int j = 0; j < contributors.size(); j++) {
                json.append("\"").append(contributors.get(j).getId()).append("\"");
                if (j < contributors.size() - 1) {
                    json.append(",");
                }
            }

            json.append("]");
            if (i < roles.length - 1) {
                json.append(",");
            }
        }

        json.append("}");
        return json.toString();
    }
}