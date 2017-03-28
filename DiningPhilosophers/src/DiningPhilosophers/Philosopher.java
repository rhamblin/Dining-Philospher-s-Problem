
package lab9;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lab9.DiningPhilosopher.N;

/**
 *
 * @author rhamblin
 */
public class Philosopher {
    
    private DiningPhilosopher.phil_state[] state;
    private Semaphore mutex;
    private Semaphore[] s ;
    private int process;

    public Philosopher(DiningPhilosopher.phil_state[] state, Semaphore mutex, Semaphore[] s, int process) {
        this.state = state;
        this.mutex = mutex;
        this.s = s;
        this.process = process;
    }

    private final static int RIGHT(int i) {
        return (i + 1) % N;
    }
    private final static int LEFT(int i) {
        return ((i) == 0) ? N - 1 : (i) - 1;
    }

    void get_forks(int i) {
        state[i] = DiningPhilosopher.phil_state.HUNGRY;
        while (state[i] == DiningPhilosopher.phil_state.HUNGRY) {
            mutex.down();
            if (state[i] == DiningPhilosopher.phil_state.HUNGRY && state[LEFT(i)] != DiningPhilosopher.phil_state.EATING
                    && state[RIGHT(i)] != DiningPhilosopher.phil_state.EATING) {
                state[i] = DiningPhilosopher.phil_state.EATING;
                s[i].up();
            }
            mutex.up();
            s[i].down();
        }
    }

    void put_forks(int i) {
        mutex.down();
        state[i] = DiningPhilosopher.phil_state.THINKING;
        if (state[LEFT(i)] == DiningPhilosopher.phil_state.HUNGRY) {
            s[LEFT(i)].up();
        }
        if (state[RIGHT(i)] == DiningPhilosopher.phil_state.HUNGRY) {
            s[RIGHT(i)].up();
        }
        mutex.up();
    }

    public void startProcess() {
        while (true) {
            think(process);
            get_forks(process);
            eat(process);
            put_forks(process);
        }
    }

    private void think(int i) {
        System.out.println("\tPhilosopher " + i + " is Thinking....");
        try {
            Thread.sleep(new Random().nextInt(1000)+100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("\tPhilosopher " + i + " is finished Thinking.");

    }

    private void eat(int i) {
        System.out.println("Philosopher " + i + " is Eating....");
        try {
            Thread.sleep(new Random().nextInt(1000)+100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Philosopher.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Philosopher " + i + " is finished Eating.");

    }

}
