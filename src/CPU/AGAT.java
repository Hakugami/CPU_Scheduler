package CPU;


import java.util.*;


public class AGAT extends  CPUScheduler {
    protected Queue<Process> readyQueue=new LinkedList<Process>();
    private ArrayList<Process> outputTest = new ArrayList<Process>();
    private ArrayList<Process> processes2 = new ArrayList<Process>();
    public float calculate_v1() {
        float v1;
        int res=processes.get(processes.size()-1).getArrivalTime();
        if (res > 10) {
            v1 = res/10F;
        } else {
            v1 = 1;
        }
        return  v1;
    }
    public float calculate_v2() {
        float v2;
        int res=0;
        int n= processes.size();
        Vector<Integer> remainingBursts = new Vector<>();
        for (Process process : processes) {
            remainingBursts.add(process.getRemainingTime());
        }
        int mx=remainingBursts.get(0);
        for(int i=0;i<n;i++){
            res=Math.max(mx,remainingBursts.get(i));
//            System.out.println("mx: "+mx+"---------remainingBursts: "+remainingBursts.get(i));
        }
        if (res > 10) {
            v2 = res/10F;
        } else {
            v2 = 1;
        }
        return  v2;
    }
    public double agatFactor(int priority, int arrivalTime, int burstTime){

       float v1=calculate_v1();
       float v2=calculate_v2();
//        System.out.println("V2: "+v2);
        double result= ((10 - priority) + Math.ceil(arrivalTime / v1) + Math.ceil(burstTime / v2));
        System.out.println("Factor: "+result+" ----------------V2: "+v2);
        return result;
    }

    public boolean isFinished()
    {
        for (Process process : processes) {
            if (process.getQuantum() != 0)
                return false;
        }

        return true;
    }
    public Process getLeastAG(int _time)
    {
        double minAG = Integer.MAX_VALUE;
        int index = 0;

        int i = 0;
        while( (i < this.processes.size()) && (this.processes.get(i).getArrivalTime() <= _time) )
        {
            if(this.processes.get(i).getAGAT_Factor() < minAG && this.processes.get(i).getQuantum() != 0)
            {
                minAG = this.processes.get(i).getAGAT_Factor();
                index = i;
            }
            i++;
        }

        return this.processes.get(index);
    }
    public int getProcessIndex(Process process)
    {
        for(int i = 0; i < processes.size(); i++)
        {
            if(processes.get(i).getProcessName().equals(process.getProcessName()))
                return i;
        }
        return -1;
    }

    public boolean isLastProcess(Process _p, int _time)
    {
        int index = this.getProcessIndex(_p);
        boolean flag = true;

        if(index == this.processes.size() - 1)
            return true;

        index++;

        while((index < this.processes.size()) && this.processes.get(index).getArrivalTime() <= _time)
        {
            if (this.processes.get(index).getAGAT_Factor() != 0) {
                flag = false;
                break;
            }
            index++;
        }

        return flag;
    }

