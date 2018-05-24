import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

class PortReader implements SerialPortEventListener {
    private int cnt,max;
    private boolean isDone,dataRdy;
    private String data;
    private SerialPort sp;
    public PortReader(SerialPort sp, int max) {
        this.max = max;
        this.isDone = false;
        this.data = "";
        this.cnt=0;
        this.sp=sp;
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        if(serialPortEvent.isRXCHAR()){
            try {
                if(this.cnt<this.max){
                    if(!dataRdy){
                        String s = sp.readString();
                        String[] split;
                        if (s.contains("\r\n")) {
                            split = s.split("\\r\\n");
                            this.data = split[0];
                            this.dataRdy = true;
                            this.cnt++;
                        }
                        else{
                            this.dataRdy=false;
                        }
                    }
                }
                else{
                    this.isDone=true;
                }
            } catch (SerialPortException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean isDone() {
        return isDone;
    }

    public boolean isDataRdy() {
        return dataRdy;
    }

    public String getData() {
        return data;
    }
    public void setDataRdy(boolean dataRdy) {
        this.dataRdy = dataRdy;
    }
}