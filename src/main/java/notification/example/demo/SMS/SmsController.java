package notification.example.demo.SMS;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/sms")
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(final SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping
    public void sendSms(@Validated @RequestBody SmsRequest smsRequest) {
        smsService.sendSms(smsRequest);
    }
}
