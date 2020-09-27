import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    /*
    * Cael Phillips
    * 000397240
    * I, Cael Phillips 000397240, promise that all of the code is my own
    * */
    public static void main(String[] args) throws FileNotFoundException {
        double start,end,total;//variables for the timer
        final String path = "src/ELEVATIONS.TXT";
        File file = new File(path);
        Scanner reader = new Scanner(file);
        String[] splits;
        String line;
        int row, column, exRadius;
        int[][] elevation;
        Peak[] Peaks;



        line = reader.nextLine();//get the next line
        splits = line.split(" ");//split the line by whitespaces
        row = Integer.parseInt(splits[0]);
        column = Integer.parseInt(splits[1]);
        exRadius = Integer.parseInt(splits[2]);

        elevation = new int[row][column];//init the main matrix

        //populates the array
        for (int i = 0; i < row; i++){
            line = reader.nextLine();
            splits = line.split(" ");//split by space
            for (int j = 0; j < splits.length; j++){
                elevation[i][j]=Integer.parseInt(splits[j]);//populate the array
            }//for
        }//for

        //the good shit
        start=System.currentTimeMillis();//start the timer
        lowValue(elevation,row,column);//finds the lowes value and its frequency
        Peaks=findPeaks(elevation,row,column,exRadius,98480);//supposed to find all peaks. Kinda makes me want to commit unalive
        shortestDistance(Peaks);//finds the closest peaks. Dependant on the last one
        commonElevation(elevation,row,column);//finds the most common elevation and it's frequency
        end=System.currentTimeMillis();//ends the timer
        total=end-start;//calculate time
        System.out.println(total/1000+" seconds");//convert it to seconds
    }//main

    /**
     * Finds the most common elevation
     * @param elevation
     * @param row
     * @param column
     */
    public static void commonElevation(int[][] elevation, int row, int column) {
        int index=0,flag,length=row*column;
        int[] freq = new int[length];

        //loading both arrays
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                freq[elevation[i][j]]+=1;//adds up the frequency
            }//for
        }//for
        flag = freq[0];
        for (int i = 0; i < freq.length; i++) {//sort the array
            if(freq[i]>flag){
                flag=freq[i];
                index=i;
            }//if
        }//for
        System.out.println("The most common elevation is "+index+" appearing "+flag+" times");//print the most common peak
    }

    /**
     * finds the closest peaks
     * @param peaks
     */
    public static void shortestDistance(Peak[] peaks){
        int count=0;
        int length = peaks.length;
        Distance d,temp;
        Distance[] distances = new Distance[peaks.length* peaks.length];

        for (int i = 0; i < length; i++){
            for (int j = i+1; j < length; j++) {
                d = new Distance(peaks[i],peaks[j]);
                distances[count]=d;
                count++;
            }//for
        }//for


        for (int i = 0; i < length-1; i++) {
            for (int j = i+1; j <length; j++) {
                if (distances[i].getDistance()>distances[j].getDistance()){
                    temp=distances[i];
                    distances[i]=distances[j];
                    distances[j]=temp;
                }
            }
        }


        for (int i = 0; i < 3; i++) {
            System.out.printf("The distance between: "+distances[i].getOne()+" and "+distances[i].getTwo()+" is %.2f\n",distances[i].getDistance());
        }
        
        
        
    }

    /**
     * finds the lowest value
     * @param elevation
     * @param row
     * @param column
     */
    public static void lowValue(int[][] elevation, int row, int column){
        int min=elevation[0][0],count=0;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                if (min > elevation[i][j]){
                    min = elevation[i][j];
                    count = 0;
                    i=0;
                    j=0;
                }else if (min == elevation[i][j]){
                    count++;
                }
            }//for
        }//for
        System.out.printf("The lowest value is: %d. It appeared %d times\n",min,count);
    }//lowValue

    /**
     * Finds all the peaks
     * @param elevation
     * @param row
     * @param column
     * @param exRadius
     * @param heightRef
     * @return
     */
    public static Peak[] findPeaks(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int flag,peakCount,count=0,length=column*row,sel;
        Peak[] Peaks=new Peak[length];
        //outer two loops grab a selection
        for (int i = exRadius; i < row-exRadius; i++){//selection row
            for (int j = exRadius; j < column-exRadius; j++){//selection column
                flag=elevation[i][j];//sets the flagged index
                peakCount=0;//counts the indexes that the selection is greater than
                if (flag >= heightRef) {//if the flagged index is greater than the height reference
                    //inner two loops search the selection
                    for (int k = i - exRadius; k < i + exRadius; k++){//start 11 indexes left of the flag and end 11 indexes to the right of the flag
                        for (int l = j - exRadius; l < j + exRadius; l++) {
                            sel = elevation[k][l];//set the comparison index
                            if (flag > sel) {//if the flag is taller
                                peakCount++;//increment counter
                            }//if
                        }//for l
                    }//for k
                    if (peakCount==(int)Math.pow(exRadius*2,2)-1){//if the peak counter is equal to the amount of indexes minus the flagged one
                        //create a Peak
                        Peak P = new Peak(flag, i, j);
                        Peaks[count] = P;
                        count++;
                    }//peakCount
                }//if the flagged value is greater than the height requirement
            }//for j
        }//for i
        Peaks = Arrays.copyOf(Peaks,count);//reduce the array size
        System.out.println("Number of Peaks:\t"+Peaks.length);//display number of peaks
        return Peaks;//return the array
    }//find Peaks





}//Main