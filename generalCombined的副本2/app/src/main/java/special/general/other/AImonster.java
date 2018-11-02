package special.general.other;

import java.util.LinkedList;

/*  Attention!!!! :  the coordinate point(0,0) is at the left top corner, which is different from other files,
 *                   the coordinate is opposite, which is [Y][X]
 *                   y+1下
 *                   y-1上
 *                   x+1右
 *                   x-1左
 *
 */
public class AImonster {
    int ScreenH;
    int ScreenW;
    int weightI = 0;
    int totalWeight = 0;
    int currentX;                                                   //define the current coordinate X
    int currentY;                                                   //define the current coordinate Y
    int xW=ScreenW/16;
    int xH=ScreenH/10;
    int orderNum = 2;                                               //to differ different candidate solutions
    int currentParent = 2;                                          //to locate current parent solution
    int parentOrderNum;
    int grandParentOrderNum;
    int currentBestWeight = 99999999;                               //current best solution's weight cost
    int state1 = 0;                                                 //state 1: there has been at least one solution, state 0: there has not been a solution
    int currentBestOrderNum;                                        //current best solution's order number
    int[][] taken = {{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                     {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}};//behalf the position has an existed solution with solution and its weight is
    int[][] pointSolution = new int[10][16];
    int[][] route = new int[10][16];
    int[][] weight = new int[10][16];                               //define the harm weight for each coordinate point
    LinkedList<AImonster> routeSolutions = new LinkedList<>();       //define the solution set of routes

    Guaiwu opGuaiwu = new Guaiwu();
//    Canvas canvas1 = new Canvas();
    static AImonster operatorAImonster = new AImonster();
//    AImonster firstrouteCandidate = new AImonster();

    //the damage of each different towers are 1,2,3; 2,4,6; 3,6,9;
    //

    public int getWeight( int y, int x, int[][] route){                             //for a solution and one step
                                                //this method might potentially be used for many other realistic problems after change the weight formula\
        weightI = 0;
        this.ScreenH=1080;//canvas1.getHeight();
        this.ScreenW=1920;//canvas1.getWidth();

        for(int i=x-2;i<=x+2;i++) {
            for(int j=y-3;j<=y+3;j++) {
                if((i>=0)&&(j>=0)&&(i<16)&&(j<10)){
//                   if(length(x*xW,y*xH,i*xW,j*xH)<3*xW){//如果在范围内
                        switch (route[j][i]){
                            case -1: weightI+=1;break;
                            case -11: weightI+=2;break;
                            case -1111: weightI+=3;break;
                            case -2: weightI+=2;break;
                            case -22: weightI+=4;break;
                            case -222: weightI+=6;break;
                            case -3: weightI+=3;break;
                            case -33: weightI+=6;break;
                            case -333: weightI+=9;break;
                        }
//                    }
                }
            }
        }
        if(route[y][x]==-1||route[y][x]==-11||route[y][x]==-111||route[y][x]==-2||route[y][x]==-22||route[y][x]==-222||route[y][x]==-3||route[y][x]==-33||route[y][x]==-333)
            return weightI * 4;
        else
            return weightI;
    }
    public void moving(int currentY, int currentX, AImonster parentSolution , int mapNum){
                /*                   y+1下 2
                 *                   y-1上 4
                 *                   x+1右 1
                 *                   x-1左 3
                 */
        int i = currentX-1; int j = currentY;
        int direction = 3;
        System.out.println("test before moving judge");
        movingJudge(parentSolution,j, i,currentY, currentX, direction, mapNum);
        System.out.println("test after first moving judge");
        i = currentX+1; j = currentY;
        direction = 1;
        movingJudge(parentSolution,j, i,currentY, currentX, direction, mapNum);
        i = currentX; j = currentY-1;
        direction = 4;
        movingJudge(parentSolution,j, i,currentY, currentX, direction, mapNum);
        i = currentX; j = currentY+1;
        direction = 2;
        movingJudge(parentSolution,j, i,currentY, currentX, direction, mapNum);
        System.out.println("solution size : "+ routeSolutions.size());
                routeSolutions.remove(parentSolution);
                System.out.println("delete one");
    }
    public void makeNewSolution(AImonster parentSolution,int j, int i,int currentY, int currentX, int weightTem,int direction, int mapNum){
        AImonster routeCandidate = new AImonster();                 //make a new solution
     //   routeCandidate = parentSolution;      //would point to the same address                      //let new solution = its parent solution first
        int[][] tempMap = new int[10][16];
        for(int m = 0; m < 10; m++){
            for(int n = 0; n < 16; n++){
                routeCandidate.route[m][n] = parentSolution.route[m][n];
            }
        }
        routeCandidate.orderNum = orderNum;                         //give a unique identify num to new solution
        routeCandidate.totalWeight = weightTem;                     //upgrade the new solution's weight
        taken[j][i] = weightTem;                                    //mark the weight on the map
        pointSolution[j][i] = orderNum;                             //mark the best solution for this coordinate
        if (routeCandidate.route[j][i] != -1&&
                routeCandidate.route[j][i] != -11&&
                routeCandidate.route[j][i] != -111&&
                routeCandidate.route[j][i] != -2&&
                routeCandidate.route[j][i] != -22&&
                routeCandidate.route[j][i] != -222&&
                routeCandidate.route[j][i] != -3&&
                routeCandidate.route[j][i] != -33&&
                routeCandidate.route[j][i] != -333
                )
            routeCandidate.route[j][i] = 9;                             //give a undecided initial direction to new solution
        if (routeCandidate.route[currentY][currentX] != -1&&
                routeCandidate.route[currentY][currentX] != -11&&
                routeCandidate.route[currentY][currentX] != -111&&
                routeCandidate.route[currentY][currentX] != -2&&
                routeCandidate.route[currentY][currentX] != -22&&
                routeCandidate.route[currentY][currentX] != -222&&
                routeCandidate.route[currentY][currentX] != -3&&
                routeCandidate.route[currentY][currentX] != -33&&
                routeCandidate.route[currentY][currentX] != -333
                )
        routeCandidate.route[currentY][currentX] = direction;       //due to which direction we changed
        routeCandidate.currentY = j;
        routeCandidate.currentX = i;
        routeCandidate.parentOrderNum = parentSolution.orderNum;
        routeCandidate.grandParentOrderNum = parentSolution.parentOrderNum;
        routeSolutions.add(routeCandidate);                         //add new solution to list
        checkDestination(j,i, mapNum);
        System.out.println("order num:" + orderNum);
        orderNum++;                                                 //add 1 to the unique orderNum
        printLast();
    }
    public void movingJudge(AImonster parentSolution,int j, int i,int currentY, int currentX,int direction, int mapNum){
        if (i>=0&&i<15&&j>0&&j<=9) {
            int weightTem = parentSolution.totalWeight + getWeight(j,i,parentSolution.route);
            if (taken[j][i]!=-1) {                                                      //if there already has a solution passed here
                if(taken[j][i] > weightTem){                                            //if the new solution is better than the solution which already has went passed this position
                    for(int m = 0; m < routeSolutions.size(); m++){
                        if(routeSolutions.get(m).orderNum == pointSolution[j][i]||routeSolutions.get(m).parentOrderNum == pointSolution[j][i]||routeSolutions.get(m).grandParentOrderNum == pointSolution[j][i]){      //这里的问题是如果有一个解作为父代已经被删除了，怎麼办，1.其中一个子代继承父代的ordernumber，2.讲ordernumber改为数组
                            routeSolutions.remove(m);                                   //remove the worse former solution
                        }
                    }
                    makeNewSolution(parentSolution, j, i,currentY,currentX, weightTem, direction, mapNum);
                }else if(taken[j][i] <= weightTem){                                     //if the former solution is better or as good as the new one
                }
            }else if (taken[j][i]==-1){                                                 //if there has not a solution which has been here
                makeNewSolution(parentSolution, j, i,currentY,currentX, weightTem, direction ,mapNum);
            }
        }
    }
    public void checkDestination(int j, int i, int mapNum){
        int yDestination ;
        int xDestination ;
        switch (mapNum){
            case 0:
                yDestination = 8;
                xDestination = 14;
                break;
            case 1:
                yDestination = 5;
                xDestination = 15;
                break;
            case 2:
                yDestination = 6;
                xDestination = 14;
        }
        if(j == 8&& i ==14){
            state1 = 1;
            if(routeSolutions.getLast().totalWeight < currentBestWeight){
                currentBestWeight = routeSolutions.getLast().totalWeight;
                currentBestOrderNum = routeSolutions.getLast().orderNum;
                System.out.println("current best order num: " + currentBestOrderNum);
            }
        }
    }
    public int[][] initialStart(int map[][], int mapNum){
        for(int j = 0; j < 10; j++){
            for(int i = 0; i < 16; i++){
                if(map[j][i] == 1||
                        map[j][i] == 2||
                        map[j][i] == 3||
                        map[j][i] == 4||
                        map[j][i] == 5||
                        map[j][i] == 6||
                        map[j][i] == 7||
                        map[j][i] == 8||
                        map[j][i] == 9)
                    map[j][i]=0;
            }
        }
        int bestNum = 0;
        AImonster firstSolution = new AImonster();
        switch (mapNum){
            case 0:
                firstSolution.currentY = 3;
                firstSolution.currentX = 0;
                break;
            case 1:
                firstSolution.currentY = 1;
                firstSolution.currentX = 0;
                break;
            case 2:
                firstSolution.currentY = 5;
                firstSolution.currentX = 0;
        }
        firstSolution.route = map;
        firstSolution.route[firstSolution.currentY][firstSolution.currentX] = 1;
        firstSolution.orderNum = 1;
        firstSolution.totalWeight = 0;
/*
        firstSolution.route[8][10] = -3;
        firstSolution.route[8][15] = -3;
        firstSolution.route[8][5] = -3;
        firstSolution.route[8][2] = -3;
        firstSolution.route[5][2] = -3;
        firstSolution.route[2][10] = -3;
        firstSolution.route[4][10] = -3;
        firstSolution.route[6][2] = -3;
        */
        routeSolutions.add(firstSolution);
        System.out.println("first solution:");
        for(int j = 0; j < 10; j ++){
            for(int i = 0; i < 16; i++){
                System.out.print(firstSolution.route[j][i]+",, ");
            }
            System.out.println(" ");
        }

        while(getObjectOrderNum() != -1){
            moving(routeSolutions.get(getObjectOrderNum()).currentY,routeSolutions.get(getObjectOrderNum()).currentX,routeSolutions.get(getObjectOrderNum()),mapNum);
            System.out.println("solution size : "+ routeSolutions.size());
//            printFirst();
        }
        System.out.println("best weight:"+ currentBestWeight);
        System.out.println("best OrderNum:"+ currentBestOrderNum);
        for(int k = 0;k < routeSolutions.size();k++){
            if(routeSolutions.get(k).orderNum == currentBestOrderNum){
                bestNum = k;
                for(int j = 0; j < 10; j ++){
                    for(int i = 0; i < 16; i++){
                        System.out.print(routeSolutions.get(k).route[j][i]+" ");
                    }
                    System.out.println(" ");
                }
            }else {
                System.out.println("---------algorithm error--------");
            }
        }
        for(int j = 0; j < 10; j ++){
            for(int i = 0; i < 16; i++){
                map[j][i] =routeSolutions.get(bestNum).route[j][i];
            }
        }
        routeSolutions.clear();
        currentBestWeight = 99999999;
        state1 = 0;
        orderNum = 2;
        for(int j = 0; j < 10; j++){
            for(int i = 0; i< 16; i++){
                taken[j][i] = -1;
                pointSolution[j][i] = 0;
            }
        }
        return map;

    }
    public int getObjectOrderNum(){
        int judgeOrder = -1;                                             //used for getting the object solution for moving
        int judgeWeight = 9999999;                                      //used for getting the object solution for moving
        System.out.println("current best weight:"+currentBestWeight+"   judge weight:" + judgeWeight);

        for(int i = 0; i < routeSolutions.size();i++){
            if ((routeSolutions.get(i).totalWeight < judgeWeight)&&(routeSolutions.get(i).totalWeight < currentBestWeight)){
                    judgeWeight = routeSolutions.get(i).totalWeight;
                    judgeOrder = i;
            }
        }
        System.out.println("getObjectOrderNum:"+judgeOrder);
        return judgeOrder;

    }
    public void checkDominate(AImonster solution){
        int state =0;
        for(int i = 0; i < routeSolutions.size()-1; i++){
            if(solution.currentX == routeSolutions.get(i).currentX && solution.currentY == routeSolutions.get(i).currentY) {
                if (solution.totalWeight >= routeSolutions.get(i).totalWeight){
                    state = 1;
                }else{
                    routeSolutions.remove(i);
                    i--;
                }
            }
        }
    }
    public void printLast(){
        System.out.println("the last solution weight:" + routeSolutions.getLast().totalWeight);
        for(int j = 0; j < 10; j ++){
            for(int i = 0; i < 16; i++){
                System.out.print(routeSolutions.getLast().route[j][i]+" ");
            }
            System.out.println(" ");
        }
    }
    public void printFirst(){
        System.out.println("the first solution weight:" + routeSolutions.getFirst().totalWeight);
        for(int j = 0; j < 10; j ++){
            for(int i = 0; i < 16; i++){
                System.out.print(routeSolutions.getFirst().route[j][i]+" ");
            }
            System.out.println(" ");
        }
    }
    public static void main(String args[]){
        int[][] mapAI = new int[10][16];
        operatorAImonster.initialStart(mapAI,1);


    }
}
