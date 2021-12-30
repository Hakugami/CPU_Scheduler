package CPU;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        //Test case for AGAT Scheduler
        /*Process p1 = new Process("P1", 17, 0, 4, 4);
        Process p2 = new Process("P2", 6, 3, 9, 3);
        Process p3 = new Process("P3", 10, 4, 3, 5);
        Process p4 = new Process("P4", 4, 29, 8, 2);
        ArrayList<Process> processes1=new ArrayList<>();
        processes1.add(p1);
        processes1.add(p2);
        processes1.add(p3);
        processes1.add(p4);
        AGAT agat = new AGAT(processes1);
        System.out.println(Process.v1);
        System.out.println("ArrayList");
        for (Process p : agat.processesList) {
            System.out.println(p);
        }
        System.out.println("upComingQueue");
        for (Process p : agat.upComingQueue) {
            System.out.println(p);
        }


        System.out.println("readyQueue");
        for (Object p : agat.readyQueue) {
            System.out.println(p);
        }

        Process best = agat.getBestProcess();
        agat.runProcess(best);

        System.out.println("headOfQueue");
        System.out.println(agat.upComingQueue.peek());*/

        //comment the previous part to add your test case...!!!!!!!!!
        Process p1 = new Process("1", 0 , 4,1);
        Process p2 = new Process("2", 0 , 3,2);
        Process p3 = new Process("3", 6 , 7,1);
        Process p4 = new Process("4", 11, 4,3);
        Process p5 = new Process("5", 12, 2,2);
        CPUScheduler scheduler = new PriorityScheduler();
        scheduler.add(p1);
        scheduler.add(p2);
        scheduler.add(p3);
        scheduler.add(p4);
        scheduler.add(p5);
        scheduler.process();
    }
}
