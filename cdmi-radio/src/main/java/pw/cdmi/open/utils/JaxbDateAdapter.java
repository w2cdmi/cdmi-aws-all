package pw.cdmi.open.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbDateAdapter extends XmlAdapter<String, Date> {

    static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String marshal(Date arg0) throws Exception {
        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.format(arg0);

    }

    @Override
    public Date unmarshal(String arg0) throws Exception {
        if (arg0 == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.parse(arg0);
    }

}
