package com.task.phototeca.telegram.service.impl;

import com.task.phototeca.finance.repository.ChanelRepository;
import com.task.phototeca.finance.repository.entity.ChanelData;
import com.task.phototeca.finance.service.FinanceService;
import com.task.phototeca.telegram.config.BotConfig;
import com.task.phototeca.telegram.service.TelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TelegramBotImpl extends TelegramLongPollingBot implements TelegramBot {

    BotConfig config;

    ChanelRepository chanelRepository;

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            addChatIfNotExist(chatId);

            String[] messageTextParts = update.getMessage().getText().split(" ");
            String command = messageTextParts[0];

            sendMessage(chatId, "Hello user");

           if (command.charAt(0) == '/') {
               switch (command) {
                   case "/refresh":
                       Optional<ChanelData> saved = chanelRepository.findByChanelId(chatId);
                       if (saved.isPresent()) {
                           ChanelData chanelData = saved.get();
                           chanelData.setEnabled(false);
                           chanelRepository.save(chanelData);

                           sendMessage(chatId, "refreshed");
                       }
                       break;

                   default:
                       sendMessage(chatId, String.format("Sorry, %s was not recognized", command));

               }
           }
        }

    }

    private void addChatIfNotExist(long chatId) {
        Optional<ChanelData> saved = chanelRepository.findByChanelId(chatId);
        if (saved.isEmpty()) {
            chanelRepository.save(ChanelData.builder()
                            .chanelId(chatId)
                            .enabled(true)
                    .build());
        }
    }

    @Override
    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

}
