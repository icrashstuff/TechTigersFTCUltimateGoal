package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


public class SuperSecretTelemetry {
  
  public static int lettersVisible = 4;
  public static int lettersPosition = 0;
  public static String[][] letters;
  public static String[][] lines;
  public static int k;
  /**
   * Holds the parent op mode
   * Private because the the user should have the opmode themselves
   */
  private static LinearOpMode parentOpMode;
  
  SuperSecretTelemetry(LinearOpMode parentOpMode)
  {
    this.parentOpMode = parentOpMode;
    
    letters = new String[255][6];
    letters = new String[255][6];
    letters[(int)'t'][0]="┏━━━━━━┓";
    letters[(int)'t'][1]="┗━┓  ┏━┛";
    letters[(int)'t'][2]="  ┃  ┃  ";
    letters[(int)'t'][3]="  ┃  ┃  ";
    letters[(int)'t'][4]="  ┃  ┃  ";
    letters[(int)'t'][5]="  ┗━━┛  ";
    
    letters[(int)'e'][0]="┏━━━━━━┓";
    letters[(int)'e'][1]="┃ ┏━━━━┛";
    letters[(int)'e'][2]="┃ ┗━━━━┓";
    letters[(int)'e'][3]="┃ ┏━━━━┛";
    letters[(int)'e'][4]="┃ ┗━━━━┓";
    letters[(int)'e'][5]="┗━━━━━━┛";
    
    letters[(int)'c'][0]="┏━━━━━━┓";
    letters[(int)'c'][1]="┃ ┏━━━━┛";
    letters[(int)'c'][2]="┃ ┃     ";
    letters[(int)'c'][3]="┃ ┃     ";
    letters[(int)'c'][4]="┃ ┗━━━━┓";
    letters[(int)'c'][5]="┗━━━━━━┛";
    
    letters[(int)'h'][0]="┏━┓  ┏━┓";
    letters[(int)'h'][1]="┃ ┃  ┃ ┃";
    letters[(int)'h'][2]="┃ ┗━━┛ ┃";
    letters[(int)'h'][3]="┃ ┏━━┓ ┃";
    letters[(int)'h'][4]="┃ ┃  ┃ ┃";
    letters[(int)'h'][5]="┗━┛  ┗━┛";
    
    letters[(int)' '][0]="        ";
    letters[(int)' '][1]="        ";
    letters[(int)' '][2]="        ";
    letters[(int)' '][3]="        ";
    letters[(int)' '][4]="        ";
    letters[(int)' '][5]="        ";

    letters[(int)'i'][0]="┏━━━━━━┓";
    letters[(int)'i'][1]="┗━┓  ┏━┛";
    letters[(int)'i'][2]="  ┃  ┃  ";
    letters[(int)'i'][3]="  ┃  ┃  ";
    letters[(int)'i'][4]="┏━┛  ┗━┓";
    letters[(int)'i'][5]="┗━━━━━━┛";
    
    letters[(int)'g'][0]="┏━━━━━━┓";
    letters[(int)'g'][1]="┃ ┏━━━━┛";
    letters[(int)'g'][2]="┃ ┃┏━━━┓";
    letters[(int)'g'][3]="┃ ┃┗━┓ ┃";
    letters[(int)'g'][4]="┃ ┗━━┛ ┃";
    letters[(int)'g'][5]="┗━━━━━━┛";
    
    letters[(int)'s'][0]="┏━━━━━━┓";
    letters[(int)'s'][1]="┃ ┏━━━━┛";
    letters[(int)'s'][2]="┃ ┗━━━━┓";
    letters[(int)'s'][3]="┗━━━━┓ ┃";
    letters[(int)'s'][4]="┏━━━━┛ ┃";
    letters[(int)'s'][5]="┗━━━━━━┛";
    
    letters[(int)'p'][0]="┏━━━━━━┓";
    letters[(int)'p'][1]="┃ ┏━━┓ ┃";
    letters[(int)'p'][2]="┃ ┗━━┛ ┃";
    letters[(int)'p'][3]="┃ ┏━━━━┛";
    letters[(int)'p'][4]="┃ ┃     ";
    letters[(int)'p'][5]="┗━┛     ";
    
    letters[(int)'r'][0]="┏━━━━━━┓";
    letters[(int)'r'][1]="┃ ┏━━┓ ┃";
    letters[(int)'r'][2]="┃ ┗━━┛ ┃";
    letters[(int)'r'][3]="┃ ┏━┓ ┏┛";
    letters[(int)'r'][4]="┃ ┃ ┗┓┗┓";
    letters[(int)'r'][5]="┗━┛  ┗━┛";
    
    lines = new String[14][5];
    
    lines[0] = letters[(int)'t'];
    lines[1] = letters[(int)'e'];
    lines[2] = letters[(int)'c'];
    lines[3] = letters[(int)'h'];
    lines[4] = letters[(int)' '];
    
    lines[5] = letters[(int)'t'];
    lines[6] = letters[(int)'i'];
    lines[7] = letters[(int)'g'];
    lines[8] = letters[(int)'e'];
    lines[9] = letters[(int)'r'];
    lines[10] = letters[(int)'s'];

    lines[11] = letters[(int)' '];
    lines[12] = letters[(int)' '];
    lines[13] = letters[(int)' '];
    
  }
  public static void addTelemetry()
  {
    k++;
    if(k>1000)
    {
      k=0;
    lettersPosition++;
    }
    String s = "";
    if(lettersPosition>=lines.length)
    {
      lettersPosition = 0;
      for(int iLine = 0; iLine < 6; iLine++)
      {
         s="";
         for(int j = 0; j < lettersVisible; j++)
         {
           int a = lettersPosition + j;
           while(a>=lines.length)
             a-=lines.length;
             s+=(lines[a][iLine] + " ");
         }
         parentOpMode.telemetry.addLine(s);
      }
    }
  }
}
