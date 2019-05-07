

package logic;


import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SetLookAndFeel {

    private static UIManager.LookAndFeelInfo looks[];
   
    public static void setLookandFeel(){
        
        looks = UIManager.getInstalledLookAndFeels();
        
        int value=0;
        
        for(int index=0; index<looks.length;index++){
            System.out.println(looks[index].getName() + ", ");
            if(looks[index].getName().equalsIgnoreCase("Windows")){
                value=index;
                System.out.println("CHOSEN : " + looks[index].getName() + ", ");
            }
        }
       
        try {
            UIManager.setLookAndFeel(looks[value].getClassName());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Error Loading Look and Feel " + ex.getMessage(), "Look and Feel", JOptionPane.ERROR_MESSAGE);
        } catch (InstantiationException ex) {
            JOptionPane.showMessageDialog(null,"Error Loading Look and Feel " + ex.getMessage(), "Look and Feel", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalAccessException ex) {
            JOptionPane.showMessageDialog(null,"Error Loading Look and Feel " + ex.getMessage(), "Look and Feel", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null,"Error Loading Look and Feel " + ex.getMessage(), "Look and Feel", JOptionPane.ERROR_MESSAGE);
        }
                 
    }
    
}
