package CPU;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SJFComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return Integer.compare(o1.getBurstTime2(),o2.getBurstTime2());
    }
}

public class SJF extends CPUScheduler {
    public static List<Process> orderedProcesses = new ArrayList();
    public int t = 0;
    @Override
    public void process() {
        while (!processes.isEmpty()) {
            processes.sort(new ArrivalTimeComparator());
            List<Process> SJFProcesses = new ArrayList<>();
            for (Process process : processes) {
                if (process.getChangeableArrivalTime() == 0)
                    SJFProcesses.add(process);
                else
                    break;
            }
            for (Process process : SJFProcesses) {
                process.setBurstTime2( process.getBurstTime());
            }
            SJFProcesses.sort(new SJFComparator());
            if (!SJFProcesses.isEmpty()) {
                Process currentProcess = SJFProcesses.get(0);
                currentProcess.setFinishTime(t + currentProcess.getBurstTime());
                currentProcess.setWaitingTime(t - currentProcess.getArrivalTime());
                currentProcess.setTurnaroundTime(currentProcess.getFinishTime() - currentProcess.getArrivalTime());
                this.orderedProcesses.add(currentProcess);
                this.processes.remove(currentProcess);
                SJFProcesses.remove(0);
                for (Process process : processes) {
                    process.setChangeableArrivalTime(process.getChangeableArrivalTime() - currentProcess.getBurstTime());
                    if (process.getChangeableArrivalTime() < 0)
                        process.setChangeableArrivalTime(0);
                }
                t += currentProcess.getBurstTime();

                for (Process process : SJFProcesses){
                    if(process.getBurstTime2()>0)
                        process.setBurstTime2( process.getBurstTime2() - 1 );
                }
            } else {
                for (Process process : processes) {
                    process.setChangeableArrivalTime(process.getChangeableArrivalTime() - 1);

                }
                t++;
            }
        }

    }

    public static void getOrderedProcesses() {
        for (Process process : orderedProcesses){
            System.out.println(process.toString());
        }
    }
    public static void getWaitingTime() {
        for (Process process : orderedProcesses){
            System.out.println(process.getWaitingTime());
        }
    }
    public static void getTurnaroundTime() {
        for (Process process : orderedProcesses){
            System.out.println(process.getTurnaroundTime());
        }
    }


//    public static void main(String[] args){
//        Process p1 = new Process("p1", 0 , 3);
//        Process p2 = new Process("p2", 2 , 5);
//        Process p3 = new Process("p3", 1 , 4);
//        Process p4 = new Process("p4", 4, 2);
//        Process p5 = new Process("p5", 6, 9);
//        Process p6 = new Process("p6", 5, 4);
//        Process p7 = new Process("p7", 7, 10);
//        CPUScheduler scheduler = new SJF();
//        scheduler.add(p1);
//        scheduler.add(p2);
//        scheduler.add(p3);
//        scheduler.add(p4);
//        scheduler.add(p5);
//        scheduler.add(p6);
//        scheduler.add(p7);
//        scheduler.process();
//        getOrderedProcesses();
//    }


}
