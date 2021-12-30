package CPU;

import java.util.Comparator;

public class Process implements Comparable<Process> {
    protected String processName;
    protected int pID;
    protected int arrivalTime;
    protected int burstTime;
    protected int priority;
    protected int waitingTime;
    protected int turnaroundTime;
    protected int finishTime;
    protected double AGAT_Factor;
    protected int quantum;
    protected int remainingTime;
    protected int serviceTime;
    int nonPreemitiveTime;
    int preemitiveTime;
    public static float v1;
    public static float v2;
    public int ceil1;
    public int ceil2;
    public boolean isFinshed = false;

    public Process(String processName, int arrivalTime, int burstTime, int priority, int waitingTime, int turnaroundTime) {
            this.processName = processName;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.priority = priority;
            this.waitingTime = waitingTime;
            this.turnaroundTime = turnaroundTime;
            this.changeableArrivalTime = arrivalTime;
        }


        public Process(String processName, int arrivalTime, int burstTime, int priority)
        {
            this(processName, arrivalTime, burstTime, priority, 0, 0);
        }
    public Process(String processName, int burstTime, int arrivalTime, int priority, int quantum) {
        this.processName = processName;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantum = quantum;
        serviceTime = 0;
        setPreemitive_NonPreemitiveTime();

    }

    public void setPreemitive_NonPreemitiveTime(){
        nonPreemitiveTime = (int) Math.round(0.4 * quantum);
        preemitiveTime = quantum - nonPreemitiveTime;
    }
    class readyQueueProcessComparator implements Comparator<Process>{

        @Override
        public int compare(Process o1, Process o2) {
            return (int) (o1.AGAT_Factor - o2.AGAT_Factor);
        }

    }

    static class upComingQueueProcessComparator implements Comparator<Process>{

        @Override
        public int compare(Process o1, Process o2) {
            return o1.arrivalTime - o2.arrivalTime;
        }

    }

    public Process(String processName, int arrivalTime, int burstTime){
        this(processName, arrivalTime, burstTime, 0, 0, 0);
    }


    public int getQuantum() {
        return quantum;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public double getAGAT_Factor() {
        return AGAT_Factor;
    }

    public void setAGAT_Factor(double AGAT_Factor) {
        this.AGAT_Factor = AGAT_Factor;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public void setBurstTime(int burstTime){
        this.burstTime = burstTime;
    }
    public void setPriority(int priority){
        this.priority = priority;
    }
    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }
    public void setTurnaroundTime(int turnaroundTime){
        this.turnaroundTime = turnaroundTime;
    }
    public void setFinishTime(int finishTime){
        this.finishTime = finishTime;
    }
    public String getProcessName(){
        return this.processName;
    }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getBurstTime() {
        return this.burstTime;
    }
    public int getPriority(){
        return this.priority;
    }
    public int getWaitingTime(){
        return this.waitingTime;
    }
    public int getTurnaroundTime(){
        return this.turnaroundTime;
    }
    public int getFinishTime(){
        return this.finishTime;
    }

    // utility attribute to change in the schedule
    private int changeableArrivalTime = arrivalTime;
    public int getChangeableArrivalTime() {
        return changeableArrivalTime;
    }
    public void setChangeableArrivalTime(int changeableArrivalTime) {
        this.changeableArrivalTime = changeableArrivalTime;
    }


    @Override
    public String toString() {
        return "Process{" +
                "processName='" + processName + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", waitingTime=" + waitingTime +
                ", turnaroundTime=" + turnaroundTime +
                '}'+'\n';
    }

    //ascending order sorting
    @Override
    public int compareTo(Process o) {
        return this.arrivalTime - o.arrivalTime;
    }
}


