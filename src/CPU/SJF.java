package CPU;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class SJFComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return Integer.compare(o1.getBurstTime(),o2.getBurstTime());
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


            } else {
                for (Process process : processes) {
                    process.setChangeableArrivalTime(process.getChangeableArrivalTime() - 1);
                    t++;
                }
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
//        Process p2 = new Process("p2", 0 , 4);
//        Process p3 = new Process("p3", 0 , 2);
//        Process p4 = new Process("p4", 0, 1);
//        Process p5 = new Process("p5", 0, 3);
//        CPUScheduler scheduler = new SJF();
//        scheduler.add(p1);
//        scheduler.add(p2);
//        scheduler.add(p3);
//        scheduler.add(p4);
//        scheduler.add(p5);
//        scheduler.process();
//        getOrderedProcesses();
//
//    }


}
