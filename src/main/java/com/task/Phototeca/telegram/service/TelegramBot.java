package com.task.phototeca.telegram.service;

public interface TelegramBot {

    void sendMessage(long chatId, String textToSend);

}
