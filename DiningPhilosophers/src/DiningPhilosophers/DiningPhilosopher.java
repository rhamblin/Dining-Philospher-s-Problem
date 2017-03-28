package lab9;

/**
 *
 * @author rhamblin
 */
public class DiningPhilosopher {

    public final static int N = 5;
 
    private static phil_state[] state = new phil_state[N];
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore[] s = new Semaphore[N]; /* one per philosopher, all 0 */
    
    public enum phil_state {
        THINKING, HUNGRY, EATING
    }

    public DiningPhilosopher() {
        for(int i=0; i< s.length; i++)
            s[i] = new Semaphore(0);
        
        start();
    }
    
    private void start() {
       
        Thread[] philosophers_thread = new Thread[N]; //create threads for each philosopher
        Philosopher[] philosophers = new Philosopher[N]; //create philosophers
        
        //on each thread we will run the philosophers process of thinking and eating
        for(int i=0; i< N; i++) {
            philosophers[i] = new Philosopher(state,mutex,s,i);
        
            final int index = i;
            
            philosophers_thread[i] = new Thread( new Runnable() {
                @Override
                public void run() {
                    philosophers[index].startProcess();
                }
            });
            
            philosophers_thread[i].start();
       
        }    
 
    }
}