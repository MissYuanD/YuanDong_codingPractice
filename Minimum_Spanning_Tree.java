/*
Given a list of Connections, which is the Connection class (the city name at both ends of the edge and a cost between them), 
find some edges, connect all the cities and spend the least amount. Return the connects if can connect all the cities, otherwise 
return empty list.

!!!Notice: Return the connections sorted by the cost, or sorted city1 name if their cost is same, or sorted city2 if their city1 
name is also same.

Example
Gievn the connections = ["Acity","Bcity",1], ["Acity","Ccity",2], ["Bcity","Ccity",3]

Return ["Acity","Bcity",1], ["Acity","Ccity",2]
*/
/**
 * Definition for a Connection.
 * public class Connection {
 *   public String city1, city2;
 *   public int cost;
 *   public Connection(String city1, String city2, int cost) {
 *       this.city1 = city1;
 *       this.city2 = city2;
 *       this.cost = cost;
 *   }
 * }
 */
public class Solution {
    /**
     * @param connections given a list of connections include two cities and cost
     * @return a list of connections from results
     */
    Map<String, String> parent = null;
    Map<String, Integer> numOfSons = null;
    public List<Connection> lowestCost(List<Connection> connections) {
        // Write your code here
        Collections.sort(connections, new Comparator<Connection>(){
            public int compare(Connection a, Connection b) {
                if (a.cost == b.cost) {
                    if (a.city1.equals(b.city1) ) {
                        return a.city2.compareTo(b.city2);
                    } else {
                        return a.city1.compareTo(b.city1);
                    }
                } else {
                    return a.cost - b.cost;
                }
            }
        });
        //init
        List<Connection> ret = new ArrayList<>();
        parent = new HashMap<String, String>();
        numOfSons = new HashMap<String, Integer>();
        for (Connection curt : connections) {
            if (!parent.containsKey(curt.city1)) {
                parent.put(curt.city1, curt.city1);
            }
            if (!parent.containsKey(curt.city2)) {
                parent.put(curt.city2, curt.city2);
            }
            if (!numOfSons.containsKey(curt.city1) ) {
                numOfSons.put(curt.city1, 1);
            }
            if (!numOfSons.containsKey(curt.city2) ) {
                numOfSons.put(curt.city2, 1);
            }
        }
        // traverse and union
        for (Connection curt : connections) {
            if (!find(curt.city1).equals(find(curt.city2) ) ) {
                ret.add(curt);
                union(curt.city1, curt.city2);
            }
        }
        // return
        if (ret.size() != parent.size() - 1 ) return new ArrayList<Connection>();
        return ret;
    }
    
    private String find(String city) {
        if (parent.get(city).equals(city)) {
            return city;
        }
        String p = find(parent.get(city));
        parent.put(city, p);
        return p;
    }
    
    private void union(String city1, String city2) {
        String p1 = find(city1);
        String p2 = find(city2);
        if (p1.equals(p2) ) {
            return;
        } else {
            int num1 = numOfSons.get(p1);
            int num2 = numOfSons.get(p2);
            if (num1 >= num2) {
               parent.put(p2, p1);
               numOfSons.put(p1, num1 + num2);
            } else {
                parent.put(p1, p2);
                numOfSons.put(p2, num1 + num2);
            }
        }
        return;
    }
}
