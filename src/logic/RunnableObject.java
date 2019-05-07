/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 *
 * @author YANIXMRML
 */
public class RunnableObject implements Runnable
{
    private Lock lockObject; // application lock; passed in to constructor
    private Condition suspend; // used to suspend and resume thread
    private boolean suspended = false; // true if thread suspended
    private Graphics graphicsProcess;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel progressLabel;
    private int delay = 1500;
    
    private javax.swing.JTextArea textArea; // JLabel for output
    private javax.swing.JScrollPane scroll;

    public RunnableObject(Lock theLock, javax.swing.JTextArea textArea, javax.swing.JScrollPane scroll) {
        lockObject = theLock; // store the Lock for the application
        suspend = lockObject.newCondition(); // create new Condition
        this.textArea = textArea; // store JLabel for outputting character
        this.scroll = scroll;
    }
    
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
