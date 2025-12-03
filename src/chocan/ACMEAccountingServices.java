package chocan;

import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ACMEAccountingServices extends JFrame{
    private Member[] Members;
    private Member[] SuspendedMembers; //ACME's list of suspended members, should talk to VerifyMember
    
    public ACMEAccountingServices() {
    	
    }
}
