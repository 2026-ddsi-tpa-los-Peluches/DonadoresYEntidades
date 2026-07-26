package ar.edu.utn.dds.k3003.configurationBot;


import ar.edu.utn.dds.k3003.model.TelegramBotComponent;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class TelegramBotConfig {

    private final TelegramBotComponent bot;

    public TelegramBotConfig(TelegramBotComponent bot) {
        this.bot = bot;
    }

    @PostConstruct
    public void iniciarBot() {
        try {

            TelegramBotsApi api =
                    new TelegramBotsApi(DefaultBotSession.class);

            api.registerBot(bot);

            System.out.println("Bot de Telegram iniciado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}