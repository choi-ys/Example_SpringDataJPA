package io.example.springdatajpa.generator;

import io.example.springdatajpa.domain.entity.Team;

/**
 * @author : choi-ys
 * @date : 2021/04/05 7:34 오후
 * @Content : 팀 TC 수행에 필요한 Team Entity 생성
 */
public class TeamGenerator {

    public static Team createTeam(){
        String teamName = "CoreDevTeam";
        return teamBuilder(teamName);
    }

    public static Team createTeamByTeamName(String teamName){
        return teamBuilder(teamName);
    }

    private static Team teamBuilder(String teamName){
        return Team.builder()
                .name(teamName)
                .build();
    }
}