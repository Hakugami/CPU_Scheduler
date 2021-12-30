package CPU;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Test case for AGAT Scheduler
//        Process p1 = new Process("P1", 17, 0, 4, 4);
//        Process p2 = new Process("P2", 6, 3, 9, 3);
//        Process p3 = new Process("P3", 10, 4, 3, 5);
//        Process p4 = new Process("P4", 4, 29, 8, 2);
//        ArrayList<Process> processes1=new ArrayList<>();
//        processes1.add(p1);
//        processes1.add(p2);
//        processes1.add(p3);
//        processes1.add(p4);
//        AGAT agat = new AGAT(processes1);
//        System.out.println(Process.v1);
//        System.out.println("ArrayList");
//        for (Process p : agat.processesList) {
//            System.out.println(p);
//        }
//        System.out.println("upComingQueue");
//        for (Process p : agat.upComingQueue) {
//            System.out.println(p);
//        }
//
//
//        System.out.println("readyQueue");
//        for (Object p : agat.readyQueue) {
//            System.out.println(p);
//        }
//
//        Process best = agat.getBestProcess();
//        agat.runProcess(best);
//
//        System.out.println("headOfQueue");
//        System.out.println(agat.upComingQueue.peek());

        //comment the previous part to add your test case...!!!!!!!!!

        //Priority Scheduler
//        Process p1 = new Process("1", 0 , 4,1);
//        Process p2 = new Process("2", 0 , 3,2);
//        Process p3 = new Process("3", 6 , 7,1);
//        Process p4 = new Process("4", 11, 4,3);
//        Process p5 = new Process("5", 12, 2,2);
//        CPUScheduler scheduler = new PriorityScheduler();
//        scheduler.add(p1);
//        scheduler.add(p2);
//        scheduler.add(p3);
//        scheduler.add(p4);
//        scheduler.add(p5);
//        scheduler.process();
        //SRTF
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
//        //call method process to create schedule data
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
        //SJF
        Process p1 = new Process("p1", 0 , 3);
        Process p2 = new Process("p2", 0 , 4);
        Process p3 = new Process("p3", 0 , 2);
        Process p4 = new Process("p4", 0, 1);
        Process p5 = new Process("p5", 0, 3);
        CPUScheduler scheduler = new SJF();
        scheduler.add(p1);
        scheduler.add(p2);
        scheduler.add(p3);
        scheduler.add(p4);
        scheduler.add(p5);
        scheduler.process();
        SJF.getOrderedProcesses();
        SJF.getTurnaroundTime();
        SJF.getWaitingTime();
    }
}
