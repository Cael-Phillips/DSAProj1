public class Distance{
    Peak One, Two;
    double distance;
    public Distance(Peak P1, Peak P2){
        One = P1;
        Two = P2;
        calculateDistance(P1,P2);
    }//Distance
    public double calculateDistance(Peak One, Peak Two){
        distance = Math.pow((One.getRow()-Two.getRow()),2)+Math.pow((One.getCol()-Two.getCol()),2);
        distance = Math.abs(distance);
        distance = Math.sqrt(distance);
        return distance;
    }
    public double getDistance() {
        return distance;
    }
}
