package com.web.app.heaven_hr.handler.notification;

import com.web.app.heaven_hr.application.domain.Application;
import com.web.app.heaven_hr.application.util.constant.ApplicationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote This class is to handle the notifications.
 * @since 2019
 */

// TODO For now we are printing the details on console, however in real-time we have to sent an email or SMS to end user.
@Component
public class NotificationEventHandler {

    private static Logger LOGGER = LoggerFactory.getLogger(NotificationEventHandler.class);

    public void pushNotification(Application application, ApplicationStatus prevStatus) {
        switch (application.getStatus()) {
            case APPLIED:
                this.triggerNotification(application, "APPLIED", null);
                break;
            case INVITED:
                this.triggerNotification(application, "INVITED", prevStatus);
                break;
            case REJECTED:
                this.triggerNotification(application, "REJECTED", prevStatus);
                break;
            case HIRED:
                this.triggerNotification(application, "HIRED", prevStatus);
                break;
            default:
                throw new RuntimeException("Invalid status, not able to push notification...");
        }
    }

    private void triggerNotification(Application application, String newStatus, ApplicationStatus prevStatus) {
        LOGGER.info("");
        LOGGER.info("");
        LOGGER.info("****************************************************************************************");
        this.printLog(application, newStatus, prevStatus);
        LOGGER.info("****************************************************************************************");
        //TODO Build email/SMS sending logic here to send updated status to end customer.
    }

    private void printLog(Application application, String newStatus, ApplicationStatus oldStatus) {
        StringBuilder sb = null;
        if (Objects.nonNull(oldStatus))
            sb = new StringBuilder().append("Application status for user ").append(application.getEmail())
                    .append("{ ").append(application.getId()).append(" }").append(" changed from ").append(oldStatus).append(" to ").append(newStatus);
        else
            sb = new StringBuilder().append("Application status for user ").append(application.getEmail())
                    .append("{ ").append(application.getId()).append(" }").append(" is ").append(newStatus);
        LOGGER.info(sb.toString());
    }
}
