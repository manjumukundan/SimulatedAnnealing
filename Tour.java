
import java.util.ArrayList;
import java.util.Collections;

public class Tour{

    // Tour of cities
    private ArrayList tour = new ArrayList<City>();

    private int length = 0;
    
    public Tour(){
        for (int i = 0; i < City.numberOfCities(); i++) {
            tour.add(null);
        }
    }
    
    // Constructs a tour from another tour
    // copy constructor
    public Tour(ArrayList tour){
        this.tour = (ArrayList) tour.clone();
    }

    public ArrayList getTour(){
        return tour;
    }

    // Creates a random tour
    public void createRandomTour() {
       
        for (int cityIndex = 0; cityIndex < City.numberOfCities(); cityIndex++) {
          setCity(cityIndex, City.getCity(cityIndex));
        }
        
        Collections.shuffle(tour);
    }

    // Gets a city from the tour
    public City getCity(int tourPosition) {
        return (City)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        // If the tours been altered we need to reset the length
        length = 0;
    }
    
    // Gets the total distance of the tour
    public int getTourLength(){
        if (length == 0) {
            int tourLength = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                City fromCity = getCity(cityIndex); 
                City destinationCity;
                // check if we are in the last city , then set the initial city as the destination city.
                if(cityIndex+1 < tourSize()){
                    destinationCity = getCity(cityIndex+1);
                }
                else{
                    destinationCity = getCity(0);
                }
                tourLength += fromCity.distanceTo(destinationCity);
            }
            length = tourLength;
        }
        return length;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }
    
    @Override
    public String toString() {
        String tour = "";
        for (int i = 0; i < tourSize(); i++) {
        	tour += getCity(i)+" -> ";
        }
        return tour;
    }
}