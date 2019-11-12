/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.aulas.concurrency.safelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author lapaesleme
 */
class Friend {

    private final String name;
    private final Lock lock = new ReentrantLock();

    public Friend(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public boolean impendingBow(Friend bower) {
        Boolean myLock = false;
        Boolean yourLock = false;
        try {
            myLock = lock.tryLock();
            yourLock = bower.lock.tryLock();
        } finally {
            if (!(myLock && yourLock)) {
                if (myLock) lock.unlock();
                if (yourLock) bower.lock.unlock();
            }
        }
        return myLock && yourLock;
    }

    public void bow(Friend bower) {
        if (impendingBow(bower))
            try {
                System.out.format("%s: %s has bowed to me!%n", this.name, bower.getName());
                bower.bowBack(this);
            } finally {
                lock.unlock();
                bower.lock.unlock();
            }
        else
            System.out.format("%s: %s started to bow to me, but saw that I was already bowing to him.%n", this.name, bower.getName());
    }

    public void bowBack(Friend bower) {
        System.out.format("%s: %s has bowed back to me!%n", this.name, bower.getName());
    }

}
