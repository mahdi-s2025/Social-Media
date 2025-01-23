package org.example.socialmedia.Models;

import org.example.socialmedia.Controller.AccountController;

import java.util.*;

class Pair {

    String username;
    double probability;

    Pair(String username , double probability){
        this.username = username;
        this.probability = probability;
    }
}

public class Graph {

    private static Graph graph;
    String username;
    List<List<String>> currentUsers;
    PriorityQueue<Pair> pbTable;
    private Graph(){
        currentUsers = new ArrayList<>();
        pbTable = new PriorityQueue<>(Comparator.comparingDouble(user -> user.probability));
    }
    public static Graph getGraph(){
        if(graph == null){
            graph = new Graph();
        }
        return graph;
    }


    public void addEdge(String currentUser, String user2){

        for(List<String> userConnection : currentUsers){

            if(userConnection.get(0).equals(currentUser)){
                userConnection.add(user2);
                username = currentUser;
            }
            if(userConnection.get(0).equals(user2)){
                userConnection.add(currentUser);
            }
        }
    }
    public void addVertex(String username){
        List<String> connections=new ArrayList<>();
        connections.add(username);
        currentUsers.add(connections);
    }


    private List<String> findConnection(String usename){
        for (List<String> connections : currentUsers){
            if (connections.get(0).equals(usename)){
                return connections;
            }
        }
        return null;
    }
    public void setProbability(){
        List<String> currentUserConnections = findConnection(AccountController.getCurrentAccount().getUsername());

        for(List<String> connections : currentUsers){

            int isPair=0;

            if(connections.get(0).equals(username)){
                continue;
            }

            if (connections.contains(currentUserConnections.get(0))){
                continue;
            }
            for (String username : currentUserConnections){

                if(username.equals(currentUserConnections.get(0))){
                    continue;
                }

                if(connections.contains(username)){
                    isPair++;
                }
            }

            int notPair = (connections.size() - isPair) + (currentUserConnections.size() - isPair);

            double pb = (notPair > 0) ? (double) isPair / notPair : (double) isPair;
            int index = currentUsers.indexOf(connections);
            Pair pair = new Pair(currentUsers.get(index).get(0) , pb);
            pbTable.add(pair);
        }
    }
}
