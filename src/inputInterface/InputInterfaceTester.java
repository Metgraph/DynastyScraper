package inputInterface;

public class InputInterfaceTester {

    public static boolean a(int b){
        if (b == 100){
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        InputGUI inFrame = new InputGUI();

        while(true) {
            if (inFrame.isFinished()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }

        /*int c = 0;
        while(a(c)){
            c++;
        }*/

        System.out.println("*" + inFrame.getDynastyName() + "*");

    }

}