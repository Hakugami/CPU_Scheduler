package CPU;

import java.util.Scanner;
class SRTF extends CPUScheduler {
    
    @Override
    public void process() {
        order=new String[getTotalBurstTime()];
        int size= processes.size();
        int remingT_Arr[] = new int[size];
        int complete = 0;
        int currentTime = 0;
        int minm = Integer.MAX_VALUE;
        int shortest = 0;
        int finishTime;
        int countEnterProcess=0;//for order
        int wt;
        int tat;
        int numOfProcessInRun=3; //max process os can see
        int countCompletedProcess=numOfProcessInRun;
        int reminaingAfterDived=size%numOfProcessInRun;
        int forItreation=size/numOfProcessInRun;
        int trueTime=0;
        boolean found = false;
        
                // copy burst time into remingT_Arr[] and find total burst time of all processses
        for (int i = 0; i < size; i++)
            remingT_Arr[i] = processes.get(i).getBurstTime();
        
        if(reminaingAfterDived!=0&&forItreation==0){
            countCompletedProcess=reminaingAfterDived;
            reminaingAfterDived=0;
            forItreation++;}
        for(int i=0;i<forItreation;i++){
        while (complete != countCompletedProcess) {
            //find process have min remaining time 
            for (int j = 0; j < countCompletedProcess; j++)
            {
                if ((processes.get(j).getArrivalTime() <= currentTime) && (remingT_Arr[j]< minm) && remingT_Arr[j] > 0) {
                    minm = remingT_Arr[j];//min burst time
                    shortest = j;//short burst time index
                    found = true; 
                }
            }
      
            if (found == false) {
                currentTime++;
                trueTime++;
                continue;
            }
            order[countEnterProcess]=processes.get(shortest).getProcessName();
                    countEnterProcess++;
                    
            remingT_Arr[shortest]--;
            
            // set minm
            minm = remingT_Arr[shortest];
            
            if (minm == 0)
                minm = Integer.MAX_VALUE;//process complete
 
            
            if (remingT_Arr[shortest] == 0) {
                complete++;//process executed
                found = false;
                
                finishTime = trueTime + 1;
                processes.get(shortest).setFinishTime(finishTime);
                // Calculate waiting time for each process
                wt= finishTime -processes.get(shortest).getBurstTime() -processes.get(shortest).getArrivalTime();
      
                if (wt < 0)
                    wt = 0;
                // Calculate turn around time for each process
                tat = processes.get(shortest).getBurstTime()+ wt;
                processes.get(shortest).setWaitingTime(wt);
                processes.get(shortest).setTurnaroundTime(tat);
                
            }
            currentTime++;
            trueTime++;
        }
        
        //check finall iteration if remaining!=0
        if(reminaingAfterDived!=0&&forItreation-i==1){
            currentTime=processes.get(countCompletedProcess).getArrivalTime();
            countCompletedProcess+=reminaingAfterDived;
            reminaingAfterDived=0;
            forItreation++;}
        else {
            if(size>countCompletedProcess)
                currentTime=processes.get(countCompletedProcess).getArrivalTime();
            countCompletedProcess+=numOfProcessInRun;
        }
        
            
        }
    }

}
 class Main
{

public static void main(String[] args)
    {
//        CPUScheduler s=new SRTF();
//        Scanner input = new Scanner(System.in);
//        System.out.print("Enter number of process:");
//        int pNum=input.nextInt();
//        //take processes info from user
//        for (int i=0;i<pNum;i++){
//            System.out.print("Enter process "+(i+1)+" Name:");
//            String pName=input.next();
//            System.out.print("Enter process "+(i+1)+" Arrival Time:");
//            int arrival=input.nextInt();
//            System.out.print("Enter process "+(i+1)+" Burst Time:");
//            int burstTime=input.nextInt();
//
//            Process p=new Process(pName,arrival, burstTime);
//            s.add(p);
//        }
//        //call method proces to create schdule data
//        s.process();
//        //call method print order to print order of processes
//        s.printOrder();
//
//        System.out.println("\nProcesses " +" Burst time " +" Waiting time " +" Turn around time");
//
//        //print each process data
//        for (int i = 0; i < s.getProcesses().size(); i++) {
//            System.out.println(" " + s.getProcesses().get(i).getProcessName() + "\t\t"+ s.getProcesses().get(i).getBurstTime() + "\t\t " + s.getProcesses().get(i).getWaitingTime()+ "\t\t" + s.getProcesses().get(i).getTurnaroundTime());
//        }
//        System.out.println("Average waiting time = " +s.getAverageWaitingTime());
//        System.out.println("Average turn around time = " +s.getAverageTurnAroundTime());
        //**************************************************************************************************
        // _name, _color, _arrivalTime, _burstTime, _priorityNumber, _quantom

        System.out.println("\nAG Scheduling:");
        AGAT agScheduling = new AGAT();
        agScheduling.processes.add(new Process("P1",  0, 17, 4, 4));
        agScheduling.processes.add(new Process("P2",  3,  6, 9, 3));
        agScheduling.processes.add(new Process("P3", 4, 10, 3, 5));
        agScheduling.processes.add(new Process("P4",  29, 4, 8, 2));
        agScheduling.process();

    }
}