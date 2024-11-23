package notification.example.demo.SMS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class SmsService {
    private static SmsSender smsSender = null;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public static void sendSms(SmsRequest smsRequest) {
        smsSender.sendSms(smsRequest);
    }


}
