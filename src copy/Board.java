import javax.swing.*;
import java.awt.event.ActionListener;

public class Board {
    private Field fields[][];
    private int bombLocations[][];
    private ClassLoader loader = getClass().getClassLoader();
    private int bombsPlaced, masterH, masterW;
    private boolean visited[][];

    public Board(ActionListener AL, int height, int width, int bombs){
        //initialize fields and visited matricies
        fields = new Field[height][width];
        visited = new boolean[height][width];
        for (int h = 0; h <height;h++){
            for (int w = 0; w < width; w++){
                fields[h][w] = new Field();
                visited[h][w] = false;
            }
        }
        masterH = height;
        masterW = width;
        //add all the bombs, and calculate surrondings
        this.bombPlacer(bombs);
        this.bombSurronding();
        this.addListener(AL);

    }

    public void fillBoardView(JPanel boardView) {
        //add the buttons to the view
        for (int i =0; i < masterH; i++){
            for (int j =0; j < masterW; j++) {
                boardView.add(fields[i][j]);
            }
        }
    }


    public void revealBombs() {
        //reveal all the bombs based on bomb location array
        for (int i = 0; i < bombsPlaced; i++){
            fields[bombLocations[i][0]][bombLocations[i][1]].isClicked();
        }

    }

    public void revealZeros(Field clicked) {
        //calculate surronding points
        int above = clicked.returnX()-1;
        int below = clicked.returnX()+1;
        int left = clicked.returnY()-1;
        int right = clicked.returnY()+1;

        if (above >=0){ //makes sure not out of bound
            if (left >=0 && !visited[above][left]) { //make sure not visited yet
                this.isVisited(fields[above][left]); //mark as visited
                if (fields[above][left].customName().equals("0")){ //checks if 0
                    revealZeros(fields[above][left]); //reveal surronding recurssive
                }
            }//repeats but for next square in board
            if(!visited[above][clicked.returnY()]) {
                this.isVisited(fields[above][clicked.returnY()]);

                if (fields[above][clicked.returnY()].customName().equals("0")) {
                    revealZeros(fields[above][clicked.returnY()]);
                }
            }
            if (right < masterW && !visited[above][right]){
                this.isVisited(fields[above][right]);
                if (fields[above][right].customName().equals("0")){
                    revealZeros(fields[above][right]);
                }
            }
        }
        if (left >=0){
            if(!visited[clicked.returnX()][left]) {
                this.isVisited(fields[clicked.returnX()][left]);
                if (fields[clicked.returnX()][left].customName().equals("0")) {
                    revealZeros(fields[clicked.returnX()][left]);
                }
            }
        }
        if (right < masterW){
            if(!visited[clicked.returnX()][right]) {
                this.isVisited(fields[clicked.returnX()][right]);
                if (fields[clicked.returnX()][right].customName().equals("0")) {
                    revealZeros(fields[clicked.returnX()][right]);
                }
            }
        }
        if (below < masterH){
            if (left >=0 && !visited[below][left]){
                this.isVisited(fields[below][left]);
                if (fields[below][left].customName().equals("0")){
                    revealZeros(fields[below][left]);
                }
            }
            if(!visited[below][clicked.returnY()]) {
                this.isVisited(fields[below][clicked.returnY()]);
                if (fields[below][clicked.returnY()].customName().equals("0")) {
                    revealZeros(fields[below][clicked.returnY()]);
                }
            }
            if (right < masterW && !visited[below][right]){
                this.isVisited(fields[below][right]);
                if (fields[below][right].customName().equals("0")){
                    revealZeros(fields[below][right]);
                }
            }
        }
    }

    public void isVisited(Field clicked) {
        //when clicked, reveal underneath and mark as visited in away
        clicked.isClicked();
        visited[clicked.returnX()][clicked.returnY()] = true;
    }

    public void reset(int height, int width, int bombs) {
        //resets arrays and height, re randomizes bombs
        masterW=width;
        masterH=height;
        fields=new Field[height][width];
        visited=new boolean[height][width];
        for(int h=0; h < masterH;h++){
            for (int j=0; j < masterW; j++){
                fields[h][j] = new Field();
                visited[h][j] = false;
            }
        }
        this.bombPlacer(bombs);
        this.bombSurronding();
    }
    private void bombPlacer(int bombs){
        //randomly place bombs, and store location in array of two int arrays
        bombsPlaced =0;
        bombLocations = new int[bombs][2];

        while (bombsPlaced != bombs){
            int w =(int)(Math.random()*masterW);
            int h =(int)(Math.random()*masterH);
            ImageIcon img = new ImageIcon(loader.getResource("res/bomb.png"));
            if(fields[h][w].customName()!="bomb") {
                fields[h][w] = new Field(img,h,w);
                fields[h][w].setCustomName("bomb");
                bombLocations[bombsPlaced][0] = h;
                bombLocations[bombsPlaced][1] = w;
                bombsPlaced++;
            }
        }
    }

    private void bombSurronding(){
        //calculate if a bomb is arround it
        for(int h = 0; h < masterH; h++){
            for(int w = 0; w < masterW; w++){
                int bombsNear=0;
                if (fields[h][w].customName()!="bomb") {
                    int above = h - 1;
                    int below = h + 1;
                    int left = w - 1;
                    int right = w + 1;
                    if(above >=0) {
                        if(left>=0) {
                            if (fields[above][left].customName() == "bomb") {
                                bombsNear++;
                            }
                        }
                        if (fields[above][w].customName() == "bomb") {
                            bombsNear++;
                        }
                        if (right < masterW) {
                            if (fields[above][right].customName() == "bomb") {
                                bombsNear++;
                            }
                        }
                    }
                    if (left >= 0) {
                        if (fields[h][left].customName() == "bomb") {
                            bombsNear++;
                        }
                    }
                    if (right < masterW) {
                        if (fields[h][right].customName() == "bomb") {
                            bombsNear++;
                        }
                    }
                    if (below < masterH) {
                        if (left >=0) {
                            if (fields[below][left].customName() == "bomb") {
                                bombsNear++;
                            }
                        }
                        if (fields[below][w].customName() == "bomb") {
                            bombsNear++;
                        }
                        if (right < masterW) {
                            if (fields[below][right].customName() == "bomb") {
                                bombsNear++;
                            }
                        }
                    }
                    String imgName = "res/Minesweeper_" + bombsNear +".svg.png";

                    ImageIcon img = new ImageIcon(loader.getResource(imgName));
                    fields[h][w] = new Field(img,h,w);
                    fields[h][w].setCustomName(String.valueOf(bombsNear));
                }
            }
        }
    }

    public void addListener(ActionListener AL){
        //add action listner
        for (int h = 0; h < masterH; h++){
            for (int w = 0; w < masterW; w++){
                fields[h][w].addActionListener(AL);
            }
        }
    }

    public boolean hasBeenVisited(int i, int i1) {
        //return if it has been visited
        return visited[i][i1];
    }
    public boolean hasWon(int b){
        //make sure that those not visited is same as number of bombs
        int num=0;
        for (int i = 0; i < masterH;i++){
            for (int j=0; j < masterW;j++){
                if (!visited[i][j]){
                    num++;
                }
            }
        }
        if (num==b){
            return true;
        }
        else{
            return false;
        }
    }
}

