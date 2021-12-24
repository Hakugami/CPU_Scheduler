package CPU;

public class Process {
    private String processName;
    private int pID;
    private int arrivalTime;
    private int burstTime;
    private int priority;
    private int waitingTime;
    private int turnaroundTime;
    private int finishTime;
    private double AGAT_Factor;
    private int quantum;

    public double getAGAT_Factor() {
        return AGAT_Factor;
    }

    public void setAGAT_Factor(double AGAT_Factor) {
        this.AGAT_Factor = AGAT_Factor;
    }

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

    public Process(String processName, int arrivalTime, int burstTime){
        this(processName, arrivalTime, burstTime, 0, 0, 0);
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
    public String toString(){
        return processName;
    }
}

