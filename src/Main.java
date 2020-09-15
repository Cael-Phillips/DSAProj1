import java.io.File;
import java.io.FileNotFoundException;
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
        findPeaks(elevation,row,column,exRadius,98480);
        //TEST(elevation,row,column,exRadius,98480);



        end=System.currentTimeMillis();
        total=end-start;
        System.out.println(total/1000+" seconds");
        
    }//main

    public static void findPeaks(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int length=elevation.length;
        int peak,count=0;
        int rowAmount=row,colAmount=column;
        Peak[] Peaks=new Peak[length];

        for (row = exRadius; row < rowAmount-exRadius; row+=exRadius/2) {
            for (column = exRadius; column <colAmount-exRadius; column+=exRadius/2){
                peak = elevation[row][column];
                if(peak>=heightRef){
                    Peak P = new Peak(peak,row,column);
                    Peaks[count]=P;
                    count++;
                }//if
            }//Column
        }//Row
        Peaks = Arrays.copyOf(Peaks,count);
    }//find Peaks

    public static void TEST(int[][] elevation, int row, int column, int exRadius, int heightRef){
        int length=elevation.length;
        int flag=0,peak,count=0;
        int rowAmount=row,colAmount=column;
        Peak[] Peaks=new Peak[length];
        row=0;
        column=0;
        /*
        while(row<rowAmount-exRadius&&column<=colAmount-exRadius){
            peak = elevation[row][column];
            for (int i = 0; i < 11; i++) {
                if (peak<elevation[row][column+i]||peak<elevation[row][column-i]){
                    if (elevation[row][column+i]>elevation[row][column-i]){
                        peak=elevation[row][column+i];
                    }else{
                        peak=elevation[row][column-i];
                    }
                }
            }
            if(peak>=heightRef){
                Peak P = new Peak(peak,row,column);
                Peaks[count]=P;
                count++;
            }
            row++;
            column++;
        }
        */
        while(row<rowAmount-exRadius&&column<=colAmount-exRadius){
            peak = elevation[row][column];
            for (int i = 0; i < exRadius; i++) {
                for (int j = 0; j < exRadius; j++) {
                    if (peak<elevation[row+i][column+j]&&flag<elevation[row+i][column+i]){
                        flag=elevation[row+i][column+i];
                        System.out.println(flag);
                    }//if
                }//for
            }//for
            if(peak>=heightRef){
                Peak P = new Peak(peak,row,column);
                Peaks[count]=P;
                count++;
            }
            row++;
            column++;
        }



        Peaks = Arrays.copyOf(Peaks,count);
        System.out.println(Arrays.toString(Peaks));
        System.out.println(Peaks.length);
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
