/*
 * Cael Phillips
 * 000397240
 * I, Cael Phillips 000397240, promise that all of the code is my own
 * */

public class Peak{
    private int val,row,col;
    public Peak(int val, int row, int col){
        this.val=val;
        this.row=row;
        this.col=col;
    }//contructor

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Peak{" +
                "val=" + val +
                ", row=" + row +
                ", col=" + col +
                '}';
    }
}
