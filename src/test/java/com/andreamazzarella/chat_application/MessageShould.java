package com.andreamazzarella.chat_application;

import com.andreamazzarella.chat_server.Message;
import com.andreamazzarella.chat_server.NullUser;
import com.andreamazzarella.chat_server.User;
import com.andreamazzarella.support.FakeUser;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MessageShould {

    @Test
    public void provideNullUserIfUserIfMissing() {
        Message message = new Message(Optional.empty(), "irrelevant content");

        assertThat(message.getUser().getClass()).isEqualTo(new NullUser().getClass());
    }

    @Test
    public void provideTheGivenUser() {
        User andrea = new FakeUser("andrea");
        Message message = new Message(Optional.of(andrea), "irrelevant content");

        assertThat(message.getUser()).isEqualTo(andrea);
    }
}
