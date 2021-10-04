import java.util.LinkedList;
import java.util.Random;

public class OS_No_Counters {

        private static LinkedList<Object> sleepQueue;
        private static LinkedList<Job> readyQueue;

        static {
            sleepQueue = new LinkedList<>();
            readyQueue = new LinkedList<>();
        }

        public static void main(String[] args) {

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

        }

        //awake function belongs to OS class
        public static void awake() {
            //on every iteration we take one element off of sleep queue
            //jobs with sleep(i) were added to the i-1 position so they will be removed on iteration i (0 indexed)
            LinkedList<Job> jobs = (LinkedList<Job>)sleepQueue.remove();
            if(jobs==null) return;
            readyQueue.addAll((LinkedList<Job>)jobs);
            for (Object j : jobs) {
                ((Job) j).switchState();
            }
        }

        private static class Job {
            public int jobID;
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
                //validation of input
                if (ms <= 0) return;

                this.switchState();
                long size = sleepQueue.size();

                //if there's not enough room in the queue to insert job
                //we add empty lists of jobs and finally add this job to the correct list.
                if (size-ms<0) {
                    long i = size;
                    for (; i <ms; i++) {
                        sleepQueue.addLast(new LinkedList<Job>());
                    }
                }
                LinkedList<Job> temp = (LinkedList<Job>)sleepQueue.get((int)ms-1);
                temp.add(this);

            }

        }
}
