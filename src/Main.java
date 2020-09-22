import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
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

        end=System.currentTimeMillis();
        total=end-start;
        System.out.println(total/1000+" seconds");
        
    }//main

    private static void shortestDistance(Peak[] peaks){
        int count=0;
        int length = peaks.length;
        Distance d;
        Distance[] distances = new Distance[peaks.length* peaks.length];

        for (int i = 0; i < length; i++){
            for (int j = i+1; j < length; j++) {
                d = new Distance(peaks[i],peaks[j]);
                distances[count]=d;
                count++;
            }//for
        }//for

        distances=Arrays.copyOf(distances,count);
        for (int i = 0; i < count; i++) {
            System.out.printf("Distance: %.2f\n",distances[i].getDistance());
        }
    }
    public static Peak[] findPeaks(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int flag=0,peakCount=0,count=0;
        int length=elevation.length;
        Peak[] Peaks=new Peak[length];

        for (int i = exRadius; i < row-exRadius; i+=exRadius){
            for (int j = exRadius; j < column-exRadius; j+=exRadius){
                peakCount=0;
                flag=elevation[i][j];

                for (int k = i-exRadius; k <i; k++) {
                    for (int l = j-exRadius; l <j; l++) {
                        if (elevation[k][l]<flag ){
                            peakCount++;
                        }//if
                    }//for l
                }//for k
                if(peakCount==Math.pow(exRadius,2) && flag>=heightRef){
                    Peak P = new Peak(flag,i,j);
                    Peaks[count] = P;
                    count++;
                }//peakCount
            }//for j
        }//for i
        System.out.println(count);
        Peaks = Arrays.copyOf(Peaks,count);
        for (int i = 0; i < count; i++) {
            System.out.println(Peaks[i].toString());
        }
        return Peaks;
    }//find Peaks
    public static void lowValue(int[][] elevation, int row, int column){
        int min=elevation[0][0],count=0;
        for(int i = 0; i < row; i++) {
            for(int j = 0; j < column; j++) {
                if (min > elevation[i][j]){
                    min = elevation[i][j];
                    count = 0;
                }else if (min == elevation[i][j]){
                    count++;
                }else{
                    // :/ just vibin
                }//else
            }//for
        }//for
        System.out.printf("The lowest value is: %d. It appeared %d times\n",min,count);
    }//lowValue
}//Main