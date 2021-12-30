package CPU;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class AGAT extends CPUScheduler {

    public int currentTime = 0;
    ArrayList<Process> processesList = new ArrayList<>();
    PriorityQueue<Process> readyQueue = new PriorityQueue<>(new Process.readyQueueProcessComparator());
    PriorityQueue<Process> upComingQueue = new PriorityQueue<>(new Process.upComingQueueProcessComparator());

    AGAT(ArrayList<Process> processes) {
        processesList = processes;
        calcV1();
        calcCeil1();
        calcV2();
        calcCeil2();
        calcAgatFactor();
        //copyListToQueue(processesList, readyQueue);
        copyListToQueue(processesList, upComingQueue);
    }

    // ToDo check on update after each add to readyQueue
    public void runProcess(Process process) {
        Process runningProcess = process;
        Process nextProcess = readyQueue.peek();
        while (runningProcess != null) {
            while (runningProcess.serviceTime < runningProcess.nonPreemitiveTime) {
                System.out.println("time " + currentTime + ": " + runningProcess.processName +" is running in its Non-Preemitive Time");
                currentTime++;
                runningProcess.serviceTime++;
                runningProcess.burstTime--;
            }
            while (true) {
                addReadyProcessesToReadyQueue();
                updateProcessesValues();
                // the process has finished it's job and leaving readyQueue
                if (runningProcess.burstTime <= 0) {
                    System.out.println("time " + currentTime + ": " + runningProcess.processName + " finshed and leaving");
                    runningProcess.isFinshed = true;
                    runningProcess = readyQueue.poll();
                    break;
                }
                // it's quantum is over
                if (runningProcess.serviceTime >= runningProcess.quantum) {
                    System.out.println("time " + currentTime + ": " +
                            runningProcess.processName + " quantum is over and is going to ready queue");
                    runningProcess.quantum += 2;
                    runningProcess.serviceTime = 0;
                    nextProcess = readyQueue.poll();
                    readyQueue.add(runningProcess);
                    runningProcess = nextProcess;
                    break;
                }
                // another process in ready queue must enter instead it
                if (!readyQueue.isEmpty() && readyQueue.peek().AGAT_Factor < runningProcess.AGAT_Factor) {
                    System.out.println("time " + currentTime + ": " +
                            runningProcess.processName + " going to ready queue as another process has to enter instead");
                    runningProcess.quantum += runningProcess.quantum - runningProcess.serviceTime;
                    runningProcess.serviceTime = 0;
                    nextProcess = readyQueue.poll();
                    readyQueue.add(runningProcess);
                    runningProcess = nextProcess;
                    break;
                } else {
                    System.out.println("time " + currentTime + ": " + runningProcess.processName +" is running in its Preemptive Time");
                    currentTime++;
                    runningProcess.serviceTime++;
                    runningProcess.burstTime--;
                }
            }
        }
    }
    /*
        public void runProcess(Process process) {
            Process runningProcess = process;
            Process nextProcess = readyQueue.peek();
            while (runningProcess.serviceTime < runningProcess.nonPreemitiveTime) {
                currentTime++;
                runningProcess.serviceTime++;
                runningProcess.burstTime--;
            }
            while (runningProcess.serviceTime < runningProcess.quantum && runningProcess.burstTime > 0) {
                addReadyProcessesToReadyQueue();
                updateProcessesValues();
                if (!readyQueue.isEmpty() && readyQueue.peek().AGAT_Factor < runningProcess.AGAT_Factor) {
                    runningProcess.quantum += runningProcess.quantum - runningProcess.serviceTime;
                    runningProcess.serviceTime = 0;
                    nextProcess = readyQueue.poll();
                    readyQueue.add(runningProcess);
                    runProcess(nextProcess);
                } else {
                    currentTime++;
                    runningProcess.serviceTime++;
                    runningProcess.burstTime--;
                }
            }

            if (runningProcess.burstTime > 0) {
                runningProcess.quantum += 2;
                runningProcess.serviceTime = 0;
                nextProcess = readyQueue.poll();
                readyQueue.add(runningProcess);
                runProcess(nextProcess);
            } else {
                runProcess(readyQueue.poll());
            }

        }
    */
    public Process getBestProcess() {
        addReadyProcessesToReadyQueue();
        updateProcessesValues();
        return readyQueue.poll();
    }

    public void addReadyProcessesToReadyQueue() {
        if (upComingQueue.isEmpty()) {
            return;
        }
        while (true) {
            if (!upComingQueue.isEmpty() && currentTime >= upComingQueue.peek().arrivalTime) {
                readyQueue.add(upComingQueue.poll());
            } else {
                break;
            }
        }
    }

    public void calcV1() {
        int maxArrivalTime = processesList.get(0).arrivalTime;
        for (int i = 1; i < processesList.size(); i++) {
            int processArrivalTime = processesList.get(i).arrivalTime;
            maxArrivalTime = Math.max(processArrivalTime, maxArrivalTime);
        }

        Process.v1 = maxArrivalTime > 10 ? maxArrivalTime / 10F : 1;
    }

    public void calcCeil1() {
        for (Process currentProcess : processesList) {
            currentProcess.ceil1 = (int) Math.ceil(currentProcess.arrivalTime / Process.v1);
        }
    }

    public void calcV2() {
        int maxBurstTime = processesList.get(0).burstTime;
        for (int i = 1; i < processesList.size(); i++) {
            int processBurstTime = processesList.get(i).burstTime;
            maxBurstTime = Math.max(processBurstTime, maxBurstTime);
        }
        Process.v2 = maxBurstTime > 10 ? maxBurstTime / 10F : 1;
    }

    public void calcCeil2() {
        for (Process currentProcess : processesList) {
            currentProcess.ceil2 = (int) Math.ceil(currentProcess.burstTime / Process.v2);
        }
    }

    public void calcAgatFactor() {
        for (Process currentProcess : processesList) {
            currentProcess.AGAT_Factor = (10 - currentProcess.priority) + currentProcess.ceil1 + currentProcess.ceil2;
        }
    }

    public void updatePreemptive_NonPreemptiveTime() {
        for (Process currentProcess : processesList) {
            currentProcess.setPreemitive_NonPreemitiveTime();
        }
    }

    public void updateProcessesValues() {
        calcV2();
        calcCeil2();
        calcAgatFactor();
        updatePreemptive_NonPreemptiveTime();
    }

    public void copyListToQueue(ArrayList<Process> processesList, PriorityQueue<Process> Queue) {
        for (Process p : processesList) {
            Queue.add(p);
        }
    }

    @Override
    public void process() {

    }
}