    public Process getBestProcess(int _time, int _preIndex)
    {
        Process p = processes.get(_preIndex);



        if(this.isLastProcess(p, _time) && readyQueue.size() > 0)
        {
            Process temp = readyQueue.poll();

            while(true)
            {
                assert temp != null;
                if(temp.getRemainingTime() != 0)
                {
                    return temp;
                }

                temp = readyQueue.poll();
            }

        }
        else
        {
            return getLeastAG(_time);
        }
    }
    public void setProcesses()
    {
        // Set Remaining Time for all processes (Remaining Time = burstTime), AG-Factor
        for (Process process : this.processes) {
            // Set Remaining Time
            process.setRemainingTime(process.getBurstTime());

            // Set AG-Factor
            process.setAGAT_Factor(agatFactor( process.getPriority() , process.getArrivalTime() , process.getBurstTime()));
        }
    }
    public void setProcessTime()
    {
        for (Process process : this.processes) {
            // Set Remaining Time
//            process.setRemainingTime(process.getBurstTime());

            // Set AG-Factor
            //check arrival time .......................................
            if(process.getRemainingTime()!=0){
                System.out.println( process.getProcessName()+"  Arrival: "+process.getArrivalTime()+" -=--=-=-=-=-==-Remaining time: "+process.getRemainingTime());
               process.setAGAT_Factor(agatFactor( process.getPriority() , process.getArrivalTime() , process.getRemainingTime()));
               continue;
            }
            process.setAGAT_Factor(0);
        }
    }
    public int nonPreemptiveAG(Process _p)
    {
        int nonPreemptiveAGTime = (int) Math.ceil(_p.getQuantum() * 0.4);

        return Math.min(nonPreemptiveAGTime, _p.getRemainingTime());

    }
    public int preemptiveAG(Process _p, int _time, int _executingQuantum)
    {
        int timeForThisProcess = 0;


        while(_p.getRemainingTime() > 0 )
        {
            Process p = this.getLeastAG(_time);

            if(!Objects.equals(p.getProcessName(), _p.getProcessName()))
                break;


            _p.setRemainingTime(_p.getRemainingTime()-1);
            _time++;
            timeForThisProcess++;

            if(_p.getQuantum() == timeForThisProcess + _executingQuantum)
                break;


        }

        return timeForThisProcess;
    }
    public int getceilOfMeanQuantum()
    {
        double sum = 0.0;
        int n = 0;

        for (Process p : processes)
        {
            sum += p.getQuantum();

            if(p.getQuantum() != 0)
                ++n;
        }

        return (int) Math.ceil((sum/n) * 0.1);

    }
    public void printResults()
    {
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;


        for(Process p: outputTest)
        {
            // Set Turnaround Time
            p.setTurnaroundTime(p.getWaitingTime() + p.getBurstTime());

            System.out.println(p);
        }

        for(Process p: processes2)
        {

            totalTurnaroundTime += p.getTurnaroundTime();
            totalWaitingTime += p.getWaitingTime();
        }

        System.out.println("AVG - Turnaround Time: " + (double) totalTurnaroundTime/processes2.size());
        System.out.println("AVG - Waiting Time: " + (double) totalWaitingTime/processes2.size());
    }


    @Override
    public void process() {




        // Sort processes (arrival time - ascending order)
        Collections.sort(this.processes);

        // Set Remaining Time for all processes (Remaining Time = burstTime), AG-Factor
        this.setProcesses();

        int preIndex = -1;
        Process current = null;
        int currentIndex;
        int time=0;
        Process previous=null;

        for( time  = this.processes.get(0).getArrivalTime(); !this.isFinished(); )
        {


            if(preIndex == -1)
            {
                current = this.processes.get(0);
                currentIndex = 0;
            }
            else
            {
                System.out.println("----------------------------------------");
                 setProcessTime();
                System.out.println("----------------------------------------");
                current = this.getBestProcess(time, preIndex);
                currentIndex = this.getProcessIndex(current);

            }
            if (current==previous){
                readyQueue.add(processes.get(currentIndex));
                preIndex = this.getProcessIndex(current)+1;
                continue;
            }
//                processes.get(currentIndex).setAGAT_Factor(agatFactor( processes.get(currentIndex).getPriority() , processes.get(currentIndex).getArrivalTime()
//                        , processes.get(currentIndex).getRemainingTime()));

            if(this.processes.get(currentIndex).quantum != 0)
            {
                // Set Service Time
                this.processes.get(currentIndex).setServiceTime(time);
            }


            int nonPreemptiveAGTime = this.nonPreemptiveAG(current);
            time += nonPreemptiveAGTime;



            this.processes.get(currentIndex).remainingTime -= nonPreemptiveAGTime;


            int preemptiveAGTime = this.preemptiveAG(current, time, nonPreemptiveAGTime);
            time += preemptiveAGTime;

            // Update Quantum
            if(current.remainingTime == 0) {
                this.processes2.add(current);
                // The running process finished its job
                this.processes.get(currentIndex).setQuantum(0);
            }
            else if((nonPreemptiveAGTime + preemptiveAGTime) == current.quantum)
            {
                // The running process used all its quantum time
                this.processes.get(currentIndex).quantum += getceilOfMeanQuantum();
                readyQueue.add(current);
            }
            else
            {
                int total = (this.processes.get(currentIndex).quantum - (nonPreemptiveAGTime + preemptiveAGTime));
                this.processes.get(currentIndex).quantum += total;

                readyQueue.add(current);
            }





            // Set waiting Time
            int wT = this.processes.get(currentIndex).getServiceTime() - this.processes.get(currentIndex).arrivalTime;
            this.processes.get(currentIndex).waitingTime += wT;
            this.processes.get(currentIndex).arrivalTime = time;


            preIndex = this.getProcessIndex(current);

//            System.out.println(processes.get(currentIndex).getProcessName()+" Quantum: "+processes.get(currentIndex).getQuantum());

            outputTest.add(current);
            previous=current;


        }

        // Print Results with AVG
        this.printResults();
    }
}
