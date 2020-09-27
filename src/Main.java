import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        double start,end,total;
        final String path = "src/ELEVATIONS.TXT";
        File file = new File(path);
        Scanner reader = new Scanner(file);
        String[] splits;
        String line;
        int row, column, exRadius;
        int[][] elevation;
        Peak[] Peaks;

        start=System.currentTimeMillis();

        line = reader.nextLine();//get the next line
        splits = line.split(" ");//split the line by whitespaces
        row = Integer.parseInt(splits[0]);
        column = Integer.parseInt(splits[1]);
        exRadius = Integer.parseInt(splits[2]);

        elevation = new int[row][column];

        for (int i = 0; i < row; i++){
            line = reader.nextLine();
            splits = line.split(" ");//split by space
            for (int j = 0; j < splits.length; j++){
                elevation[i][j]=Integer.parseInt(splits[j]);//populate the array
            }//for
        }//for

        lowValue(elevation,row,column);
        Peaks=findPeaks(elevation,row,column,exRadius,98480);
        shortestDistance(Peaks);
        commonElevation(elevation,row,column);
        end=System.currentTimeMillis();
        total=end-start;
        System.out.println(total/1000+" seconds");
        
    }//main

    public static void commonElevation(int[][] elevation, int row, int column) {
        int index=0,flag=0,x=0,length=row*column;
        int[] heights = new int[length];
        int[] freq = new int[length];

        //loading both arrays
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                heights[x]=elevation[i][j];
                freq[elevation[i][j]]+=1;
                x++;
            }//for
        }//for
        flag = freq[0];
        for (int i = 0; i < freq.length; i++) {
            if(freq[i]>flag){
                flag=freq[i];
                index=i;
            }
        }
        System.out.println("The most common elevation is "+index+" appearing "+flag+" times");
    }
    public static void shortestDistance(Peak[] peaks){
        int count=0,end;
        int length = peaks.length;
        Distance d,temp;
        Distance[] distances = new Distance[peaks.length* peaks.length], Dist;

        for (int i = 0; i < length; i++){
            for (int j = i+1; j < length; j++) {
                d = new Distance(peaks[i],peaks[j]);
                distances[count]=d;
                count++;
            }//for
        }//for

        Dist=Arrays.copyOf(distances,count);

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



    public static Peak[] findPeaks(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int flag,peakCount,count=0,length=column*row,sel,k,l=0;
        Peak[] Peaks=new Peak[length];
        //outer two loops grab a selection
        for (int i = exRadius; i < row-exRadius; i+=exRadius*2){
            for (int j = exRadius; j < column-exRadius; j+=exRadius*2){
                peakCount=0;
                flag=elevation[i][j];

                //inner two loops search the selection
                for (k = i-exRadius; k <i+exRadius; k++) {
                    for (l = j-exRadius; l < j+exRadius; l++) {
                        sel=elevation[k][l];
                        if (sel>flag && sel>heightRef){
                            flag=sel;
                        }
                    }//for l
                }//for k
                if(flag>=heightRef){
                    Peak P = new Peak(flag,k,l);
                    System.out.println(P.toString());
                    Peaks[count] = P;
                    count++;
                }//peakCount

            }//for j
        }//for i

        Peaks = Arrays.copyOf(Peaks,count);

        System.out.println("Number of Peaks:\t"+Peaks.length);
        return Peaks;
    }//find Peaks



}//Main