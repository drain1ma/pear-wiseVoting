import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        new Main(); 
        
        
    }
    
    public Main() {
        Scanner scnr = new Scanner(System.in);
        int n = scnr.nextInt(); 
        int m = scnr.nextInt(); 
        
        List<Ballot> ballots = new ArrayList<>(); 
        for (int i = 0; i < m; i++)
        {
            Ballot b = new Ballot(); 
            b.votes = scnr.nextInt(); 
            b.order = scnr.next(); 
            ballots.add(b); 
        }
        
        Map<Character, Set<Character>> graph = new HashMap<>(); 
        
        for (int i = 0; i < n; i++)
        {
            char c = (char) ('A' + i); 
            graph.put(c,  new HashSet<Character>()); 
        }
        for (int i = 0; i < n; i++)
        {
            char c1 = (char) ('A' + i); 
            for (int j = 0; j < n; j++)
            {
                if (i != j)
                {
                    char c2 = (char) ('A' + j); 
                    char winner = winner(c1, c2, ballots); 
                    if (winner == c1)
                    {
                        graph.get(c1).add(c2); 
                    }
                }
            }
        }
        
        boolean[] can_win = new boolean[n];
        
        for (int i = 0; i < n; i++)
        {
            char c = (char) ('A' + i); 
            can_win[i] = can_win(graph, c); 
        }
        
        for (int i = 0; i < n; i++)
        {
            char c = (char)('A' + i); 
            if(can_win[i])
            {
                System.out.println(c + ": can win");
            }
            else 
            {
                System.out.println(c + ": can't win"); 
            }
        }
    }
    
    private boolean can_win(Map<Character, Set<Character>> graph, char c) {
        Set<Character> visited = new HashSet<>();       
        Stack<Character> stack = new Stack<>(); 
        
        stack.push(c); 
        while(!stack.empty())
        {
            char current = stack.pop(); 
            if (!visited.contains(current)){
                Set<Character> adj_nodes = graph.get(current); 
                stack.addAll(adj_nodes); 
                visited.add(current); 
            }
        }
        
        if (visited.size() ==  graph.size())
        {
            return true; 
        }
        return false;
    }

    public char winner(char c1, char c2, List<Ballot> ballots) {
        int c1_votes = 0;  
        int c2_votes = 0; 
        
        for (Ballot ballot : ballots) {
            int c1_index = 0; 
            int c2_index = 0; 
            for (int i = 0; i < ballot.order.length(); i++) {
                if (ballot.order.charAt(i) == c1) {
                    c1_index = i; 
                }
                else if (ballot.order.charAt(i) == c2) {
                    c2_index = i; 
                }
            }
            if (c1_index < c2_index) {
                c1_votes += ballot.votes; 
            }
            else {
                c2_votes += ballot.votes; 
            }
        }
        if(c1_votes > c2_votes) {
            return c1; 
        }
        else {
            return c2; 
        }
    }
    class Ballot {
        int votes; 
        String order; 
    }
}