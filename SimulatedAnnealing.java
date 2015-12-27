
/*
 *   Simulated Annealing algorithm for solving Traveling Salesman Problem.
 *   At high temperatures, worser tours are accepted, as temperature decreases the probability of accepting worse 
 *   tours also decreases. Cooling schedule is chosen such that the temperature reduces slowly and gradually so 
 *   more worse tours are rejected and optimal solutions are found at the lowest temperature.
 *   Cooling schedule is deducted from the temperature between a range of 0.0 and 1.0.
 */
public class SimulatedAnnealing 
{

	public static int xCoord[] = {34,47,5,93,91,57,55,2,46,32,99,8,87,92,39,72,66,5,44,18,59,88,75,64,49,23,77,92,11,70,39,32,79,54,
			90,70,39,72,77,3,80,74,63,66,94,78,14,17,65,52,36,73,4,76,12,54,33,53,77,36,14,75,49,51,90,30,29,
			85,11,73,29,49,77,55,32,59,86,18,48,0,9,69,57,80,30,59,47,75,23,7,28,78,37,7,3,76,86,45,
			43,58,75,58,12,28,55,37,23,6,61,14,29,95,22,63,29,78,29,50,86,84};
	
	public static int yCoord[] = {63,1,31,9,64,45,44,40,3,29,83,27,93,38,83,2,86,70,73,43,76,80,13,
			11,20,9,94,5,91,0,42,61,90,53,89,47,70,95,61,41,19,83,61,88,60,63,25,86,16,89,40,21,10,
			29,93,86,93,56,61,39,6,94,32,9,0,76,3,1,88,37,48,22,8,67,12,43,73,26,74,32,98,40,1,94,
			50,32,19,71,71,94,79,59,19,85,12,44,64,55,27,37,67,79,39,39,68,88,2,21,35,89,62,34,26,22,58,61,96,5,47,1};
	
	public static void main(String[] args) 
	{      
		// TODO Auto-generated method stub
		for (int i = 0; i < xCoord.length; i ++)
		{
			City city = new City(xCoord[i], yCoord[i], i + 1);
			City.addCity(city);
		}
		
        double temp = 100;

        /* Cooling rate at which temperature decreases from its values.
        	as the cooling rate increases, the temperature is decreased more gradually, 
        	so does the acceptance of the tours. */
        double coolingRate = 0.000005; 
        
        long startTime = System.currentTimeMillis();
        
        Tour currentTour = new Tour();
        currentTour.createRandomTour();
        
        System.out.println("Initial random tour length: " + currentTour.getTourLength());
        
        Tour best = new Tour(currentTour.getTour());
        
        int tem = 0;
        while (temp > 1)
        {
        	Tour newTour = new Tour(currentTour.getTour());
        	
        	 // Get a random positions in the tour
            int pos1 = (int) (newTour.tourSize() * Math.random());
            int pos2 = (int) (newTour.tourSize() * Math.random());        
            City city1 = newTour.getCity(pos1);
            City city2 = newTour.getCity(pos2);
            newTour.setCity(pos2, city1);
            newTour.setCity(pos1, city2);  
            int currentLength = currentTour.getTourLength();
            int newLength = newTour.getTourLength();
            
            // Decide if we should accept the neighbor
            if (acceptanceProbability(currentLength, newLength, temp) > Math.random()) {
            	currentTour = new Tour(newTour.getTour());
            }
            
            // find the best solution
            if (currentTour.getTourLength() < best.getTourLength()) {
                best = new Tour(currentTour.getTour());
            }
            
            // find intermediate fittest or best tours.
            double i = temp;
            if (i > 0)
            {       	
            	int j = (int)i % 10;
            	if (j != tem)
            	{
            		System.out.println("Intermediate random tour distance " + i + " : " + best.getTourLength());
            		tem = j; 
            	}
            } 
            
            // Cool system
            temp *= 1 - coolingRate;
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("Best solution length: " + best.getTourLength());
        System.out.println("Tour: " + best);
        
        long timeTaken = (endTime - startTime);
        System.out.println("Time Taken: " + timeTaken + "ms ");
	}
	
	// Calculate the acceptance probability
    public static double acceptanceProbability(int currentLength, int newLength, double temperature) 
    {
        // If the new solution is good, accept it
        if (newLength < currentLength) {
            return 1.0;
        }
        // If the new solution is bad, calculate an acceptance probability
        return Math.exp((currentLength - newLength) / temperature);
    }


}
