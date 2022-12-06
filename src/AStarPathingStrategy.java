import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<Point>();

        Map<Node, Node> closedList = new HashMap<Node, Node>(); // prev node, curr node
        Queue<Node> openList = new PriorityQueue<Node>(Node::compareTo); // pqueue on smallest F
        Map<Node, Integer> openListMap = new HashMap<Node, Integer>(); // map to compare G vals

        Node startNode = new Node(0, distanceForm(start, end), distanceForm(start, end), start);

        openList.add(startNode);
        openListMap.put(startNode, startNode.getG());
        closedList.put(startNode, startNode);

        while(!openList.isEmpty())
        {
            Node curr = openList.poll(); // dequeue + sets curr to what has just been dequeued

            if (withinReach.test(curr.getLocation(), end))
            {
                path.add(curr.getLocation()); // add the last node's location

                while(!curr.getLocation().equals(start)) // do not want to include start loc in path
                {
                    Node tempNode = closedList.get(curr); // previous node of my current node
                    if(!tempNode.getLocation().equals(start)) // if the tempNode is NOT the start
                        path.add(tempNode.getLocation()); // add to the path
                    curr = tempNode; // setting curr node to prev node of curr node
                }
                Collections.reverse(path);
                return path;
            }

            // up down left right neighbors ONLY; list of potential neighbors
            // potential neighbor can't be start or end node
            List<Point> neighbors = potentialNeighbors.apply(curr.getLocation())
                    .filter(canPassThrough)
                    .filter(pt -> !pt.equals(start) && !pt.equals(end))
                    .collect(Collectors.toList());

            for (Point neighbor: neighbors) // for all the neighbors
            {
                Node neighborNode = new Node(0,0,0, neighbor);
                if (closedList.get(neighborNode) == null) // if the neighbor is NOT on the closed list
                {
                    int gValue = curr.getG() + 1; // dist of curr + dist from curr to adjacent

                    if(openListMap.containsKey(neighborNode)) {
                        if (gValue < openListMap.get(neighborNode)) { // if the g value on openListMap is > than curr
                            Node betterG = new Node(gValue, distanceForm(neighbor, end),
                                    distanceForm(neighbor, end) + gValue, neighbor);
                            openList.remove(betterG); // remove from queue
                            openList.add(betterG); // readd to resort the queue
                            openListMap.replace(neighborNode, betterG.getG()); // replace to the better G on the openMap
                        }
                    }
                    else {
                        // calculate heuristic values
                        int gHeuristic = curr.getG() + 1; // dist from start
                        int hHeuristic = distanceForm(neighbor, end); // dist from loc to end
                        int fHeuristic = gHeuristic + hHeuristic; // sum of G + H

                        // add neighbor to open list + map
                        Node neighborBuddy = new Node(gHeuristic, hHeuristic, fHeuristic, neighbor);
                        openList.add(neighborBuddy); // adding node to openList
                        openListMap.put(neighborBuddy, neighborBuddy.getG()); // also putting it on openListMap
                        closedList.put(neighborBuddy, curr);
                    }
                }
            }
        }
        return path;
    }

    public int distanceForm(Point one, Point two)
    {
        return Math.abs(two.x - one.x) + Math.abs(two.y - one.y);
    }

}
