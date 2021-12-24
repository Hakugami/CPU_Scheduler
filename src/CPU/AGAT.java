package CPU;


import java.util.Queue;
import java.lang.reflect.Array;


public class AGAT extends  CPUScheduler {
    protected Queue<Process> readyQueue;
    public float calculate_v1() {
        float v1;
        int size =processes.size();
        int res=processes.get(size).getArrivalTime();
        if (res > 10) {
            v1 = res/10;
        } else {
            v1 = 1;
        }
        return  v1;
    }
    public float calculate_v2() {
        float v2;
        int res=0;
        int n= processes.size();
        int reming_burst[] = new int[n];
        for (int i = 0; i < n; i++){
            reming_burst[i] = processes.get(i).getBurstTime();
        }
        int mx=reming_burst[0];
        for(int i=0;i<n;i++){

            res=Math.max(mx,reming_burst[i]);
        }
        if (res > 10) {
            v2 = res/10;
        } else {
            v2 = 1;
        }
        return  v2;
    }
    public void agatFactor(int priority,int arrivalTime,int burstTime){

       float v1=calculate_v1();
       float v2=calculate_v2();
        for (Process process : processes) {
            process.setAGAT_Factor((10 - priority) + Math.ceil(arrivalTime / v1) + Math.ceil(burstTime / v2)); //placeholder
            //not useful
        }
    }

    @Override
    public void process() {

    }
}
