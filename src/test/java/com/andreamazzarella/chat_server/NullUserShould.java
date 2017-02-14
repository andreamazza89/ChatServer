package com.andreamazzarella.chat_server;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class NullUserShould {

    @Test
    public void haveAnEmptyStringAsUserName() {
        assertThat(new NullUser().getUserName()).isEqualTo("");
    }
}
