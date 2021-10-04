import java.util.*;

public class Better_OS {

    private static LinkedList<Object> sleepQueue;
    private static LinkedList<Object> readyQueue;

    static {
        sleepQueue = new LinkedList<>();
        sleepQueue.addLast((Integer)0);
        readyQueue = new LinkedList<>();
    }

    public static void main(String[] args) {
/*
        //section for testing
        //create jobs
        Random r = new Random();
        for (int i=0; i<10; i++){
            Job temp = new Job();
            temp.sleep((long)r.nextInt(15)+1);
        }

        //this will be scheduled by OS, loop is just for testing functionality.
        for(int i=0;i<1000;i++)
            awake();
*/
    }

    //awake function belongs to OS class
    public static void awake() {
        long sleep = (Integer) sleepQueue.removeLast()+1;
        sleepQueue.addLast(sleep);
        //if an entry has a key equal to sleep, it's time to move it to ready queue
        Object head = sleepQueue.peekFirst();
        if (head ==sleepQueue.peekLast()) {
            sleepQueue.removeLast();
            sleepQueue.addLast((long) 0);
            return;
        }
        else {
            LinkedList<Object> temp = (LinkedList<Object>) head;
            if ((Long) temp.peekFirst()==sleep) {
                sleepQueue.remove(); //removing the list of jobs that finished sleeping
                temp.remove(); //remove the non-job head of the list being the sleep time
                readyQueue.add(temp);
                for (int i=0;i<temp.size();i++) {
                    Job j = (Job) temp.get(i);
                    j.switchState();
                }
            }
        }
    }

    private static class Job {
        public long jobID;
        private boolean ready; //false: sleeping, true: ready

        public Job() {
            jobID = new Random().nextInt(1000);
            ready = true;
        }

        public void switchState() {
            ready = !ready;
            System.out.printf("Job %d %s \n", jobID, !ready ? "blocked" : "ready");
        }

        //sleep function belongs to the Job class
        public void sleep(long ms) {
            long sleep = ms + (Integer)sleepQueue.peekLast();
            int i = 0;
            for (;i<sleepQueue.size()-1;i++){
                LinkedList<Object> curr = (LinkedList<Object>) sleepQueue.get(i);
                if ((Long)curr.getFirst()==sleep) {
                    curr.add(this);
                    this.switchState();
                    return;
                }
                if ((Long)curr.getFirst()>sleep)
                    break;
            }
            LinkedList<Object> temp = new LinkedList<>();
            temp.addFirst(sleep);
            temp.addLast(this);
            sleepQueue.add(i,temp);
            this.switchState();
        }

    }
}
