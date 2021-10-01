import java.util.*;

public class OS {

    //data structures
    static ArrayList<Job> readyQueue; //ArrayList allow inserts in O(1)
    static HashMap<Long,Integer> sleepQueue; //HashMap allow inserts, search and remove in O(1)
    static Map<Integer,Job> jobDict = new HashMap<>();

    public static void main(String[] args) {
        readyQueue = new ArrayList<>();
        sleepQueue = new HashMap<>();
        //the entry below will be updated on every execution of wake
        sleepQueue.put(null,1);

        /*
        //section for testing
        //create jobs
        for (int i=0; i<10; i++){
            Job temp = new Job();
            jobs.put(temp.jobID,temp);
        }

        //activate sleep
        Random r = new Random();
        for (Job j: jobs.values()){
            j.sleep((long)r.nextInt(1000)); //sleep up to 3 seconds
        }

        for(int i=0;i<1000;i++)
            awake();
        */
    }
    //awake function belongs to OS class
    public static void awake(){
        if (sleepQueue.size()==1){
            sleepQueue.put(null,1);
            return;
        }
        //if an entry has a key equal to n, it's time to move it to ready queue
        int n = sleepQueue.get(null);         //O(1)
        sleepQueue.put(null,n+1);             //O(1)
        if (!sleepQueue.containsKey((long)n)) //O(1)
            return;
        int jobID = sleepQueue.remove((long)n);//O(1)
        Job job = jobDict.get(jobID);             //O(1)
        readyQueue.add(job);                   //O(1)
        job.switchState();                     //O(1)
    } // 7 x O(1)) = O(1)

    private static class Job {
        public int jobID;
        private int state; //0: sleeping, 1: ready

        public Job(){
            jobID = new Random().nextInt(1000);
            state = 1;
        }

        public void switchState(){
            state = state == 0 ? 1:0;
            String output = state == 0 ? "Job "+jobID+" blocked":"Job "+jobID+" ready";
            System.out.println(output);
        }

        //sleep function belongs to the Job class
        public void sleep(Long ms){
            long key = sleepQueue.get(null) + ms;
            sleepQueue.put(key,this.jobID);
            this.switchState();
        }


    }
}
