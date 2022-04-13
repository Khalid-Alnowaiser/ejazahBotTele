import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;


public class EjazahBot extends TelegramLongPollingBot {
    Boolean holiday = false;


    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String messageContent = message.getText();
        String userName = message.getFrom().getUserName();
        String chatID = message.getChatId().toString();
        System.out.println("Message Content: " + messageContent + " Sender username: @" + userName);
        LocalDate nowGMT3 = LocalDate.now(ZoneId.of("Asia/Riyadh"));
        SendMessage snd = new SendMessage();
        snd.setChatId(chatID);
        snd.setReplyToMessageId(message.getMessageId());
        LocalDate[] vacations = new LocalDate[4];
        vacations[0] = LocalDate.of(2022, Month.APRIL, 25);
        vacations[1] = LocalDate.of(2022, Month.MAY, 25);
        vacations[2] = LocalDate.of(2022, 6, 16);
        vacations[3] = LocalDate.of(2022, 06, 30);
        LocalDate startOfStudying = LocalDate.of(2022, Month.MAY, 8);


        int vacCounter = 0;
        String output = "error";
        if (message.getText().equals("اجازة")) {
            if (!holiday) {
                int remainingDays = Period.between(nowGMT3, vacations[vacCounter]).getDays();
                if (remainingDays < 0) {
                    while (remainingDays < 0) {
                        vacCounter++;
                        remainingDays = Period.between(nowGMT3, vacations[vacCounter]).getDays();
                    }
                }
                if (!holiday) {
                    output = "باقي " + remainingDays + " يوم على بداية الاجازة";
                }


            }
            if (holiday) {
                int remainingDays = Period.between(nowGMT3, startOfStudying).getDays();
                if (remainingDays < 0) {
                    holiday = true;
                } else {
                    output = "باقي " + remainingDays + " يوم على بداية الدراسة";
                }

            }


            snd.setText(output);
            try {
                execute(snd);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public String getBotUsername() {
        // TODO
        return "Ejazahbot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5306618409:AAFSPcoL72DnF60F_KKuRPIRMcfkRDEuIFc";
    }
}

