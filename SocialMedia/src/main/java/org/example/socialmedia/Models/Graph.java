package org.example.socialmedia.Models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

    private static Graph graph;
    private Graph(){

    }
    public static Graph getGraph(){
        if(graph == null){
            graph = new Graph();
        }
        return graph;
    }
    int currentIndex=0;
    List<List<String>> currentUsers=new ArrayList<>();
    ArrayList<Double> pbTable=new ArrayList<>();
    public void addEdge(String currentUser,String user2){
        for(List<String> current:currentUsers){
            if(current.get(0).equals(currentUser)){
                current.add(user2);
                currentIndex=currentUsers.indexOf(current);
            }
            if(current.get(0).equals(user2)){
                current.add(currentUser);
                for (String name:current){
                    System.out.println(name);
                }
            }
        }
    }
    public void addVertex(String usernme){
        List<String> connections=new ArrayList<>();
        connections.add(usernme);
        currentUsers.add(connections);
    }
    public void setProbability(){
        for (int i = 0; i <currentUsers.size() ; i++) {
            pbTable.add(-1.0);
        }
        List<String> currentConnections=currentUsers.get(currentIndex);
        int isPair=0;
        int notPair=0;
        for(List<String> connections:currentUsers){
            if(currentIndex == currentUsers.indexOf(connections)){
                continue;
            }
            for (String usernameC1:currentConnections){
                if(usernameC1.equals(currentConnections.get(0))){
                    continue;
                }
                if(connections.contains(usernameC1)){
                    isPair++;
                }
            }
            notPair=(connections.size() + currentConnections.size()-2*isPair)-1;
            System.out.println(notPair);
           double pb=(double)isPair/notPair;
            int index=currentUsers.indexOf(connections);
            pbTable.set(index,pb);
        }
        for (double d:pbTable) {
            System.out.println(d);
        }
    }
}
