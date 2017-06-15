package com.a20170208.tranvanhay.appat.UtilitiesClass;

/**
 * Created by Van Hay on 14-Jun-17.
 */

public class AlertMessage {
    private long timeSend;
    private String convertedTimeSend;
    private String bodyMessage;

    public AlertMessage() {
    }

    public AlertMessage(long timeSend, String bodyMessage) {
        this.timeSend = timeSend;
        this.bodyMessage = bodyMessage;
        TimeAndDate.convertMilisecondToTimeAndDate(this.timeSend);
    }

    public long getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(long timeSend) {
        this.timeSend = timeSend;
    }

    public String getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(String bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public String getConvertedTimeSend() {
        return convertedTimeSend;
    }

    public void setConvertedTimeSend(String convertedTimeSend) {
        this.convertedTimeSend = convertedTimeSend;
    }
}
