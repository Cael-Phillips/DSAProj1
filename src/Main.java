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

        //lowValue(elevation,row,column);
        Peaks=findPeaks(elevation,row,column,exRadius,98480);
        //shortestDistance(Peaks);

        end=System.currentTimeMillis();
        total=end-start;
        System.out.println(total/1000+" seconds");
        
    }//main

    private static void shortestDistance(Peak[] peaks){
        int count=0;
        int length = peaks.length;
        int min=0,index=0;
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
//        for (int i = 0; i < count; i++) {
  //          System.out.printf("Distance: %.2f\n",distances[i].getDistance());
    //    }

        for (int start = 0; start<count; start++) {

        }
        
        
        
        
    }
    public static Peak[] findPeaks(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int flag,peakCount,count=0,length=row*column,x,breakCount=0;
        int[] selCheck = new int[length];
        Peak[] Peaks=new Peak[length];
        boolean dupe=false;
        //outer two loops grab a selection
        for (int i = exRadius; i < row-exRadius; i+=exRadius){
            for (int j = exRadius; j < column-exRadius; j+=exRadius){
                peakCount=0;
                flag=elevation[i][j];
                x=0;

                //sets a check by array
                for (int k = i-exRadius; k <i+exRadius; k++) {
                    for (int l = j - exRadius; l < j + exRadius; l++){
                        selCheck[x]=elevation[k][j];
                        x++;
                    }//for
                }//for

                //inner two loops search the selection
                for (int k = i-exRadius; k <i+exRadius; k++) {
                    for (int l = j-exRadius; l < j+exRadius; l++) {

                        //Find if the flag is taller or smaller than the selection
                        if (elevation[k][l]<flag){//flag taller
                            peakCount++;//increase Peak counter
                        }else if(elevation[k][l]>flag){//smaller
                            flag=elevation[k][l];//change peak
                            peakCount++;//increase Peak counter
                        }

                        for (x = 0;  x< selCheck.length ; x++) {
                            if (flag == selCheck[x]){
                                dupe=true;
                                break;
                            }else{
                                dupe=false;
                            }
                        }
                    }//for l
                }//for k
                if(flag>=heightRef && peakCount>=(int)Math.pow(exRadius*2,2) && !dupe){
                    Peak P = new Peak(flag,i,j);
                    System.out.println(P.toString());
                    Peaks[count] = P;
                    count++;
                }//peakCount

            }//for j
        }//for i
        System.out.println("Number of Peaks:\t"+count);
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
                    i=0;
                    j=0;
                }else if (min == elevation[i][j]){
                    count++;
                }
            }//for
        }//for
        System.out.printf("The lowest value is: %d. It appeared %d times\n",min,count);
    }//lowValue
}//Main