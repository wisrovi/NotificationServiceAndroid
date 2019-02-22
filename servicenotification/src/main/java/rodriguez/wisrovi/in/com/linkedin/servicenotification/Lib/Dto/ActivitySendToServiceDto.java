/*
 * Copyright (c) 2019. Created by William Steve Rodriguez Villamizar.
 * Esta representación está protegida por WISROVI. Queda prohibida la reproducción  y distribución del còdigo sin permiso del autor.
 * https://www.linkedin.com/in/wisrovi-rodriguez/
 * https://github.com/wisrovi/
 * wisrovi.rodriguez@gmail.com
 * https://es.stackoverflow.com/users/85923/william-angel
 *
 *
 *
 */

package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Dto;



public class ActivitySendToServiceDto {
    private String dataCompress;
    private String process;
    private boolean isMsgBroadcast;

    public boolean isMsgBroadcast() {
        return isMsgBroadcast;
    }

    public void setMsgBroadcast(boolean msgBroadcast) {
        isMsgBroadcast = msgBroadcast;
    }

    public String getDataCompress() {
        return dataCompress;
    }

    public void setDataCompress(String dataCompress) {
        this.dataCompress = dataCompress;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
